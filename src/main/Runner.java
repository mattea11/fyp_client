package main;

import java.net.URISyntaxException;


public class Runner {
	
	 public static void main(String[] args) throws URISyntaxException, InterruptedException{
	       // Start server in a separate thread
	        Thread serverThread = new Thread(() -> {
	            LarvaServer larvaServer = new LarvaServer(8080);
	            larvaServer.start();
	        });
	        serverThread.start();

	        // Start RosBridgeClient in the main thread
	        RosBridgeClient rbc = new RosBridgeClient();
	        rbc.StartRosBridgeClient();

	        // Wait for server thread to finish
	        serverThread.join();
	 }
}
