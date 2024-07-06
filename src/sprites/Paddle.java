/*
Barak Davidovitch
211604350
OOP BIU 2024
 */

package sprites;

import biuoop.DrawSurface;
import java.awt.Color;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import collidables.Collidable;
import game_util.Game;
import game_util.Util;

/**
 * This class is the "player" in the game. it is rectangle that is controlled
 * by the arrow keys.
 */
public class Paddle implements Sprite, Collidable {

    private Block block;
    private final Game game;

    // the paddle velocity each one press left/right.
    private final double velocity = 5;
    private final KeyboardSensor keyboard;

    /**
     * constructor with rectangle and different colors to outside and inside.
     * it's gives the block all these properties.
     * @param rectangle the rectangle of the block.
     * @param insideColor the Color inside the block.
     * @param outsideColor the color outside the block.
     * @param game the Game the paddle on, for know what is the boundaries.
     */
    public Paddle(Rectangle rectangle, Color insideColor, Color outsideColor, Game game) {
        this.block = new Block(rectangle, insideColor, outsideColor);
        this.game = game;

        // create KeyBoardSensor interface to check if keys are pressed.
        GUI gui = new GUI("title", 0, 0);
        this.keyboard = gui.getKeyboardSensor();
    }

    /**
     * move the Paddle left.
     */
    public void moveLeft() {
        Block currentBlock = new Block(this.block);

        // if pass the screen from left return in circular way.
        if (Util.isSmallerOrEqual(this.block.getRectangle().getUpperLeft().getX(), 0)) {
            this.block.moveBlock(this.game.getGuiWidth()
                                    - this.block.getRectangle().getWidth(), 0);
        } else {
            this.block.moveBlock(-this.velocity, 0);
        }

        // makes sure a ball doesn't enter from the sides of paddle.
        for (Ball b : this.game.getBalls()) {
            Ball degenerateBall = new Ball(b.getCenter(), 0, Color.BLACK, b.getGe());
            if (degenerateBall.isBallInsideRectangle(this.block.getRectangle())) {
                b.moveBall(-this.velocity, 0);
            }

            // special case when the ball and paddle are closer to the edge.
            if (this.game.isBallInsideCollidables(degenerateBall)) {
                b.moveBall(this.velocity, 0);
                this.block = currentBlock;
                break;
            }
        }
    }

    /**
     * move the Paddle right.
     */
    public void moveRight() {
        Block currentBlock = new Block(this.block);

        // if pass the screen from left return in circular way.
        if (Util.isBiggerOrEqual(this.block.getRectangle().getUpperLeft().getX()
                                + this.block.getRectangle().getWidth(), this.game.getGuiWidth())) {
            this.block.moveBlock(-this.game.getGuiWidth()
                    + this.block.getRectangle().getWidth(), 0);
        } else {
            this.block.moveBlock(this.velocity, 0);
        }

        // makes sure a ball doesn't enter from the sides of paddle.
        for (Ball b : this.game.getBalls()) {
            Ball degenerateBall = new Ball(b.getCenter(), 0, Color.BLACK, b.getGe());
            if (degenerateBall.isBallInsideRectangle(this.block.getRectangle())) {
                b.moveBall(this.velocity, 0);
            }

            // special case when the ball and paddle are closer to the edge.
            if (this.game.isBallInsideCollidables(degenerateBall)) {
                b.moveBall(-this.velocity, 0);
                this.block = currentBlock;
                break;
            }
        }
    }

    /**
     * every iteration of the animation it's checks which keys are pressed and
     * move accordingly.
     */
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        this.block.drawOn(d);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.block.getCollisionRectangle();
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double newDx = currentVelocity.getDx();
        double newDy = currentVelocity.getDy();

        // Check where the collision happened and update velocity accordingly
        if (Util.isEqual(collisionPoint.getX(),
                this.block.getRectangle().getUpperLeft().getX())
                || Util.isEqual(collisionPoint.getX(),
                this.block.getRectangle().getUpperLeft().getX()
                + this.block.getRectangle().getWidth())) {
            newDx = -newDx;
        }

        /*
        the paddle having 5 equally-spaced regions. The behavior of the ball's
        bounce depends on where it hits the paddle. the left-most region is 1,
        the rightmost as 5 & everything in between accordingly. If the ball hits
        the middle region, it should keep its horizontal direction and only change
        its vertical one. if we hit region 1, the ball bounce back with an angle
        of 300 degrees, regardless of where it came from. for region 2 it
        bounces back 330 degrees, for region 4 it bounces in 30 degrees, and for
        region 5 in 60 degrees.
         */
        double speed = Math.sqrt(newDx * newDx + newDy * newDy);
        double regionWidth = this.block.getRectangle().getWidth() / 5;
        double collisionX = collisionPoint.getX();
        double paddleX = this.block.getRectangle().getUpperLeft().getX();
        double relativeCollisionX = collisionX - paddleX;
        int region = (int) (relativeCollisionX / regionWidth) + 1;

        if (Util.isEqual(collisionPoint.getY(),
                this.block.getRectangle().getUpperLeft().getY())
                || Util.isEqual(collisionPoint.getY(),
                this.block.getRectangle().getUpperLeft().getY()
                + this.block.getRectangle().getHeight())) {
            switch (region) {
                case 1:
                    return Velocity.fromAngleAndSpeed(300, speed);
                case 2:
                    return Velocity.fromAngleAndSpeed(330, speed);
                case 3:
                    return new Velocity(newDx, -newDy);
                case 4:
                    return Velocity.fromAngleAndSpeed(30, speed);
                case 5:
                    return Velocity.fromAngleAndSpeed(60, speed);
                default:
                    return new Velocity(newDx, newDy);
            }
        }
        return new Velocity(newDx, newDy);
    }

    @Override
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    @Override
    public boolean equals(Collidable other) {
        return this.getCollisionRectangle().equals(other.getCollisionRectangle());
    }

    @Override
    public boolean equals(Sprite other) {
        return this.getPoint().equals(other.getPoint());
    }

    @Override
    public Point getPoint() {
        return this.block.getPoint();
    }
}
