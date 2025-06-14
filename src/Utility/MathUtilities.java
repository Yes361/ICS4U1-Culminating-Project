/*
* MathUtilities is a class to handle math functions
*  */

package Utility;

public class MathUtilities {
    /**
     * Constrains a value to a range of [min, max]
     *
     * @param min the minimum of the range
     * @param max the maximum of the range
     * @param val the value to constrain
     *
     * @return the constrained value
     */
    public static float constrain(float min, float max, float val) {
        if (val < min) {
            return min;
        } else if (val > max) {
            return max;
        } else {
            return val;
        }
    }

    public static double constrain(double min, double max, double val) {
        if (val < min) {
            return min;
        } else if (val > max) {
            return max;
        } else {
            return val;
        }
    }

    public static int constrain(int min, int max, int val) {
        if (val < min) {
            return min;
        } else if (val > max) {
            return max;
        } else {
            return val;
        }
    }
}
