package main;

import javax.websocket.*;
import java.net.URI;

@ClientEndpoint
public class WebSocketClientLol {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WebSocket opened: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("WebSocket message received: " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("WebSocket closed: " + session.getId() + ", Reason: " + reason.getReasonPhrase());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("WebSocket error: " + session.getId());
        throwable.printStackTrace();
    }

    public static void main(String[] args) {
        // WebSocket client connecting to server
        System.out.println("WebSocket client connecting...");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String uri = "ws://localhost:8080/Larva_socket"; // change this to your server URI
        try {
            container.connectToServer(WebSocketClientLol.class, URI.create(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
