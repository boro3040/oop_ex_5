/*
Barak Davidovitch
211604350
oop biu 2024
*/

package sprites;

import biuoop.DrawSurface;
import geometry.Point;
import game_util.Game;

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

    /**
     * This method checks if 2 Sprites are equals.
     * @param other The other Sprite we want to check about.
     * @return true the center are in the same place, otherwise false.
     */
    boolean equals(Sprite other);

    /**
     * give the point that represent the Sprite.
     * @return center or upperLeft point.
     */
    Point getPoint();
}
