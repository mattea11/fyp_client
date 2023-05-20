package main;

import java.net.URISyntaxException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.java_websocket.client.WebSocketClient;
import org.json.JSONObject;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

public class Runner {
	static boolean runMon = true;
	static Lock lock = new ReentrantLock(true);

	static boolean larva_check_valid_action1() {
		return true;
	}

	static boolean larva_check_valid_action2() {
		return false;
	}

	public static void main(String[] args) throws URISyntaxException, InterruptedException {

		long startTime = System.currentTimeMillis();

		checkCommand cc = new checkCommand();

		// Start server in a separate thread
		Thread serverThread = new Thread(() -> {
			System.out.println("Starting Larva server");
			LarvaServer larvaServer = new LarvaServer(8081, lock, runMon);
			larvaServer.start();
		});
		serverThread.start();

		// Start RosBridgeClient in the main thread
		RosBridgeClient rbc = new RosBridgeClient();
		WebSocketClient rosClient = rbc.StartRosBridgeClient();

		Thread monitoringThread = new Thread(() -> {
			System.out.println("MONITORING");
			while (true) {
				System.out.println("Entered mon loop");
				if(!runMon){
					try {
						Thread.sleep(2000);
						System.out.println("Sleeping in monitor");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				}
				System.out.println("MAIN LOOP");
				synchronized (lock) {
					try {
						System.out.println("waiting to lock in monitor");
						System.out.println("does thread mon have lock? " + Thread.holdsLock(lock));
						lock.lock();
						System.out.println("LOCKED MON");
						if (GlobalVar.curr_data != null) {
							System.out.println("CURR data key: " + GlobalVar.curr_data.getValue0());
						}

						// check what type of command was received
						if (GlobalVar.curr_data == null && GlobalVar.obj_dist == null) {
							System.out.println("Nothing to compute");
							continue;
						} else if (GlobalVar.curr_data != null && GlobalVar.change_data != null
								&& GlobalVar.obj_dist != null && GlobalVar.x_data != null && GlobalVar.y_data != null
								&& GlobalVar.turn_z_data != null && GlobalVar.turn_w_data != null
								&& GlobalVar.obj_dist.getValue0().contains("dist")) {
							double obj_distance = GlobalVar.obj_dist.getValue1();
							double nav_x = GlobalVar.x_data.getValue1();
							double nav_y = GlobalVar.y_data.getValue1();
							double nav_z = GlobalVar.turn_z_data.getValue1();
							double nav_w = GlobalVar.turn_w_data.getValue1();

							// navigation isnt allowed
							boolean valid = checkCommand.check_nav(obj_distance, nav_x, nav_y, nav_w)
									? larva_check_valid_action1()
									: larva_check_valid_action2();
							if (!valid) {
								// if it isnt skip and move on
								System.out.println("Invalid, send move on");
								LarvaServer.broadcast("wrong move on");
							} else if (valid) {
								double new_speed = cc.get_change_data();
								boolean valid_speed = cc.check_speed(new_speed) ? larva_check_valid_action1()
										: larva_check_valid_action2();
								if (valid_speed) {
									JSONObject nav_command_to_send = rbc.send_navigation_command(nav_x, nav_y, nav_z,
											nav_w);
									JSONObject speed_command_to_send = rbc.send_speed_command(new_speed);
									rosClient.send(nav_command_to_send.toString());
									rosClient.send(speed_command_to_send.toString());

									System.out.println("valid, send move on");
									LarvaServer.broadcast("valid move on");
									System.out.println("sent");
								} else if (!valid_speed) {
									// if it isnt skip and move on
									System.out.println("Invalid, send move on");
									LarvaServer.broadcast("wrong move on");
								}
							}
						} else if (GlobalVar.curr_data != null && GlobalVar.change_data != null) {
							if (GlobalVar.curr_data.getValue0().contains("vert_ang")) {
								double change_ang = cc.get_change_data();

								boolean valid = cc.check_vert_ang(change_ang) ? larva_check_valid_action1()
										: larva_check_valid_action2();
								if (valid) {
									JSONObject command_to_send = rbc.send_vert_ang_command(change_ang);
									// System.out.println("command to send from main: " + command_to_send);
									rosClient.send(command_to_send.toString());
									System.out.println("Valid command , send to Ros system");
									System.out.println(command_to_send.toString());
									LarvaServer.broadcast("valid move on");
									System.out.println("Send move on to Control system");
								} else if (!valid) {
									System.out.println("Invalid command , send move on to Control system");
									LarvaServer.broadcast("wrong move on");
								}
							}
						} else if (GlobalVar.curr_data != null && GlobalVar.curr_data.getValue0().contains("end")) {
							System.out.println("No more commands received, ending!");
							long endTime = System.currentTimeMillis();
							System.out.println("Time taken: " + (endTime - startTime) / 1000); // ms
							OperatingSystemMXBean operatingSystemMXBeanEnd = (OperatingSystemMXBean) ManagementFactory
									.getOperatingSystemMXBean();
							System.out.println("CPU use end: " + operatingSystemMXBeanEnd.getProcessCpuLoad() * 100);
							System.exit(0);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						runMon = false;
						System.out.println("Released and notifying client");
//						monitorLocked = false;
						synchronized (lock) {
						lock.unlock();
						lock.notify();
						}
						System.out.println("Unlcoked from monit");
//						synchronized (lock) {
//						lock.notify();
//						}
						System.out.println("notified from monit");
					}
				}
			}
		});
		monitoringThread.start();
		serverThread.join();
	}
}
