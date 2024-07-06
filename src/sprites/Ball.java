/*
Barak Davidovitch
211604350
OOP ex2
 */

package sprites;

import biuoop.DrawSurface;
import java.awt.Color;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import geometry.Line;
import collidables.Collidable;
import collidables.CollisionInfo;
import game_util.GameEnvironment;
import game_util.Game;
import game_util.Util;

/**
 * Class representing balls.
 * balls have size, color, and center location.
 * they know how to draw themselves.
 */
public class Ball implements Sprite {

    /*
    The limits of the screen that the bouncing ball can get.
    I entered default values.
    */
    private Point center;
    private final int radius;
    private Color color;
    private Velocity velocity;
    private final GameEnvironment ge;

    /**
     * The constructor of the ball.
     * @param center The center point object Point.
     * @param r The radius of the ball.
     * @param color The color of the ball from Color class.
     * @param ge The gameEnvironment with the collidables of the game.
     */
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment ge) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.ge = ge;
    }

    /**
     * Constructor without giving a point.
     * @param x the x value of the center.
     * @param y the y value of the center.
     * @param r The radius of the ball.
     * @param color The color of the ball from Color class.
     * @param ge The gameEnvironment with the collidables of the game.
     */
    public Ball(int x, int y, int r, java.awt.Color color, GameEnvironment ge) {
        this.center = new Point(x, y);
        this.radius = r;
        this.color = color;
        this.ge = ge;
    }

    /**
     * Constructor without giving a point, to double.
     * @param x the x value of the center.
     * @param y the y value of the center.
     * @param r The radius of the ball.
     * @param color The color of the ball from Color class.
     * @param ge The gameEnvironment with the collidables of the game.
     */
    public Ball(double x, double y, int r, java.awt.Color color,
                GameEnvironment ge) {
        this.center = new Point(x, y);
        this.radius = r;
        this.color = color;
        this.ge = ge;
    }

    /**
     * Get the x cord of the ball center.
     * @return x of Point center.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Get the y cord of the ball center.
     * @return y of Point center.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * get the radius of ball, the so-called "size".
     * @return the int radius.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * get the ball center.
     * @return Point copy Object of center.
     */
    public Point getCenter() {
        if (this.center == null) {
            return null;
        }
        return new Point(this.center);
    }

    /**
     * get the color of the ball.
     * @return The color in Color object.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * set the color of the ball.
     * @param color THe color we want to change to.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Get the current velocity of the ball.
     * @return the velocity on Velocity object type.
     */
    public Velocity getVelocity() {
        return new Velocity(this.velocity.getDx(), this.velocity.getDy());
    }

    /**
     * get the Game environment.
     * @return the ge.
     */
    public GameEnvironment getGe() {
        return this.ge;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillCircle(this.getX(), this.getY(), this.radius);
        d.setColor(Color.BLACK);
        d.drawCircle(this.getX(), this.getY(), this.radius);
    }

    @Override
    public void timePassed() {
        moveOneStep();
    }

    @Override
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addBall(this);
    }

    @Override
    public boolean equals(Sprite other) {
        return this.getPoint().equals(other.getPoint());
    }

    @Override
    public Point getPoint() {
        return new Point(this.center);
    }

    /**
     * this method remove a ball from the Game.
     * @param g The Game we want to remove from.
     */
    public void removeFromGame(Game g) {
        g.removeSprite(this);
        g.removeBall(this);
    }

    /**
     * Set the starting velocity of the ball, with Velocity object.
     * @param v the velocity like Velocity object.
     */
    public void setVelocity(Velocity v) {
        this.velocity = new Velocity(v.getDx(), v.getDy());
    }

    /**
     * set the starting velocity of the ball with the components themselves.
     * @param dx the x component of the velocity.
     * @param dy the y component of the velocity.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Move the ball center one step with the velocity of him.
     * also check the intersection points with all Collidables and move
     * center when needed.
     */
    public void moveOneStep() {
        Point startPoint = this.center;
        Point endPoint = this.getVelocity().applyToPoint(this.center);
        Line trajectory = new Line(startPoint, endPoint);

        CollisionInfo colInfo = this.ge.getClosestCollision(trajectory);
        if (colInfo == null) {
            this.center = trajectory.end();
            return;
        }

        // change the center of ball to almost touch the block
        this.center = trajectory.start();
        // change the Velocity of the ball
        Collidable colObj = colInfo.collisionObject();
        this.velocity = colObj.hit(this, colInfo.collisionPoint(), this.velocity);
    }

    /**
     * Move the Ball x in x-axis and y in y-axis.
     * @param x the distance we want to move on x-axes (can be negative too).
     * @param y the distance we want to move on y-axes (can be negative too).
     */
    public void moveBall(double x, double y) {
        this.center.movePoint(x, y);
    }

    /**
     * This method find the point of center in the line intersection with ball
     * that move from one point to another with his velocity.
     * @param line The line we want to find intersection.
     * @param minMaxFlag -1 the min side of the rectangle, 1 the other side.
     * @param velocityFlag -1 when velocity is negative, 1 else.
     * @param inOrOutFlag -1 when the ball need to be out of rectangle, 1 else.
     * @return the center of the ball when he meet the line. if
     * it's returning null there is no intersection.
     */
    public Point lineIntersection(Line line, int minMaxFlag,
                                  int velocityFlag, int inOrOutFlag) {
        Point startPoint = this.center;
        Point endPoint = this.getVelocity().applyToPoint(this.center);
        // check if ball can pass the line
        if (!((inOrOutFlag == 1) && (Math.abs(minMaxFlag + velocityFlag) == 2)
                || (inOrOutFlag == -1) && (minMaxFlag + velocityFlag == 0))) {
            return null;
        }

        Point rightCenter = null;
        Point leftCenter = null;
        Point upCenter = null;
        Point downCenter = null;
        // right point of ball
        Point rightPoint = line.intersectionWith(new Line(startPoint.getX()
                + this.radius, startPoint.getY(),
                endPoint.getX() + this.radius, endPoint.getY()));
        if (rightPoint != null) {
            rightCenter = new Point(rightPoint.getX() - this.radius,
                    rightPoint.getY());
            rightCenter.setScreenXoRY(1);
        }

        // left point of ball
        Point leftPoint = line.intersectionWith(new Line(startPoint.getX()
                - this.radius, startPoint.getY(),
                endPoint.getX() - this.radius, endPoint.getY()));
        if (leftPoint != null) {
            leftCenter = new Point(leftPoint.getX() + this.radius,
                    leftPoint.getY());
            leftCenter.setScreenXoRY(1);
        }

        // up point of ball
        Point upPoint = line.intersectionWith(new Line(startPoint.getX(),
                startPoint.getY() + this.radius,
                endPoint.getX(), endPoint.getY() + this.radius));
        if (upPoint != null) {
            upCenter = new Point(upPoint.getX(),
                    upPoint.getY() - this.radius);
            upCenter.setScreenXoRY(-1);
        }

        // down point of ball
        Point downPoint = line.intersectionWith(new Line(startPoint.getX(),
                startPoint.getY() - this.radius,
                endPoint.getX(), endPoint.getY() - this.radius));
        if (downPoint != null) {
            downCenter = new Point(downPoint.getX(),
                    downPoint.getY() + this.radius);
            downCenter.setScreenXoRY(-1);
        }
        // find the first intersection center from all 4.
        return startPoint.findClosestPoint(rightCenter,
                    startPoint.findClosestPoint(leftCenter,
                    startPoint.findClosestPoint(upCenter,
                    downCenter)));
    }

    /**
     * Find the center of the closest intersection point with rectangle, between
     * the current state of the ball, and the state after one step.
     * @param rectangle THe rectangle we want to check.
     * @param inOrOutFlag -1 when the ball need to be out of rectangle, 1 else.
     * @return The center of the first intersection point, null if there isn't
     * intersection in this move.
     */
    public Point rectangleIntersection(Rectangle rectangle, int inOrOutFlag) {
        Point startPoint = this.center;
        Point endPoint = this.getVelocity().applyToPoint(this.center);
        //treat corners.
        Point firstCorner = new Point(rectangle.getMaxX(), rectangle.getMaxY());
        Point secondCorner = new Point(rectangle.getMinX(), rectangle.getMaxY());
        Point thirdCorner = new Point(rectangle.getMaxX(), rectangle.getMinY());
        Point fourthCorner = new Point(rectangle.getMinX(), rectangle.getMinY());
        if (Util.isSmallerOrEqual(endPoint.distance(firstCorner), this.radius)
            || Util.isSmallerOrEqual(endPoint.distance(secondCorner), this.radius)
            || Util.isSmallerOrEqual(endPoint.distance(thirdCorner), this.radius)
            || Util.isSmallerOrEqual(endPoint.distance(fourthCorner), this.radius)) {
                // special case when we need to turn both velocities.
                this.velocity = new Velocity(-this.velocity.getDx(),
                                            -this.velocity.getDy());
        }

        int velocityFlagX, velocityFlagY;
        if (Util.isBiggerOrEqual(this.velocity.getDx(), 0)) {
            velocityFlagX = 1;
        } else {
            velocityFlagX = -1;
        }
        if (Util.isBiggerOrEqual(this.velocity.getDy(), 0)) {
            velocityFlagY = 1;
        } else {
            velocityFlagY = -1;
        }

        Line rightLine = new Line(rectangle.getMaxX(), rectangle.getMinY(),
                rectangle.getMaxX(), rectangle.getMaxY());
        Point rightIntersection = lineIntersection(rightLine, 1,
                velocityFlagX, inOrOutFlag);

        Line leftLine = new Line(rectangle.getMinX(), rectangle.getMinY(),
                rectangle.getMinX(), rectangle.getMaxY());
        Point leftIntersection = lineIntersection(leftLine, -1,
                velocityFlagX, inOrOutFlag);

        Line upLine = new Line(rectangle.getMinX(), rectangle.getMaxY(),
                rectangle.getMaxX(), rectangle.getMaxY());
        Point upIntersection = lineIntersection(upLine, 1,
                velocityFlagY, inOrOutFlag);

        Line downLine = new Line(rectangle.getMinX(), rectangle.getMinY(),
                rectangle.getMaxX(), rectangle.getMinY());
        Point downIntersection = lineIntersection(downLine, -1,
                velocityFlagY, inOrOutFlag);

        // find the first intersection center from all 4.
        return startPoint.findClosestPoint(rightIntersection,
                    startPoint.findClosestPoint(leftIntersection,
                    startPoint.findClosestPoint(upIntersection,
                    downIntersection)));
    }

    /**
     * This method checks if ball is really in specific rectangle.
     * @param rectangle The rectangle we want to check about.
     * @return true only when all ball is inside rectangle, otherwise false.
     */
    public boolean isBallInsideRectangle(Rectangle rectangle) {
        return Util.isSmaller(this.center.getX() + this.radius,
                            rectangle.getMaxX())
                    && Util.isBigger(this.center.getX() - this.radius,
                            rectangle.getMinX())
                    && Util.isSmaller(this.center.getY() + this.radius,
                            rectangle.getMaxY())
                    && Util.isBigger(this.center.getY() - this.radius,
                            rectangle.getMinY());
    }

    /**
     * This method checks if ball is really out of specific rectangle.
     * @param rectangle The rectangle we want to check about.
     * @return true only when all ball is outside rectangle, otherwise false.
     */
    public boolean isBallOutsideRectangle(Rectangle rectangle) {
        return Util.isBigger(this.center.getX() - this.radius,
                            rectangle.getMaxX())
                    || Util.isSmaller(this.center.getX() + this.radius,
                            rectangle.getMinX())
                    || Util.isBigger(this.center.getY() - this.radius,
                            rectangle.getMaxY())
                    || Util.isSmaller(this.center.getY() + this.radius,
                            rectangle.getMinY());
    }
}
