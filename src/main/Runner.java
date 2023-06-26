package main;

import java.net.URISyntaxException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.java_websocket.client.WebSocketClient;
import com.sun.management.OperatingSystemMXBean;

public class Runner {
	static boolean runMon = true;
	static Lock lock = new ReentrantLock(true);

	public static void main(String[] args) throws URISyntaxException, InterruptedException {

		// start the server that allows commands to be sent to the system
		Thread serverThread = new Thread(() -> {
			System.out.println("Starting Larva server");
			LarvaServer larvaServer = new LarvaServer(8081, lock, runMon);
			larvaServer.start();
		});
		serverThread.start();

		//starting the client connection to the rosbridge on the mian thread
		RosBridgeClient rbc = new RosBridgeClient();
		WebSocketClient rosClient = rbc.StartRosBridgeClient();

		//this thread is incharge of validating the data the sytsem recieves
		Thread monitoringThread = new Thread(() -> {
			while (true) {
				if (!runMon) {
					try {
						Thread.sleep(2000);
						continue;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
				synchronized (lock) {
					try {
						lock.lock();
						LarvaServer.broadcast("wrong move on");
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						runMon = false;
						synchronized (lock) {
							lock.unlock();
							lock.notify();
						}
					}
				}
			}
		});
		monitoringThread.start();
		serverThread.join();
	}
}
