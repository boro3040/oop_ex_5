/*
Barak Davidovitch
211604350
OOP ex2
 */

/**
 * This Point class implement point and their methods for doing GUI
 * that connect between lines and points.
 */
public class Point {
    private double x;
    private double y;
    /*
    a values that tell if this point is intersecting X or Y axes line (3.2).
    0 - not set yet. 1 - is intersection of Y-axes parallel.
    -1 - same with X-axes.
     */
    private int screenXoRY = 0;

    /**
     * The constructor.
     * @param x the first value of point (x-axis)
     * @param y the second value of point (y-axis)
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * THe copy constructor.
     * @param p is the point we want to imitate.
     */
    public Point(Point p) {
        this.x = p.getX();
        this.y = p.getY();
        this.screenXoRY = p.getScreenXoRY();
    }

    /**
     * the method give the distance of this point and the other point.
     * @param other is the other point.
     * @return the distance between them.
     */
    public double distance(Point other) {
        // dist = sqrt((x1-x2)^2+(y1-y2)^2)
        return Math.sqrt((other.getX() - this.x) * (other.getX() - this.x)
                + (other.getY() - this.y) * (other.getY() - this.y));
    }

    /**
     * The method checks if 2 points are the same.
     * Since computers representation of number is finite we used threshold
     * that if 2 points are close enough this is like it's the same point.
     * @param other The other point (not our object).
     * @return true if the points are equal, false otherwise.
     */
    public boolean equals(Point other) {
        return Util.isEqual(this.x, other.getX())
                && Util.isEqual(this.y, other.getY());
    }

    /**
     * This method get the x of the point.
     * @return x.
     */
    public double getX() {
        return this.x;
    }

    /**
     * This method get the y of the point.
     * @return y.
     */
    public double getY() {
        return this.y;
    }

    /**
     * multiply the vector that ends in this Point with scalar.
     * @param scalar The scalar we multiply by.
     * @return scalar * this = (scalar * x, scalar * y)
     */
    public Point scalarMultiply(double scalar) {
        return new Point(scalar * this.getX(), scalar * this.getY());
    }

    /**
     * calculate the addition between this and other vectors.
     * @param other the second vector
     * @return (this + other)
     */
    public Point plus(Point other) {
        return new Point(this.getX() + other.getX(),
                        this.getY() + other.getY());
    }

    /**
     * calculate the difference between this and other vectors.
     * @param other the second vector
     * @return (this - other)
     */
    public Point minus(Point other) {
        return new Point(this.getX() - other.getX(),
                        this.getY() - other.getY());
    }

    /**
     * Scalar product of 2 2-D vectors that ends in Points.
     * @param other The second point
     * @return (this * other)
     */
    public double dot(Point other) {
        return (this.getX() * other.getX() + this.getY() * other.getY());
    }

    /**
     * The cross product of 2 2-D vectors that ends in Points.
     * @param other The second point
     * @return (this X other)
     */
    public double cross(Point other) {
        return (this.getX() * other.getY() - this.getY() * other.getX());
    }

    /**
     * This method checks which point is closest to this point.
     * @param point1 first point
     * @param point2 second point
     * @return The closest point to this point, and null if the 2 points
     * are null.
     */
    public Point findClosestPoint(Point point1, Point point2) {
        if (point1 == null) {
            return point2;
        } else if (point2 == null) {
            return point1;
        }
        if (Util.isBiggerOrEqual(this.distance(point1), this.distance(point2))) {
            return point2;
        }
        return point1;
    }

    /**
     * This method is setting the special value of screenXOrY.
     * @param x the value we want to set.
     */
    public void setScreenXoRY(int x) {
        this.screenXoRY = x;
    }

    /**
     * Get the screenXOrY parameter.
     * @return the screenXOrY parameter.
     */
    public int getScreenXoRY() {
        return this.screenXoRY;
    }

    /**
     * Move the point x in x-axis and y in y-axis.
     * @param x the distance we want to move on x-axes (can be negative too).
     * @param y the distance we want to move on y-axes (can be negative too).
     */
    public void movePoint(double x, double y) {
        this.x = this.x + x;
        this.y = this.y + y;
    }
}
