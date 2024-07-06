/*
Barak Davidovitch
211604350
OOP 2024
 */

package game_util.listeners;

/**
 * Class that is used for counting things.
 */
public class Counter {

    private int counter;

    /**
     * The constructor.
     * @param startCount the starting count.
     */
    public Counter(int startCount) {
        this.counter = startCount;
    }

    /**
     * add number to current count.
     * @param number the number to add.
     */
    public void increase(int number) {
        this.counter += number;
    }

    /**
     * subtract number from current count.
     * @param number the number to subtract.
     */
    public void decrease(int number) {
        this.counter -= number;
    }

    /**
     * get current count.
     * @return the current count.
     */
    public int getValue() {
        return this.counter;
    }
}
