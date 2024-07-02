/*
Barak Davidovitch
211604350
OOP BIU 2024
 */
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

/**
 * This class will hold the sprites and the collidables, and will be in charge
 * of the animation.
 */
public class Game {

    private final SpriteCollection sprites;
    private final GameEnvironment environment;
    private final List<Ball> balls;
    private GUI gui;
    private final int guiWidth = 800;
    private final int guiHeight = 600;

    /**
     * The constructor that initialize empty sprites and environment.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.balls = new ArrayList<>();
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
        }
    }

    /**
     * Add the side frame of the screen.
     * @param frameSize the width of each frame block.
     * @param insideColor the Color inside the frame.
     * @param outsideColor the Color outside the frame.
     */
    private void initializeFrame(int frameSize, Color insideColor, Color outsideColor) {
        // right block
        Block block = new Block(new Rectangle(new Point(this.guiWidth - frameSize,
                frameSize), frameSize, this.guiHeight - frameSize),
                insideColor, outsideColor);
        block.addToGame(this);
        // left block
        block = new Block(new Rectangle(new Point(0, frameSize), frameSize,
                this.guiHeight - frameSize), insideColor, outsideColor);
        block.addToGame(this);
        // upper block
        block = new Block(new Rectangle(new Point(0, 0), this.guiWidth,
                frameSize), insideColor, outsideColor);
        block.addToGame(this);
        // lower block
        block = new Block(new Rectangle(new Point(frameSize, this.guiHeight - frameSize),
                this.guiWidth - 2 * frameSize, frameSize),
                insideColor, outsideColor);
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
                                this.guiHeight), Color.BLUE, Color.blue);
        screen.addScreenToGame(this);

        // add 2 ball to the game, in specific location, in middle of the screen.
        initializeBalls(new Point((double) this.guiWidth / 2,
                        (double) this.guiHeight * 3 / 4),
                        2, -45, 90, 3);

        int paddleSize = 100;
        Paddle paddle = new Paddle(new Rectangle(new Point((double) this.guiWidth / 2,
                                    (double) this.guiHeight - 2 * frameSize), paddleSize,
                frameSize), Color.ORANGE, Color.BLACK, this);
        paddle.addToGame(this);

        // add all the inside blocks.
        Color[] colors = {Color.GREEN, Color.PINK, Color.BLUE, Color.YELLOW,
                            Color.RED, Color.GRAY};
        double blockSize = 50;
        Point startBlockUpperLeft = new Point(this.guiWidth - frameSize - blockSize,
                                            (double) this.guiHeight / 2 - frameSize);
        int blocksLines = 6;
        int minBlocksInLine = 7;
        for (int i = 0; i < blocksLines; i++) {
            for (int j = 0; j < i + minBlocksInLine; j++) {
                Point tempPoint = new Point(startBlockUpperLeft);
                tempPoint.movePoint(-j * blockSize, -i * frameSize);
                Block block = new Block(new Rectangle(tempPoint, blockSize,
                                        frameSize), colors[i], Color.BLACK);
                block.addToGame(this);
            }
        }
        initializeFrame(frameSize, Color.GRAY, Color.BLACK);
    }

    /**
     * Run the game -- start the animation loop.
     */
    public void run() {
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            Sleeper sleeper = new Sleeper();
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = this.gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            this.gui.show(d);
            this.sprites.notifyAllTimePassed();
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
