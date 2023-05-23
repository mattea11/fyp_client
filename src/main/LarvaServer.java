package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class LarvaServer {
    private int port;
    private static List<WebSocketClientHandler> clients;
    private ServerSocket serverSocket;
    private boolean isRunning;
    public volatile Socket clientSocket;
    private Lock lock;
    private boolean runMon;

    public LarvaServer(int port, Lock lock, boolean runMon) {
        this.port = port;
        LarvaServer.clients = new ArrayList<>();
        this.isRunning = false;
        this.lock = lock;
        this.runMon = runMon;
    }

    public Socket start() {
        try {
            // Get the computer's IP address
            InetAddress ipAddress = InetAddress.getLocalHost();
            String hostAddress = ipAddress.getHostAddress();

            System.out.println("Larva server started on: " + hostAddress + ":" + port);

            // Create a server socket on the specified port
            serverSocket = new ServerSocket(port);
            isRunning = true;

            while (isRunning) {
                clientSocket = serverSocket.accept();
                System.out
                        .println("Client connected to Larva server: " + clientSocket.getInetAddress().getHostAddress());

                WebSocketClientHandler clientHandler = new WebSocketClientHandler(clientSocket, lock, runMon);
                clients.add(clientHandler);
                clientHandler.run();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientSocket;
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
        clients.clear();
    }

    public static void broadcast(String message) {
        for (WebSocketClientHandler client : clients) {
            client.sendMessageToRosMon(message);
        }
    }

    public void removeClient(WebSocketClientHandler client) {
        clients.remove(client);
    }

}
