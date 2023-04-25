package main;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.java_websocket.server.WebSocketServer;
import org.javatuples.Pair;
import org.json.JSONException;
import org.json.JSONObject;

//for handeling client connection to larva server
public class WebSocketClientHandler implements Runnable {
    private Socket clientSocket;
    private LarvaServer server;
    private BufferedReader in;
    private PrintWriter out;

    public WebSocketClientHandler(Socket clientSocket, LarvaServer larvaServer) {
        this.clientSocket = clientSocket;
        this.server = larvaServer;
    }

    @Override
    public void run() {
    	
    	JSON_cleaner_creator jcc = new JSON_cleaner_creator();
    	
        try {
            // Create input and output streams for the client socket
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;

            // Read input from the client and broadcast it to all clients
            while ((inputLine = in.readLine()) != null) {
            	try{
            	JSONObject jsonInputLine = new JSONObject(inputLine);
            	Pair<String, Double> cleaned = jcc.clean_data(jsonInputLine);
            	System.out.println("Received message from client (string): " + cleaned.getValue0());
            	System.out.println("Received message from client (value): " + cleaned.getValue1());
            	}catch (JSONException e) {
            		System.out.println("Error occurred while parsing JSON input: " + e.getMessage());
                }                
            }

            // Client disconnected, remove from server's client list
            server.removeClient(this);

            // Close streams and socket
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        // Send a message to the client
        out.println(message);
    }
}
