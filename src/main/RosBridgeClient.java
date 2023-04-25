package main;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.websocket.ClientEndpoint;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

@ClientEndpoint
public class RosBridgeClient {
	
	 public void StartRosBridgeClient() throws URISyntaxException, InterruptedException {
	    WebSocketClient client = new WebSocketClient(new URI("ws://localhost:9090")) {
	      @Override
	      public void onOpen(ServerHandshake serverHandshake) {
	        System.out.println("Connected to RosBridge server");

	        JSONObject subscribeMsg = new JSONObject();
	        JSONObject jointControllerStateMsg = new JSONObject(); // Create a JSON object for the expected structure
	        jointControllerStateMsg.put("process_value", 0.5); // Set the process_value field with the correct value
	        subscribeMsg.put("op", "publish");
	        subscribeMsg.put("topic", "/curiosity_mars_rover/arm_01_joint_position_controller/state");
	        subscribeMsg.put("msg", jointControllerStateMsg.toString()); // Set the msg field with the correctly formatted JSON object
	        send(subscribeMsg.toString());
	        System.out.println("LOOOOOL");
	      }
	      

	      @Override //prob wont need
	      public void onMessage(String s) {
	        System.out.println("Received message from RosBridge server : " + s);
	      }

	      @Override
	      public void onClose(int i, String s, boolean b) {
	        System.out.println("Disconnected from RosBridge server");
	      }

	      @Override
	      public void onError(Exception e) {
	        System.out.println("Error occurred on RosBridge connection: " + e.getMessage());
	      }
	    };

	    client.connect();
	    System.out.println("Connecting to RosBridge server...");
	    // Wait for connection to be established
	    CountDownLatch latch = new CountDownLatch(1);
	    latch.await(5, TimeUnit.SECONDS);

	    if (client.isOpen()) {
	      System.out.println("Sending message to RosBridge server");
	      client.send("Hello, server!");
	    }

		    // Close the connection
//		    client.close();
	  }
}