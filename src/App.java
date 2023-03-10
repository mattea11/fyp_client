// package main.java.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

public class App {

  // rostopic echo /my_topic | rosbridge
  // rostopic echo /my_topic | rosbridge

  public static void main(String[] args) throws URISyntaxException, InterruptedException {
    // private Session session;
    WebSocketClient client = new WebSocketClient(new URI("ws://localhost:9090")) {
      @Override
      public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Connected to server");

        JSONObject subscribeMsg = new JSONObject();
        subscribeMsg.put("id", "1");
        subscribeMsg.put("op", "subscribe");
        subscribeMsg.put("topic", "/rosout/topics");
        send(subscribeMsg.toString());
        System.out.println("LOOOOOL");
      }

      @Override
      public void onMessage(String s) {
        System.out.println("Received message: " + s);
      }

      // public void publishToTopic(String topic, String data) {
      //   String message = "{ \"op\": \"publish\", \"topic\": \"" + topic + "\", \"msg\": " + data + " }";
      //   this.session.getAsyncRemote().sendText(message);
      // }

      @Override
      public void onClose(int i, String s, boolean b) {
        System.out.println("Disconnected from server");
      }

      @Override
      public void onError(Exception e) {
        System.out.println("Error occurred: " + e.getMessage());
      }
    };
    
    try {
      client.connect();
      System.out.println("Connecting to server...");
      // Wait for connection to be established
      CountDownLatch latch = new CountDownLatch(1);
      latch.await(5, TimeUnit.SECONDS);
  
      if (client.isOpen()) {
        System.out.println("Sending message to server");
        client.send("Hello, server!");
      }



      
      while (!Thread.currentThread().isInterrupted()) {
      }
      // Close the connection
      System.out.println("Shutting down connection");
      client.close();
    } catch (InterruptedException e) {
        // handle the interrupt
    }
  }
}
// public static void main(String[] args) {
//   try {
//       WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//       String uri = "ws://localhost:9090/";
//       Session session = container.connectToServer(RosBridgeClient.class, URI.create(uri));
//       System.out.println("Connected to rosbridge server: " + uri);

//       RosBridgeClient client = new RosBridgeClient();
//       client.onOpen(session);

//       String topic = "/my_topic";
//       String data = "{ \"data\": \"Hello, world!\" }";
//       client.publishToTopic(topic, data);

//       Thread.sleep(1000);
//       session.close();
//   } catch (Exception e) {
//       e.printStackTrace();
//   }
// }