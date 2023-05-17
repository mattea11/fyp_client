package main;

import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.json.JSONObject;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

public class Runner {
	
	 public static boolean valid_nav = true;
	 public static boolean valid_speed = true;
	 public static boolean valid_mast = true;
	 
	 static boolean larva_check_valid_action1(){
		 return true;
	 }
	  
	 static boolean larva_check_valid_action2(){
		 return false;
	 }

	public static void main(String[] args) throws URISyntaxException, InterruptedException{
		
//		long pid = ProcessHandle.current().pid();
//        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
//        double cpuUsage = osBean.getProcessCpuLoad(pid);
//
//        System.out.println("CPU usage: " + cpuUsage);
		OperatingSystemMXBean operatingSystemMXBean = 
		          (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		       System.out.println("CPU use satrt: "+ operatingSystemMXBean.getProcessCpuLoad()*100);
		
		
		
		long startTime = System.currentTimeMillis();
		 
		 checkCommand cc = new checkCommand();
		 
	       // Start server in a separate thread
	        Thread serverThread = new Thread(() -> {
	            LarvaServer larvaServer = new LarvaServer(8081);
	            larvaServer.start();
	            
	        });
	        serverThread.start();

	        // Start RosBridgeClient in the main thread
	        RosBridgeClient rbc = new RosBridgeClient();
	        WebSocketClient  rosClient = rbc.StartRosBridgeClient();

	        Thread monitoringThread = new Thread(() -> {
	            while (true) {
	            	if(GlobalVar.curr_data != null){
	            		System.out.println("CURR data key: "+ GlobalVar.curr_data.getValue0());
	            	}
	            	
	            	
	            	//check what type of command was received
	            	if(GlobalVar.curr_data == null && GlobalVar.obj_dist == null){
	            		System.out.println("empty");
	            		continue;
	            	}
					else if(GlobalVar.obj_dist != null && GlobalVar.x_data != null && GlobalVar.y_data != null && GlobalVar.turn_z_data != null && GlobalVar.turn_w_data != null && GlobalVar.obj_dist.getValue0().contains("dist")){
	            		//get the total of what the speed would be if the command executed
	            		double obj_distance = GlobalVar.obj_dist.getValue1();
						double nav_x = GlobalVar.x_data.getValue1();
						double nav_y = GlobalVar.y_data.getValue1();
						double nav_z = GlobalVar.turn_z_data.getValue1();
						double nav_w = GlobalVar.turn_w_data.getValue1();
						// System.out.println("NAV CHECK");
	            		
	            		//navigation isnt allowed
						boolean valid = checkCommand.check_nav(obj_distance, nav_x, nav_y, nav_w)?larva_check_valid_action1():larva_check_valid_action2();
	            		if(!valid){
	            			//if it isnt skip and move on
	            			System.out.println("Invalid, send move on");
	            			LarvaServer.broadcast("move on");
	            		}
	            		else if(valid){
	            			// if it is execute it and move on
	            			JSONObject command_to_send = rbc.send_navigation_command(nav_x, nav_y, nav_z, nav_w);
	            			rosClient.send(command_to_send.toString());
	            			try {
								Thread.sleep(10000);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	            			System.out.println("valid, send move on");
	            			LarvaServer.broadcast("move on");
	            			System.out.println("sent");
	            		}
	            	}
					else if(GlobalVar.curr_data != null && GlobalVar.change_data != null){
						if(GlobalVar.curr_data.getValue0().contains("speed")){
							// System.out.println("SPEED CHECK");
		            		//get the todtal of what the speed would be if the command executed
		            		double change_speed = cc.get_change_data();
		            		
		            		//cehck if the speed would be valid
							boolean valid = cc.check_speed(change_speed)?larva_check_valid_action1():larva_check_valid_action2();
		            		
							if(valid_speed){
		            			// if it is execute it and move on
		            			JSONObject command_to_send = rbc.send_speed_command(change_speed);
		            			rosClient.send(command_to_send.toString());
		            			System.out.println("Valid command, send Command to Ros system");
		            			LarvaServer.broadcast("move on");
		            			System.out.println("Send move on to control system");
		            		}
							else if(!valid_speed){
		            			//if it isnt skip and move on
		            			System.out.println("Invalid command, send move on to control system");
		            			LarvaServer.broadcast("move on");
		            		}
		            	}
		            	else if(GlobalVar.curr_data.getValue0().contains("vert_ang")){
		            		// System.out.println("MAST CHCK");
		            		double change_ang = cc.get_change_data();
		            		
							boolean valid = cc.check_vert_ang(change_ang)?larva_check_valid_action1():larva_check_valid_action2();
							
							// System.out.println(2);
							if(valid_mast){
								// System.out.println(11);
		            			// if it is execute it and move on
		            			JSONObject command_to_send = rbc.send_vert_ang_command(change_ang);
			            		// System.out.println("command to send from main: " + command_to_send);
		            			rosClient.send(command_to_send.toString());
		            			System.out.println("Valid command , send to Ros system");
		            			System.out.println(command_to_send.toString());
		            			LarvaServer.broadcast("move on");
		            			System.out.println("Send move on to Control system");
		            		}
		            		else if(!valid_mast){
		            			// System.out.println(22);
		            			//if it isnt skip and move on
		            			System.out.println("Invalid command , send move on to Control system");
		            			LarvaServer.broadcast("move on");
		            		}
		            		
		            	} 
						
					}
					else if(GlobalVar.curr_data != null && GlobalVar.curr_data.getValue0().contains("end")){
	            		System.out.println("No more commands received, ending!");
	            		long endTime = System.currentTimeMillis();
	            		System.out.println("Time taken: "+ (endTime - startTime)/1000); //ms
	            		OperatingSystemMXBean operatingSystemMXBeanEnd = 
	          		          (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	          		       System.out.println("CPU use end: "+ operatingSystemMXBeanEnd.getProcessCpuLoad()*100);
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
