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
	 
	 public JSONObject send_navigation_command(double x, double y, double turn_z, double turn_w) {
	    JSONObject msg = new JSONObject();
	    JSONObject header = new JSONObject();
	    JSONObject pose = new JSONObject();
	    JSONObject position = new JSONObject();
	    JSONObject orientation = new JSONObject();

	    header.put("frame_id", "odom");
	    
	    position.put("x", x);
	    position.put("y", y);
	    position.put("z", 0);
	    
	    orientation.put("x", 0);
	    orientation.put("y", 0);
	    orientation.put("z", turn_z);
	    orientation.put("w", turn_w);
	    pose.put("position", position);
	    pose.put("orientation", orientation);

	    msg.put("op", "publish");
	    msg.put("topic", "/move_base_simple/goal");
	    msg.put("msg", new JSONObject().put("header", header).put("pose", pose));
	    return msg;
		}
	 
	 public JSONObject send_speed_command(double speed_change){
		    JSONObject data = new JSONObject();
		    data.put("x", speed_change);
		    data.put("y", 0.0);
		    data.put("z", 0.0);

		    JSONObject angular_velocity = new JSONObject();
		    angular_velocity.put("x", 0.0);
		    angular_velocity.put("y", 0.0);
		    angular_velocity.put("z", 0.0);

		    JSONObject twist = new JSONObject();
		    twist.put("linear", data);
		    twist.put("angular", angular_velocity);

		    JSONObject msg = new JSONObject();
		    msg.put("op", "publish");
		    msg.put("topic", "/curiosity_mars_rover/ackermann_drive_controller/cmd_vel");
		    msg.put("msg", twist);
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