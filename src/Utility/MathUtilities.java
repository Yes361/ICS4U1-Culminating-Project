package Utility;

public class MathUtilities {
    public static int constrain(int min, int max, int val) {
        if (val < min) {
            return min;
        } else if (val > max) {
            return max;
        } else {
            return max;
        }
    }

    public static float constrain(float min, float max, float val) {
        if (val < min) {
            return min;
        } else if (val > max) {
            return max;
        } else {
            return max;
        }
    }
}
