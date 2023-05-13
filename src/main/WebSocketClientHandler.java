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
            
            //tell the client they have successfully connected
            out.println("Your connection has been established !");

            // Read input from the client and broadcast it to all clients
            while ((inputLine = in.readLine()) != null) {
            	System.out.println("Client msg: " + inputLine);
            	try{
            	JSONObject jsonInputLine = new JSONObject(inputLine);
                String firstKey = jsonInputLine.keys().next();
                    
                if(firstKey.contains("dist")){
                    GlobalVar.curr_data = null;
                    GlobalVar.change_data = null;

                    GlobalVar.obj_dist = jcc.get_obj_dist(jsonInputLine);
                    GlobalVar.x_data = jcc.get_nav_command(jsonInputLine, "x");
                    GlobalVar.y_data = jcc.get_nav_command(jsonInputLine, "y");
                    GlobalVar.turn_z_data = jcc.get_nav_command(jsonInputLine, "turn_z");
                    GlobalVar.turn_w_data = jcc.get_nav_command(jsonInputLine, "turn_w");
                }
                else if(firstKey.contains("speed") || firstKey.contains("ang")){
                    GlobalVar.curr_data = jcc.get_curr_data(jsonInputLine);
                    GlobalVar.change_data = jcc.get_change_data(jsonInputLine);

                    GlobalVar.obj_dist = null;
                    GlobalVar.x_data = null;
                    GlobalVar.y_data = null;
                    GlobalVar.turn_z_data = null;
                    GlobalVar.turn_w_data = null;
                }
            	
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
