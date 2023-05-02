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
	
	 public WebSocketClient StartRosBridgeClient() throws URISyntaxException, InterruptedException {
	    WebSocketClient client = new WebSocketClient(new URI("ws://localhost:9090")) {
	      @Override
	      public void onOpen(ServerHandshake serverHandshake) {
	        System.out.println("Connected to RosBridge server");
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
	    	
	        while (!client.getReadyState().equals(WebSocketClient.READYSTATE.OPEN)) {
	            // Wait for connection
	        }
	    }
	    return client;
	  }
	 
	 public JSONObject send_navigation_command(double navigation_change){
		 JSONObject data = new JSONObject();
		 data.put("data", navigation_change);
		 
		 JSONObject msg = new JSONObject();
		 msg.put("op", "publish");
		 msg.put("topic", "/move_base_simple/goal");
		 msg.put("msg", data);
		 msg.put("type", "geometry_msgs/PoseStamped");
		 return msg;
	 }
	 
	 public JSONObject send_speed_command(double speed_change){
		 JSONObject data = new JSONObject();
		 data.put("data", speed_change);
		 
		 JSONObject msg = new JSONObject();
		 msg.put("op", "publish");
		 msg.put("topic", "/curiosity_mars_rover/ackermann_drive_controller/cmd_vel");
		 msg.put("msg", data);
		 msg.put("type", "geometry_msgs/Twist");
		 return msg;
	 }
	 
	 public JSONObject send_vert_ang_command(double angle_change){
        JSONObject data = new JSONObject();
        data.put("data", angle_change);

        JSONObject msg = new JSONObject();
        msg.put("op", "publish");
        msg.put("topic", "/curiosity_mars_rover/mast_cameras_joint_position_controller/command");
        msg.put("msg", data);
        msg.put("type", "std_msgs/Float64");
        return msg;
	 }
	 
	 public JSONObject send_horiz_ang_command(double angle_change){
	        JSONObject data = new JSONObject();
	        data.put("data", angle_change);

	        JSONObject msg = new JSONObject();
	        msg.put("op", "publish");
	        msg.put("topic", "/curiosity_mars_rover/mast_02_joint_position_controller/command");
	        msg.put("msg", data);
	        msg.put("type", "std_msgs/Float64");
	        return msg;
		 }
}