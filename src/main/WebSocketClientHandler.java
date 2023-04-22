package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.java_websocket.server.WebSocketServer;

public class WebSocketClientHandler implements Runnable {
    private Socket clientSocket;
    private Runner server;
    private BufferedReader in;
    private PrintWriter out;

    public WebSocketClientHandler(Socket clientSocket, Runner runner) {
        this.clientSocket = clientSocket;
        this.server = runner;
    }

    @Override
    public void run() {
        try {
            // Create input and output streams for the client socket
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;

            // Read input from the client and broadcast it to all clients
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received message from client: " + inputLine);
                server.broadcast(inputLine);
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
