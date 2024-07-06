/*
Barak Davidovitch
211604350
OOP BIU 2024
 */

package sprites;

import java.util.List;
import java.util.ArrayList;
import biuoop.DrawSurface;

/**
 * This class holds collection of Sprite objects.
 */
public class SpriteCollection {

    private final List<Sprite> sprites;

    /**
     * The constructor that initialize the list of sprites.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * constructor that have option to give outside Sprites.
     * @param sprites The outside Sprites.
     */
    public SpriteCollection(List<Sprite> sprites) {
        // copy list
        this.sprites = new ArrayList<>(sprites);
    }

    /**
     * add a new Sprite to sprites list.
     * @param s The sprite we want to add.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * remove a Sprite from the collection.
     * @param s The sprite we want to delete.
     */
    public void removeSprite(Sprite s) {
        int index = 0;
        for (Sprite tempSprite : new ArrayList<>(this.sprites)) {
            if (s.equals(tempSprite)) {
                this.sprites.remove(index);
            }
            index++;
        }
    }

    /**
     * call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        for (Sprite s : new ArrayList<>(this.sprites)) {
            s.timePassed();
        }
    }

    /**
     * call drawOn(d) on all sprites.
     * @param d is the DrawSurface we want to draw on.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : new ArrayList<>(this.sprites)) {
            s.drawOn(d);
        }
    }
}
