package snake.Client;

import org.json.JSONObject;
import snake.server.GameManager;
import snake.util.Snake;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by buche on 7/11/2017.
 */
public class Communicator {

    private static WebSocketContainer container;
    private static SnakeEndpoint endpoint;
    private static String userID;

    static void pingServer() {
        container = ContainerProvider.getWebSocketContainer();
        endpoint = new SnakeEndpoint();

        //ws://javaclassserver.herokuapp.com/Server/snake
        //ws://localhost:8080/Server_war_exploded/snake
        //ws://localhost:8080/test
        //ws://javaclassserver.herokuapp.com/test
        try {
            container.connectToServer(endpoint, new URI("ws://javaclassserver.herokuapp.com/snake"));
            endpoint.sendMessage("PING");
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    static void joinGame(String user) {
        userID = user;
        JSONObject message = new JSONObject();
        message.put("method", "addSnake");
        message.put("userID", userID);

        endpoint.sendMessage(message.toString());
    }

    static void setDirection(String direction) {
        JSONObject json = new JSONObject();
        json.put("method", "setDirection");
        json.put("userID", userID);
        json.put("direction", direction);

        endpoint.sendMessage(json.toString());
    }

}
