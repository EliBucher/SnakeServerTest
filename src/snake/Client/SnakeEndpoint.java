package snake.Client;

import snake.util.Snake;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import javax.websocket.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by buche on 7/11/2017.
 */
public class SnakeEndpoint extends Endpoint {
    private Session session;

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        this.session = session;

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                System.out.println("Message received from server: " + message);
                if (!message.equals("PONG")) {
                    JSONObject json = new JSONObject(message);
                    if (json.getString("packet type").equals("game packet")) {
                        Client.food = new Gson().fromJson(json.getString("food"), Point.class);
                        Type snakesMap = new TypeToken<HashMap<String, Snake>>() {
                        }.getType();
                        Client.snakes = new Gson().fromJson(json.getString("snakes"), snakesMap);
                    }
                }
            }
        });
    }

    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
