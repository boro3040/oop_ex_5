/*
Barak Davidovitch
211604350
OOP BIU 2024
 */

package game_util.listeners;

import sprites.Block;
import sprites.Ball;

/**
 * HitListener that update the counter of game score when blocks are being hit
 * and removed.
 */
public class ScoreTrackingListener implements HitListener {

    private final Counter currentScore;

    /**
     * The constructor.
     * @param scoreCounter the starting score.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(5);
    }

    /**
     * Gets the current score counter.
     * @return the current score counter.
     */
    public Counter getCurrentScore() {
        return currentScore;
    }

    /**
     * add a score to currentScore.
     * @param score the score we want to add as int.
     */
    public void addCurrentScore(int score) {
        this.currentScore.increase(score);
    }
}
