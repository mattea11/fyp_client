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
        	int msgCount = 0;
            // Create input and output streams for the client socket
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            
            //tell the client they have successfully connected
            out.println("Your connection has been established !");

            // Read input from the client and broadcast it to all clients
            while ((inputLine = in.readLine()) != null) {
//            	msgCount++;
//            	System.out.println("Received " + msgCount + " messages from client.");
            	System.out.println("Client msg: " + inputLine);
            	try{
            	JSONObject jsonInputLine = new JSONObject(inputLine);
            	GlobalVar.curr_data = jcc.get_curr_data(jsonInputLine);
            	GlobalVar.change_data = jcc.get_change_data(jsonInputLine);
            	
//            	System.out.println("Received message from client curr: " + GlobalVar.curr_data.getValue0() + " " + GlobalVar.curr_data.getValue1());
//            	System.out.println("Received message from client change: " + GlobalVar.change_data.getValue0() + " " + GlobalVar.change_data.getValue1());
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

    public void sendMessageToRosMon(String message) {
        // Send a message to the client
        out.println(message);
    }
}
