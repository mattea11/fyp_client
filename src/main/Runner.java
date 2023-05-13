package main;

import java.net.URISyntaxException;
import org.java_websocket.client.WebSocketClient;
import org.json.JSONObject;


public class Runner {
	
	 public static boolean valid_nav = true;
	 public static boolean valid_speed = true;
	 public static boolean valid_mast = true;

	public static void main(String[] args) throws URISyntaxException, InterruptedException{
		 
		 checkCommand cc = new checkCommand();
		 
	       // Start server in a separate thread
	        Thread serverThread = new Thread(() -> {
	            LarvaServer larvaServer = new LarvaServer(8080);
	            larvaServer.start();
	            
	        });
	        serverThread.start();

	        // Start RosBridgeClient in the main thread
	        RosBridgeClient rbc = new RosBridgeClient();
	        WebSocketClient  rosClient = rbc.StartRosBridgeClient();

	        Thread monitoringThread = new Thread(() -> {
	            while (true) {

                   System.out.println("curr_data: " + GlobalVar.curr_data);
                   System.out.println("change_data: " + GlobalVar.change_data);
	            	
	            	//check what type of command was received
	            	if(GlobalVar.curr_data == null && GlobalVar.obj_dist == null){
	            		continue;
	            	}
					else if(GlobalVar.obj_dist.getValue0().contains("dist")){
	            		//get the total of what the speed would be if the command executed
	            		double obj_distance = GlobalVar.obj_dist.getValue1();
						double nav_x = GlobalVar.x_data.getValue1();
						double nav_y = GlobalVar.y_data.getValue1();
						double nav_z = GlobalVar.turn_z_data.getValue1();
						double nav_w = GlobalVar.turn_w_data.getValue1();
	            		
	            		//navigation isnt allowed
	            		if(!checkCommand.check_nav(obj_distance, nav_x, nav_y, nav_w)){
	            			//if it isnt skip and move on
	            			System.out.println("invalid, send move on");
	            			LarvaServer.broadcast("move on");
	            		}
	            		else if(!checkCommand.check_nav(obj_distance, nav_x, nav_y, nav_w)){
	            			// if it is execute it and move on
	            			JSONObject command_to_send = rbc.send_navigation_command(nav_x, nav_y, nav_z, nav_w);
	            			rosClient.send(command_to_send.toString());
	            			System.out.println("valid, send move on");
	            			LarvaServer.broadcast("move on");
	            			System.out.println("sent");
	            		}
	            	}
	            	else if(GlobalVar.curr_data.getValue0().contains("speed")){
	            		//get the todtal of what the speed would be if the command executed
	            		double total_speed = checkCommand.get_total_data();
	            		
	            		//cehck if the speed would be valid
	            		if(!cc.check_speed(valid_speed, total_speed)){
	            			//if it isnt skip and move on
	            			System.out.println("invalid, send move on");
	            			LarvaServer.broadcast("move on");
	            		}
	            		else if(cc.check_speed(valid_speed, total_speed)){
	            			// if it is execute it and move on
	            			JSONObject command_to_send = rbc.send_speed_command(cc.get_change_data());
	//	            			System.out.println("command to send from main: " + command_to_send);
	            			rosClient.send(command_to_send.toString());
	            			System.out.println("valid, send move on");
	            			LarvaServer.broadcast("move on");
	            			System.out.println("sent");
	            		}
	            	}
	            	else if(GlobalVar.curr_data.getValue0().contains("vert_ang")){
	            		double check_data = cc.get_change_data();
	            		
	            		//cehck if the speed would be valid
	            		if(!cc.check_vert_ang(valid_mast, check_data)){
	            			//if it isnt skip and move on
	            			System.out.println("invalid, send move on");
	            			LarvaServer.broadcast("move on");
	            		}else if(cc.check_vert_ang(valid_mast, check_data)){
	            			// if it is execute it and move on
	            			JSONObject command_to_send = rbc.send_vert_ang_command(cc.get_change_data());
		            		System.out.println("command to send from main: " + command_to_send);
	            			rosClient.send(command_to_send.toString());
	            			System.out.println("valid, send move on");
	            			LarvaServer.broadcast("move on");
	            			System.out.println("sent");
	            		}
	            		
	            	} 
					else if(GlobalVar.curr_data.getValue0().contains("end")){
	            		System.out.println("No more commands received, ending!");
	            		System.exit(0);
	            	}

	                try {
	                    Thread.sleep(1000); // Sleep for 1 second
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	        monitoringThread.start();
	        
	        // Wait for server thread to finish
	        serverThread.join();
	 }
}
