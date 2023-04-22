package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Runner {
    private int port;
    private List<WebSocketClientHandler> clients;

    public Runner(int port) {
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
                WebSocketClientHandler clientHandler = new WebSocketClientHandler(clientSocket, this);
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
    	Runner  server = new Runner(8080);
        server.start();
    }
}

//
//import javax.websocket.*;
//import javax.websocket.server.ServerEndpoint;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.URI;
//import java.net.URISyntaxException;
//
//@ServerEndpoint("/Larva_socket")
//public class Runner {
//
//    @OnOpen
//    public void onOpen(Session session) {
//        System.out.println("WebSocket opened: " + session.getId());
//    }
//
//    @OnMessage
//    public void onMessage(Session session, String message) throws IOException {
//        System.out.println("WebSocket message received: " + message);
//
//        // Log the received message to a text file
////        try (PrintWriter out = new PrintWriter(new FileWriter("log.txt", true))) {
////            System.out.println(message);
////        }
//
//        session.getBasicRemote().sendText("Server: " + message);
//    }
//
//    @OnClose
//    public void onClose(Session session, CloseReason reason) {
//        System.out.println("WebSocket closed: " + session.getId() + ", Reason: " + reason.getReasonPhrase());
//    }
//
//    @OnError
//    public void onError(Session session, Throwable throwable) {
//        System.err.println("WebSocket error: " + session.getId());
//        throwable.printStackTrace();
//    }
//
//    public static void main(String[] args) throws InterruptedException, DeploymentException, IOException, URISyntaxException {
//        // Start the WebSocket server
//        System.out.println("WebSocket server started on port 8080...");
//        Runner server = new Runner();
//
//        // Create a WebSocketContainer
////        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
////        // Connect to the WebSocket endpoint
////        Session session = container.connectToServer(server, new URI("ws://localhost:8080/Larva_socket"));
////
////        // Send a connection request to itself
////        session.getBasicRemote().sendText("Connection request from self");
//
//        while (true) {
//            Thread.sleep(1000); // Sleep for 1 second
//        }
//    }
//}

















//    public static void main(String[] args) {
//        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//        try {
//            // Connect to the WebSocket server as a client
//            Session session = container.connectToServer(Runner.class, new URI("ws://localhost:8080/Larva_socket"));
//
//            // Send a message to the server
//            session.getBasicRemote().sendText("Hello from WebSocket Server Loopback!");
//
//            // Wait for a response from the server
//            Thread.sleep(5000);
//
//            session.close();
//        } catch (DeploymentException | URISyntaxException | InterruptedException | IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
