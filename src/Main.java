/*
Barak Davidovitch
211604350
oop biu 2024
*/
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * main function to part 1.
 */
public class Main {
    /**
     * the main method.
     * @param args we don't use this args in this case.
     */
    public static void main(String[] args) {
        Random rnd = new Random();
        int rectsAmount = 7;
        GUI gui = new GUI("title", 800, 600);
        List<Collidable> blocks = new ArrayList<>();
        for (int i = 0; i < rectsAmount; i++) {
            for (int j = 0; j < rectsAmount; j++) {
                blocks.add(new Block(new Point(10 + i * 80, 10 + j * 60), 20, 20, java.awt.Color.BLACK));
            }
        }
        blocks.add(new Block(new Point(0, 0), 800, 600, java.awt.Color.BLACK));
        GameEnvironment ge = new GameEnvironment(blocks);
        Ball[] balls = new Ball[rectsAmount * rectsAmount];
        for (int i = 0; i < rectsAmount; i++) {
            for (int j = 0; j < rectsAmount; j++) {
                balls[j + i * rectsAmount] = new Ball(new Point(500, 500), 5, java.awt.Color.RED, ge);
                balls[j + i * rectsAmount].setVelocity(Velocity.fromAngleAndSpeed(rnd.nextInt(0, 360), 5));
            }
        }
        while (true) {
            Sleeper sleeper = new Sleeper();
            sleeper.sleepFor(20);
            DrawSurface d = gui.getDrawSurface();
            for (int i = 0; i < rectsAmount; i++) {
                for (int j = 0; j < rectsAmount; j++) {
                    d.setColor(Color.black);
                    d.drawRectangle((int) blocks.get(j + i * rectsAmount).getCollisionRectangle().getUpperLeft().getX(),
                            (int) blocks.get(j + i * rectsAmount).getCollisionRectangle().getUpperLeft().getY(),
                            (int) blocks.get(j + i * rectsAmount).getCollisionRectangle().getWidth(),
                            (int) blocks.get(j + i * rectsAmount).getCollisionRectangle().getHeight());
                    d.setColor(Color.red);
                    d.fillCircle((int) balls[j + i * rectsAmount].getCenter().getX(),
                                (int) balls[j + i * rectsAmount].getCenter().getY(),
                                balls[j + i * rectsAmount].getSize());
                    balls[j + i * rectsAmount].moveOneStep();
                }
            }
            gui.show(d);


        }

    }
}
