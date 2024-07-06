/*
Barak Davidovitch
211604350
OOP 2024
 */

package game_util.listeners;

import game_util.Game;
import sprites.Ball;
import sprites.Block;

/**
 * a BallRemover is in charge of removing balls from the game,
 * as well as keeping count of the number of balls that remain.
 */
public class BallRemover implements HitListener {

    private final Game game;
    private final Counter remainingBalls;

    /**
     * The constructor.
     * @param game The game we listen.
     * @param remainingBalls the number of balls remain in Game.
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.remainingBalls.decrease(1);
    }
}
