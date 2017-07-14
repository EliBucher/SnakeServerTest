package snake;

import snake.server.GameManager;
import org.json.JSONObject;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by buche on 7/11/2017.
 */
@ServerEndpoint("/snake")
public class SnakeEndpoint {

    private static final ArrayList<Session> clients = new ArrayList<>();
    private static final GameManager gm = new GameManager();

    @OnOpen
    public void onCreateSession(Session session) {
        clients.add(session);
        System.out.println("NEW CLIENT");
    }

    @OnMessage
    public void onTextMessage(String message) {
        System.out.println("Message received: " + message);
        if (message.equals("PING"))
            broadcast("PONG");
        else {
            JSONObject jsonObject = new JSONObject(message);
            switch (jsonObject.getString("method")) {
                case "addSnake":
                    gm.addSnake(jsonObject);
                    break;
                case "setDirection":
                    gm.setDirection(jsonObject);
                    break;
                default:
                    System.out.println("UNKNOWN METHOD CALL");
                    break;
            }
        }
    }

    @OnClose
    public void onCloseSession(Session session) {
        clients.remove(session);
        System.out.println("CLIENT LEFT");
    }

    public static void broadcast(String message) {
        for (Session session : clients) {
            if (session != null && session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
