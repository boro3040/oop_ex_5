/*
Barak Davidovitch
211604350
OOP BIU 2024
 */

package game_util;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import collidables.Collidable;
import game_util.listeners.Counter;
import game_util.listeners.BlockRemover;
import game_util.listeners.BallRemover;
import game_util.listeners.ScoreTrackingListener;
import sprites.Ball;
import sprites.ScoreIndicator;
import sprites.Block;
import sprites.Paddle;
import sprites.Sprite;
import sprites.SpriteCollection;

/**
 * This class will hold the sprites and the collidables, and will be in charge
 * of the animation.
 */
public class Game {

    private final SpriteCollection sprites;
    private final GameEnvironment environment;
    private final List<Ball> balls;
    private final Counter remainingBlocks;
    private final Counter remainingBalls;
    private final Counter scoreCounter;
    private GUI gui;
    private final int guiWidth = 800;
    private final int guiHeight = 600;
    private final BlockRemover blockRemover;
    private final BallRemover ballRemover;
    private final ScoreTrackingListener scoreTracker;

    /**
     * The constructor that initialize empty sprites and environment.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.balls = new ArrayList<>();
        this.remainingBlocks = new Counter(0);
        this.remainingBalls = new Counter(0);
        this.blockRemover = new BlockRemover(this, this.remainingBlocks);
        this.ballRemover = new BallRemover(this, this.remainingBalls);
        this.scoreCounter = new Counter(0);
        this.scoreTracker = new ScoreTrackingListener(this.scoreCounter);
    }

    /**
     * add a collidable to collidables list in GameEnvironment.
     *
     * @param c The collidable we want to add.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * add a sprite to sprites list in SpriteCollection.
     *
     * @param s The sprite we want to add.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * add ball to the balls list.
     * @param b the Ball we want to add.
     */
    public void addBall(Ball b) {
        this.balls.add(b);
    }

    /**
     * remove ball from balls list.
     * @param b the Ball we want to remove.
     */
    public void removeBall(Ball b) {
        this.balls.remove(b);
    }

    /**
     * This method remove Collidable object from the GameEnvironment.
     * @param c the Collidable we want to remove.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * remove a Sprite from the sprites Collection.
     * @param s The sprite we want to delete.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * This method initialize all balls in game from the same spot with different
     * starting angels.
     * @param ballsStart the Point all balls starts from.
     * @param ballsNum the number of balls we want to add.
     * @param startAngle the angle the first ball came in.
     * @param angleDiff the difference between each balls start angels.
     * @param speed the initial speed of balls.
     */
    private void initializeBalls(Point ballsStart, int ballsNum, double startAngle,
                                 double angleDiff, double speed) {
        int ballRadius = 5;
        for (int i = 0; i < ballsNum; i++) {
            Ball ball = new Ball(ballsStart, ballRadius, Color.WHITE, this.environment);
            ball.setVelocity(Velocity.fromAngleAndSpeed(startAngle + i * angleDiff, speed));
            ball.addToGame(this);
            this.remainingBalls.increase(1);
        }
    }

