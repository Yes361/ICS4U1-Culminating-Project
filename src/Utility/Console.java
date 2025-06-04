package Utility;

// TODO: Add input
public class Console {
    public static void print(Object ...args) {
        print(" ", "\n", args);
    }

    public static void println(Object ...args) {
        print(" ", "\n", args);
    }

    public static void print(String seperator, String end, Object ...args) {
        for (int i = 0; i < args.length; i++) {
            System.out.print(args[i]);
            if (i != args.length - 1) {
                System.out.print(seperator);
            }
        }
        System.out.println(end);
    }
}
