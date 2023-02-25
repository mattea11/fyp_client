package client;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     * @throws URISyntaxException
     */
    public static void main(String[] args) throws URISyntaxException {
        
        System.out.println("Hello World!");
        URI serverUri = new URI("ws://localhost:9090");
        System.out.println("\ncreated url");
        Client client = new Client(serverUri);
        System.out.println(" \ncraeted client");
        client.connect();
        System.out.println(" \nconnected");
    }
}
