package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class LarvaServer{
    private int port;
    private static List<WebSocketClientHandler> clients;
    private ServerSocket serverSocket;
    private boolean isRunning;

    public LarvaServer(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
        this.isRunning = false;
    }


	public void start() {
        try {
            // Get the computer's IP address
            InetAddress ipAddress = InetAddress.getLocalHost();
            String hostAddress = ipAddress.getHostAddress();

            System.out.println("Larva server started on: " + hostAddress + ":" + port);

            // Create a server socket on the specified port
            serverSocket = new ServerSocket(port);
            isRunning = true;

            while (isRunning) {
                // Accept incoming client connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected to Larva server: " + clientSocket.getInetAddress().getHostAddress());
                System.out.println("~~~");
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
	
    public void close() {
        isRunning = false;

        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Clear the list of clients
        clients.clear();
    }

    public static void broadcast(String message) {
        // Broadcast a message to all connected clients
        for (WebSocketClientHandler client : clients) {
            client.sendMessageToRosMon(message);
        }
    }

    public void removeClient(WebSocketClientHandler client) {
        // Remove a client from the list of connected clients
        clients.remove(client);
    }

//    public static void main(String[] args) {
//        // Start the WebSocket server on port 8080
//    	LarvaServer  server = new LarvaServer(8080);
//        server.start();
//    }
}
