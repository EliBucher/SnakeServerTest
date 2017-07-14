package snake.Client;

import snake.server.GameManager;
import snake.util.Snake;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by buche on 7/10/2017.
 */
public class LocalCommunicator {

    private static GameManager server;
    private static String game;
    private static String userID;

    static {
        server = new GameManager();
        server.start();
    }

    static void newGame() {
        game = server.newGame();
        userID = server.addSnake(game);
    }

    static void startGame() {
        server.startGame(game);
    }

    static void getGame() {
        JSONObject json = new JSONObject(server.getGame(game));
        Client.food = new Gson().fromJson(json.getString("food"), Point.class);
        Type snakesMap = new TypeToken<HashMap<String, Snake>>() {
        }.getType();
        Client.snakes = new Gson().fromJson(json.getString("snakes"), snakesMap);
    }

    static void setDirection(String direction) {
        JSONObject json = new JSONObject();
        json.put("game", game);
        json.put("userID", userID);
        json.put("direction", direction);

        server.setDirection(json.toString());
    }

}
