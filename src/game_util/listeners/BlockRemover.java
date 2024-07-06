/*
Barak Davidovitch
211604350
OOP 2024
 */

package game_util.listeners;

import game_util.Game;
import sprites.Block;
import sprites.Ball;

/**
 * a BlockRemover is in charge of removing blocks from the game,
 * as well as keeping count of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {

    private final Game game;
    private final Counter remainingBlocks;

    /**
     * The constructor.
     * @param game The game we listen.
     * @param remainingBlocks the number of blocks remain in Game.
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.changeBallColor(hitter);
        game.removeCollidable(beingHit);
        game.removeSprite(beingHit);
        beingHit.removeHitListener(this);
        this.remainingBlocks.decrease(1);
    }
}