    /**
     * Add the side frame of the screen.
     * @param frameSize the width of each frame block.
     * @param insideColor the Color inside the frame.
     * @param outsideColor the Color outside the frame.
     */
    private void initializeFrame(int frameSize, Color insideColor, Color outsideColor) {
        // score Indicator
        int scoreIndHeight = 20;
        ScoreIndicator scoreIndicator = new ScoreIndicator(new Rectangle(new Point(0, 0),
                                                        this.guiWidth, scoreIndHeight),
                                                        Color.WHITE, Color.WHITE, this.scoreCounter);
        scoreIndicator.addHitListener(this.scoreTracker);
        scoreIndicator.addToGame(this);

        // right block
        Block block = new Block(new Rectangle(new Point(this.guiWidth - frameSize,
                frameSize + scoreIndHeight), frameSize, this.guiHeight - frameSize),
                insideColor, outsideColor);
        block.addToGame(this);
        // left block
        block = new Block(new Rectangle(new Point(0, frameSize + scoreIndHeight), frameSize,
                this.guiHeight - frameSize), insideColor, outsideColor);
        block.addToGame(this);
        // upper block
        block = new Block(new Rectangle(new Point(0, scoreIndHeight), this.guiWidth,
                frameSize), insideColor, outsideColor);
        block.addToGame(this);
        // lower block
        block = new Block(new Rectangle(new Point(frameSize, this.guiHeight - frameSize + scoreIndHeight),
                this.guiWidth - 2 * frameSize, frameSize),
                Color.GREEN, Color.GREEN);
        block.addHitListener(this.ballRemover);
        block.addToGame(this);
    }

    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle)
     * and add them to the game.
     */
    public void initialize() {
        int frameSize = 20;
        this.gui = new GUI("Arkanoid", this.guiWidth, this.guiHeight);
        Block screen = new Block(new Rectangle(new Point(0, 0), this.guiWidth,
                this.guiHeight), new Color(42, 130, 21, 255), new Color(42, 130, 21, 255));
        screen.addScreenToGame(this);

        // add 2 ball to the game, in specific location, in middle of the screen.
        initializeBalls(new Point((double) this.guiWidth / 2,
                        (double) this.guiHeight * 3 / 4),
                        1, -45, 90, 3);

        int paddleSize = 100;
        Paddle paddle = new Paddle(new Rectangle(new Point((double) this.guiWidth / 2,
                                    (double) this.guiHeight - 2 * frameSize), paddleSize,
                frameSize), Color.ORANGE, Color.BLACK, this);
        paddle.addToGame(this);

        // add all the inside blocks.
        Color[] colors = {Color.WHITE, Color.PINK, Color.BLUE, Color.YELLOW,
                            Color.RED, Color.GRAY};
        double blockSize = 50;
        Point startBlockUpperLeft = new Point(this.guiWidth - frameSize - blockSize,
                                            (double) this.guiHeight / 2 - frameSize);

        initializeFrame(frameSize, Color.GRAY, Color.GRAY);

        int blocksLines = 6;
        int minBlocksInLine = 7;
        for (int i = 0; i < blocksLines; i++) {
            for (int j = 0; j < i + minBlocksInLine; j++) {
                Point tempPoint = new Point(startBlockUpperLeft);
                tempPoint.movePoint(-j * blockSize, -i * frameSize);
                Block block = new Block(new Rectangle(tempPoint, blockSize,
                                        frameSize), colors[i], Color.BLACK);
                block.addHitListener(this.blockRemover);
                block.addHitListener(this.scoreTracker);
                this.remainingBlocks.increase(1);
                block.addToGame(this);
            }
        }
    }

    /**
     * Run the game -- start the animation loop.
     */
    public void run() {
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        int endFlag = 0;
        while (true) {
            Sleeper sleeper = new Sleeper();
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = this.gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            this.gui.show(d);
            this.sprites.notifyAllTimePassed();
            if (endFlag == 1) {
                sleeper.sleepFor(1000);
                this.gui.close();
                return;
            } else if (this.remainingBlocks.getValue() == 0) {
                endFlag = 1;
                this.scoreTracker.addCurrentScore(100);
            } else if (this.remainingBalls.getValue() == 0) {
                endFlag = 1;
            }
            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * this method get the game GUI width.
     * @return the width of screen.
     */
    public int getGuiWidth() {
        return this.guiWidth;
    }

    /**
     * this method get the game GUI height.
     * @return the height of screen.
     */
    public int getGuiHeight() {
        return this.guiHeight;
    }

    /**
     * return the ArrayList of the balls.
     * @return an ArrayList of balls in game.
     */
    public List<Ball> getBalls() {
        return new ArrayList<>(this.balls);
    }

    /**
     * This method checks if ball is in one of the collidables in GameEnvironment.
     * @param b The ball we want to check.
     * @return true if the ball is inside one of the collidables, otherwise false.
     */
    public boolean isBallInsideCollidables(Ball b) {
        for (Collidable c : this.environment.getCollidables()) {
            if (b.isBallInsideRectangle(c.getCollisionRectangle())) {
                return true;
            }
        }
        return false;
    }
}
