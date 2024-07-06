/*
Barak Davidovitch
211604350
OOP ex2
 */

package game_util;

/**
 * class of utility, helper methods to compression with threshold.
 */
public class Util {

    private static final double COMPARISON_THRESHOLD = 0.00001;

    /**
     * checks if 2 doubles is equal.
     * @param x first double.
     * @param y second double.
     * @return true if they are equal (up to the threshold), else false.
     */
    public static boolean isEqual(double x, double y) {
        return (Math.abs(x - y) < COMPARISON_THRESHOLD);
    }

    /**
     * checks if the first double bigger or equal to the second double.
     * @param x first double (checks if he is the bigger one).
     * @param y second double.
     * @return true if x >= y, else false.
     */
    public static boolean isBiggerOrEqual(double x, double y) {
        return isEqual(x, y) || (x > y);
    }

    /**
     * checks if the first double smaller or equal to the second double.
     * @param x first double (checks if he is the bigger one).
     * @param y second double.
     * @return true if x <= y, else false.
     */
    public static boolean isSmallerOrEqual(double x, double y) {
        return isEqual(x, y) || (x < y);
    }

    /**
     * checks if the first double smaller than the second double.
     * @param x first double.
     * @param y second double
     * @return true if x < y, else false.
     */
    public static boolean isSmaller(double x, double y) {
        return !isBiggerOrEqual(x, y);
    }

    /**
     * checks if the first double bigger than the second double.
     * @param x first double.
     * @param y second double
     * @return true if x > y, else false.
     */
    public static boolean isBigger(double x, double y) {
        return !isSmallerOrEqual(x, y);
    }
}
