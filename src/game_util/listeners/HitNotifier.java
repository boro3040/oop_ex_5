/*
Barak Davidovitch
211604350
OOP BIU 2024
 */

package game_util.listeners;

/**
 * this interface indicate that objects that implement is send notifications
 * when they are being hit.
 */
public interface HitNotifier {
    /**
     * Add new listener to hit events.
     * @param hl The HitListener we want to add.
     */
    void addHitListener(HitListener hl);

    /**
     * Remove listener from hit events.
     * @param hl The HitListener we want to remove.
     */
    void removeHitListener(HitListener hl);
}
