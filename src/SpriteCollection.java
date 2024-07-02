/*
Barak Davidovitch
211604350
OOP BIU 2024
 */
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
     * call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        for (Sprite s : this.sprites) {
            s.timePassed();
        }
    }

    /**
     * call drawOn(d) on all sprites.
     * @param d is the DrawSurface we want to draw on.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            s.drawOn(d);
        }
    }
}
