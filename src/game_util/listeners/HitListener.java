/*
Barak Davidovitch
211604350
OOP BIU 2024
 */

package game_util.listeners;

import sprites.Ball;
import sprites.Block;

/**
 * this interface containing all Objects that want to be notified of hit events.
 */
public interface HitListener {

    /**
     * This method is called whenever the beingHit object is hit.
     * @param beingHit The block was hit.
     * @param hitter is the Ball that's doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
