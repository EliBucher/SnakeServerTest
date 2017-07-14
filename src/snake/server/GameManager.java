package snake.server;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by buche on 7/10/2017.
 */
public class GameManager extends Thread {

    private HashMap<String, Game> games;

    public GameManager() {
        games = new HashMap<>();
    }

    public String newGame() {
        String uuid = UUID.randomUUID().toString();
        games.put(uuid, new Game(40));
        return uuid;
    }

    public void startGame(String input) {
        if (games.containsKey(input))
            games.get(input).start();
    }

    public String getGame(String input) {
        if (games.containsKey(input))
            return games.get(input).get().toString();
        return null;
    }

    public String addSnake(String input) {
        String uuid = UUID.randomUUID().toString();
        if (games.containsKey(input)) {
            games.get(input).addSnake(uuid);
            return uuid;
        }
        return null;
    }

    public void setDirection(String input) {
        JSONObject json = new JSONObject(input);
        String game = json.getString("game");
        if (games.containsKey(game)) {
            String userID = json.getString("userID");
            String direction = json.getString("direction");
            games.get(game).setDirection(userID, direction);
        }
    }


}
