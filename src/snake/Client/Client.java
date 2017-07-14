package snake.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import snake.util.Snake;


/**
 * Created by buche on 6/27/2017.
 */
public class Client extends JApplet implements KeyListener {

    static final int PIXEL_SIZE = 10;
    final int GRID_SIZE = 40;
    static HashMap<String, Snake> snakes;
    static Point food;
    private static String userID;

    private Graphics bufferGraphics;
    private Image offscreen;

    public void init() {
        Scanner sc = new Scanner(System.in);
        System.out.print("INPUT USER: ");
        userID = sc.nextLine();

        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        setSize(GRID_SIZE * PIXEL_SIZE, GRID_SIZE * PIXEL_SIZE);

        Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        offscreen = createImage(screen.width, screen.height);
        bufferGraphics = offscreen.getGraphics();

        snakes = new HashMap<>();
        food = new Point();

        Communicator.pingServer();
        Communicator.joinGame(userID);
    }


    public void paint(Graphics g) {
        bufferGraphics.clearRect(0, 0, offscreen.getWidth(this), offscreen.getHeight(this));

        for (String user : snakes.keySet()) {
            if (user.equals(userID))
                g.setColor(Color.BLUE);
            else
                g.setColor(Color.BLACK);
            snakes.get(user).draw(bufferGraphics, 10);
        }

        bufferGraphics.setColor(Color.RED);
        bufferGraphics.fillRect(food.x * PIXEL_SIZE, food.y * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);

        g.drawImage(offscreen, 0, 0, this);
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'a':
                Communicator.setDirection("LEFT");
                break;
            case 'w':
                Communicator.setDirection("UP");
                break;
            case 's':
                Communicator.setDirection("DOWN");
                break;
            case 'd':
                Communicator.setDirection("RIGHT");
                break;
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
