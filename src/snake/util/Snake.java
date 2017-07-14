package snake.util;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by buche on 7/10/2017.
 */
public class Snake implements Serializable {

    private ArrayList<Point> snake;
    private int vx, vy;
    private boolean alive;
    private Color color;

    public Snake(Point start, Color color) {
        snake = new ArrayList<>();
        snake.add(start);
        vx = 1;
        vy = 0;
        alive = true;
        this.color = color;
    }

    public void setDirection(String direction) {
        switch (direction) {
            case "UP":
                vx = 0;
                vy = -1;
                break;
            case "RIGHT":
                vx = 1;
                vy = 0;
                break;
            case "DOWN":
                vx = 0;
                vy = 1;
                break;
            case "LEFT":
                vx = -1;
                vy = 0;
                break;
        }
    }

    public boolean moveAndEat(Point food) {
        Point head = getHead();
        head = new Point(head.x + vx, head.y + vy);

        // if collides with self, remove snake;
        if (contains(head)) {
            kill();
            snake.remove(0);
            return false;
        }

        snake.add(head);

        if (food.equals(getHead()))
            return true;

        snake.remove(0);
        return false;
    }

    public Point getHead() {
        return snake.get(snake.size() - 1);
    }

    public boolean contains(Point point) {
        return snake.contains(point);
    }

    public void kill() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public void draw(Graphics g, int pixelSize) {
        g.setColor(color);
        for (Point p : snake)
            g.fillRect(p.x * pixelSize, p.y * pixelSize, pixelSize, pixelSize);
    }
}
