package snake.server;

import org.json.JSONObject;

/**
 * Created by buche on 7/10/2017.
 */
public class GameManager {

    private final Game game = new Game(40);

    public GameManager() {
        game.start();
    }

    public String getGame() {
        return game.get().toString();
    }

    public void addSnake(JSONObject input) {
        game.addSnake(input.getString("userID"));
    }

    public void setDirection(JSONObject input) {
        String userID = input.getString("userID");
        String direction = input.getString("direction");
        game.setDirection(userID, direction);
    }


}
