package client;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;

public class Client extends WebSocketClient {
    public Client(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to ROSbridge server.");
        JSONObject subscribeMsg = new JSONObject();
        subscribeMsg.put("op", "subscribe");
        subscribeMsg.put("topic", "/rosout/topics");
        send(subscribeMsg.toString());
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received message from ROS: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection to ROSbridge server closed.");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
}