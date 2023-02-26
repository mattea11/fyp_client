package client;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class App {

  public static void main(String[] args) throws URISyntaxException, InterruptedException {
    WebSocketClient client = new WebSocketClient(new URI("ws://localhost:9090")) {
      @Override
      public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Connected to server");
      }

      @Override
      public void onMessage(String s) {
        System.out.println("Received message: " + s);
      }

      @Override
      public void onClose(int i, String s, boolean b) {
        System.out.println("Disconnected from server");
      }

      @Override
      public void onError(Exception e) {
        System.out.println("Error occurred: " + e.getMessage());
      }
    };

    client.connect();
    System.out.println("Connecting to server...");
    // Wait for connection to be established
    CountDownLatch latch = new CountDownLatch(1);
    latch.await(5, TimeUnit.SECONDS);

    if (client.isOpen()) {
      System.out.println("Sending message to server");
      client.send("Hello, server!");
    }

    // Close the connection
    client.close();
  }
}