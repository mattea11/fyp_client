package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.Lock;

import org.json.JSONObject;

//for handeling client connection to larva server
public class WebSocketClientHandler implements Runnable {
    private Socket clientSocket;
    private LarvaServer server;
    private BufferedReader in;
    private PrintWriter out;
     private Lock lock;
     private boolean runMon;

    public WebSocketClientHandler(Socket clientSocket, Lock lock, boolean runMon) {
        this.clientSocket = clientSocket;
//        this.server = larvaServer;
         this.lock = lock;
         this.runMon= Runner.runMon;
    }

    @Override
    public void run() {

        JSON_cleaner_creator jcc = new JSON_cleaner_creator();

        try {
            System.out.println("Enetirng msg");
            // Create input and output streams for the client socket
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;

            // timeout if no messaeg recieved
//            clientSocket.setSoTimeout(100);

            // tell the client they have successfully connected
            out.println("Your connection has been established !");

            // Read input from the client and broadcast it to all clients
            while (true) {
            	if((inputLine = in.readLine()) == null){
            		runMon = true;
            		System.out.println("sleeping in client");
            		Thread.sleep(2000);
            		continue;
            	}
            	
                System.out.println("MESSAGE:\t\t\t " + inputLine);
                synchronized (lock) {
                    try {
                        System.out.println("waiting to lock in client");
                        System.out.println("does thread client have lock? " + Thread.holdsLock(lock));
                        lock.lock();
                        System.out.println("Client successfully locked!!");
//                        }
                        JSONObject jsonInputLine = new JSONObject(inputLine);
                        String firstKey = jsonInputLine.keys().next();
                        if (firstKey.contains("curr_dist") || firstKey.contains("new_nav")
                                || firstKey.contains("speed")) {
                            System.out.println("PARSING NAV");
                            GlobalVar.curr_data = jcc.get_curr_data(jsonInputLine);
                            GlobalVar.change_data = jcc.get_change_data(jsonInputLine);

                            GlobalVar.obj_dist = jcc.get_obj_dist(jsonInputLine);
                            GlobalVar.x_data = jcc.get_nav_command(jsonInputLine, "x");
                            GlobalVar.y_data = jcc.get_nav_command(jsonInputLine, "y");
                            GlobalVar.turn_z_data = jcc.get_nav_command(jsonInputLine, "turn_z");
                            GlobalVar.turn_w_data = jcc.get_nav_command(jsonInputLine, "turn_w");
                        }
                        if (firstKey.contains("vert_ang") || firstKey.contains("end")) {
                            System.out.println("PARSING MAST");
                            GlobalVar.curr_data = jcc.get_curr_data(jsonInputLine);
                            GlobalVar.change_data = jcc.get_change_data(jsonInputLine);

                            GlobalVar.obj_dist = null;
                            GlobalVar.x_data = null;
                            GlobalVar.y_data = null;
                            GlobalVar.turn_z_data = null;
                            GlobalVar.turn_w_data = null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {           
                        runMon = true;             
                        System.out.println("Uncloekd from client");
                        synchronized (lock) {
                        lock.unlock();
                        lock.notify();
                        }
                        System.out.println("Notified from client");
                    }
                }

                // Client disconnected, remove from server's client list
//                server.removeClient(this);
            }
            
        }
        catch (IOException e) {
        	System.out.println("Null!!!");
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("NO GOOD SUPER MEGA DANGER");
    }

    public void sendMessageToRosMon(String message) {
        out.println(message);
    }
}
