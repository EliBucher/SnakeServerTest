package snake.server;

import snake.SnakeEndpoint;
import com.google.gson.Gson;
import org.json.JSONObject;
import snake.util.Snake;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by buche on 7/10/2017.
 */
class Game {

    private HashMap<String, Snake> snakes;
    private Point food;
    private int gridSize;
    private boolean gameStarted;

    Game(int gridSize) {
        snakes = new HashMap<>();

        this.gridSize = gridSize;
        gameStarted = false;
        spawnFood();
    }

    JSONObject get() {
        JSONObject game = new JSONObject();
        game.put("packet type", "game packet");
        game.put("food", new Gson().toJson(food));
        game.put("snakes", new Gson().toJson(snakes));
        return game;
    }

    void setDirection(String userID, String direction) {
        if (snakes.containsKey(userID))
            snakes.get(userID).setDirection(direction);
    }

    void addSnake(String userID) {
        snakes.put(userID, new Snake(new Point((int) (Math.random() * (gridSize)), (int) (Math.random() * (gridSize))), Color.black));
    }

    private void spawnFood() {
        food = new Point((int) (Math.random() * (gridSize)), (int) (Math.random() * (gridSize)));
    }

    void start() {
        if (!gameStarted) {
            gameStarted = true;
            Timer timer = new Timer(100, e -> {
                // MOVE SNAKES
                boolean foodEaten = false;
                for (String userID : snakes.keySet())
                    if (snakes.get(userID).moveAndEat(food))
                        foodEaten = true;

                // RESPAWN FOOD IF EATEN
                if (foodEaten)
                    spawnFood();

                // KILL SNAKES WITH COLLISIONS
                for (String userID : snakes.keySet())
                    for (String otherUserID : snakes.keySet())
                        if (!userID.equals(otherUserID))
                            if (snakes.get(otherUserID).contains(snakes.get(userID).getHead()))
                                snakes.get(userID).kill();

                ArrayList<String> snakesToRemove = new ArrayList<>();
                for (String userID : snakes.keySet())
                    if (!snakes.get(userID).isAlive())
                        snakesToRemove.add(userID);
                for (String snakeToRemove : snakesToRemove)
                    snakes.remove(snakeToRemove);

                SnakeEndpoint.broadcast(get().toString());
            });
            timer.start();
        }
    }
}
