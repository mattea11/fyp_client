package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class LarvaServer {
    private int port;
    private List<WebSocketClientHandler> clients;

    public LarvaServer(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
    }

    public void start() {
        try {
            // Get the computer's IP address
            InetAddress ipAddress = InetAddress.getLocalHost();
            String hostAddress = ipAddress.getHostAddress();

            System.out.println("WebSocket server started on: " + hostAddress + ":" + port);

            // Create a server socket on the specified port
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                // Accept incoming client connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Create a new handler for the client and start it in a separate thread
                WebSocketClientHandler clientHandler = new WebSocketClientHandler(clientSocket, this); //to fix edit client handler
                clients.add(clientHandler);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String message) {
        // Broadcast a message to all connected clients
        for (WebSocketClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public void removeClient(WebSocketClientHandler client) {
        // Remove a client from the list of connected clients
        clients.remove(client);
    }

    public static void main(String[] args) {
        // Start the WebSocket server on port 8080
    	Runner  server = new LarvaServer(8080);
        server.start();
    }
}
