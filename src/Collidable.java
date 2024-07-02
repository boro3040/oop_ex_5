/*
Barak Davidovitch
211604350
OOP BIU 2024
 */

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
     * @param collisionPoint The point the two objects are hit.
     * @param currentVelocity The current velocity of the hitting object.
     * @return The new velocity expected after the hit.
     */
    Velocity hit(Point collisionPoint, Velocity currentVelocity);

    /**
     * This method adding an object to a game.
     * @param g the game we want to add to.
     */
    void addToGame(Game g);


}
