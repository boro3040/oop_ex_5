/*
Barak Davidovitch
211604350
OOP BIU 2024
 */

/**
 * Class that holds the information about the collision.
 * it's holds the collision point, and the Collidable object it's run into.
 */
public class CollisionInfo {

    private final Point collisionPoint;
    private final Collidable collisionObject;

    /**
     * The constructor.
     * @param collisionPoint The collision point of the collision.
     * @param collisionObject the object we collide with.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = new Point(collisionPoint);
        this.collisionObject = collisionObject;
    }

    /**
     * return the collision point info.
     * @return the collision Point.
     */
    public Point collisionPoint() {
        return new Point(this.collisionPoint);
    }

    /**
     * the collidable object involved in the collision.
     * @return the collidable Object.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
