/*
Barak Davidovitch
211604350
OOP BIU 2024
 */

package game_util;

import java.util.List;
import java.util.ArrayList;
import collidables.Collidable;
import collidables.CollisionInfo;
import geometry.Line;
import geometry.Point;

/**
 * This class holds the collection of things that the ball can collide with.
 */
public class GameEnvironment {

    private final List<Collidable> collidables;

    /**
     * The constructor that initialize the list of Collidable Objects.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * constructor that have option to give outside Collidables.
     * @param collidables The outside Collidables.
     */
    public GameEnvironment(List<Collidable> collidables) {
        // copy list
        this.collidables = new ArrayList<>(collidables);
    }

    /**
     * add a given Collidable to the environment.
     * @param c the Collidable Object.
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * This method remove Collidable object from the GameEnvironment.
     * @param c the Collidable we want to remove.
     */
    public void removeCollidable(Collidable c) {
        int index = 0;
        for (Collidable tempCol : new ArrayList<>(this.collidables)) {
            if (c.equals(tempCol)) {
                this.collidables.remove(index);
            }
            index++;
        }
    }

    /**
     * we assume an object moving from line.start() to line.end().
     * this method return the closest collisionInfo that is going to occur.
     * @param trajectory the Line that the object moving on.
     * @return if this object will not collide with any of the collidables
     * in the collection return null, else return the closest collisionInfo
     * to start from the collidables.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestPoint = null;
        Collidable collidableObject = null;
        for (Collidable c : this.collidables) {
            Point tempPoint = trajectory.closestIntersectionToStartOfLine(
                        c.getCollisionRectangle());
            if (tempPoint == null) {
                continue;
            } else if ((closestPoint == null) || Util.isSmaller(
                        trajectory.start().distance(tempPoint),
                        trajectory.start().distance(closestPoint))) {
                closestPoint = tempPoint;
                collidableObject = c;
            }
        }

        // if there is no collision return null.
        if (closestPoint == null) {
            return null;
        }
        return new CollisionInfo(closestPoint, collidableObject);
    }

    /**
     * get all collidables in game.
     * @return ArrayList of Collidables in game.
     */
    public List<Collidable> getCollidables() {
        return new ArrayList<>(this.collidables);
    }
}
