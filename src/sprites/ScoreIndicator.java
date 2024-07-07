/*
Barak Davidovitch
211604350
OOP BIU 2024
 */

package sprites;

import biuoop.DrawSurface;
import game_util.listeners.Counter;
import geometry.Rectangle;
import java.awt.Color;

/**
 * This class displaying the score of the player.
 */
public class ScoreIndicator extends Block {

    private final Counter scoreCounter;

    /**
     * constructor with rectangle and different colors to outside and inside.
     * @param rectangle the rectangle of the block.
     * @param insideColor the Color inside the block.
     * @param outsideColor the color outside the block.
     * @param scoreCounter the score of the player.
     */
    public ScoreIndicator(Rectangle rectangle, Color insideColor,
                          Color outsideColor, Counter scoreCounter) {
        super(rectangle, insideColor, outsideColor);
        this.scoreCounter = scoreCounter;
    }

    /**
     * The copy constructor.
     * @param s The block we want to copy.
     */
    public ScoreIndicator(ScoreIndicator s) {
        super(s);
        this.scoreCounter = s.getScoreCounter();
    }

    /**
     * getter of scoreCounter.
     * @return The Counter count the score.
     */
    public Counter getScoreCounter() {
        return new Counter(this.scoreCounter);
    }

    @Override
    public void drawOn(DrawSurface d) {
        super.drawOn(d);
        d.setColor(Color.BLACK);
        Rectangle r = this.getCollisionRectangle();
        // fix the place of the score text.
        int fixX = -30;
        int fixY = 5;
        d.drawText((int) r.getCenter().getX() + fixX, (int) r.getCenter().getY() + fixY,
                    "Score: " + this.scoreCounter.getValue(), 15);
    }
}
