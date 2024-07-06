/*
Barak Davidovitch
211604350
OOP BIU 2024
 */

package collidables;

import geometry.Rectangle;
import geometry.Velocity;
import geometry.Point;
import game_util.Game;
import sprites.Ball;

/**
 * interface of objects that can collide.
 */
public interface Collidable {
    /**
     * This method return the collision shape of the object.
     * @return the collision shape by class.
     */
    Rectangle getCollisionRectangle();

    /**
     * This method give the new velocity of the object hit the Collidable
     * object. The new velocity is like a mirror.
     * @param hitter the Ball that hit the collidable.
     * @param collisionPoint The point the two objects are hit.
     * @param currentVelocity The current velocity of the hitting object.
     * @return The new velocity expected after the hit.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

    /**
     * This method adding an object to a game.
     * @param g the game we want to add to.
     */
    void addToGame(Game g);

    /**
     * This method checks if 2 collidables rectangles are equals.
     * @param other The other Collidable we want to check about.
     * @return true if they are in the same place, otherwise false.
     */
    boolean equals(Collidable other);

}
