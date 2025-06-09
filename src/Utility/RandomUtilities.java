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

    public static <T> void sortListRandomly(T[] list) {
        for (int i = list.length - 1; i > 0; i--)
        {
            int index = rand.nextInt(i + 1);
            T a = list[index];
            list[index] = list[i];
            list[i] = a;
        }
    }
}
