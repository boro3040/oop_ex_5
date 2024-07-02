/*
Barak Davidovitch
211604350
oop biu 2024
*/
import biuoop.DrawSurface;

/**
 * This is a game object that can be drawn to the screen. also it can be
 * notified that time has passed to change their appearance.
 */
public interface Sprite {
    /**
     * this method draw the sprite object to the screen.
     * @param d the DrawSurface we want to draw on.
     */
    void drawOn(DrawSurface d);

    /**
     * this method notify the sprite that the time passed.
     */
    void timePassed();

    /**
     * This method adding an object to a game.
     * @param g the game we want to add to.
     */
    void addToGame(Game g);
}
