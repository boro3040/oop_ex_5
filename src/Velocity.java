/*
Barak Davidovitch
211604350
OOP ex2
 */

/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {

    private final double dx;
    private final double dy;

    /**
     * constructor of the 2 axis velocities.
     * @param dx the change in x direction for one step.
     * @param dy the change in y direction for one step.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * get the x component of the velocity.
     * @return dx.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * get the y component of the velocity.
     * @return dy.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Take a point with position (x,y)
     * and return a new point with position (x+dx, y+dy).
     * @param p the starting Point.
     * @return (x + dx, y + dy).
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**
     * Take angle (direction) and speed and make it cartesian coordinates.
     * @param angle the angle from y clockwise in degrees.
     * @param speed the vector of velocity size.
     * @return the Velocity object that fit this angle and speed.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // the screen axis are weird.
        Velocity startVector = new Velocity(0, -speed);
        return startVector.rotateVector(-angle);
    }

    /**
     * rotate the Velocity vector in specific degrees.
     * @param angle the angle we want to rotate, clockwise.
     * @return the rotated Velocity vector.
     */
    public Velocity rotateVector(double angle) {
        double newDx = Math.cos(Math.toRadians(angle)) * this.dx
                    + Math.sin(Math.toRadians(angle)) * this.dy;
        double newDy = -Math.sin(Math.toRadians(angle)) * this.dx
                    + Math.cos(Math.toRadians(angle)) * this.dy;
        return new Velocity(newDx, newDy);
    }
}
