package main;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@ClientEndpoint
public class RosBridgeClient {
    private Session session;
    private CountDownLatch latch;

    public RosBridgeClient() {
        this.latch = new CountDownLatch(1);
    }

    public void connect(String endpoint) throws DeploymentException {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            URI uri = new URI(endpoint);
            container.connectToServer(this, uri);
            latch.await(); // Wait for connection to be established
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WebSocket connection opened: " + session.getId());
        this.session = session;
        latch.countDown(); // Release latch to indicate connection is established
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("WebSocket message received: " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("WebSocket connection closed: " + session.getId());
    }

    public void sendMessage(String message) throws IOException {
        if (session != null && session.isOpen()) {
            session.getBasicRemote().sendText(message);
        } else {
            System.err.println("WebSocket session not available or closed.");
        }
    }

    public void close() throws IOException {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
    
    public static void main() throws DeploymentException, IOException{
    	//connect to the ros system
    	RosBridgeClient client = new RosBridgeClient();
    	String RosBridgeURL = "ws://127.0.0.1:9090";
    	client.connect(RosBridgeURL);
    	
//    	send message
    	String message = "{\"op\":\"publish\",\"topic\":\"/topic_name\",\"msg\":{\"data\":\"Hello, ROS!\"}}";
    	client.sendMessage(message);
    }
}