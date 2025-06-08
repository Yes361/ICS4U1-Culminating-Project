package Utility;

import java.util.Random;

public class RandomUtilities {
    private static Random rand = new Random();

    public static int[] randomPosition(int width, int height) {
        return new int[]{ rand.nextInt(0, width), rand.nextInt(0, height) };
    }

    public static <T> T choice(T[] list) {
        return list[rand.nextInt(0, list.length)];
    }
}
