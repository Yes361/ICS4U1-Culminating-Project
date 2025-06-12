package Utility;

public class MathUtilities {
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
