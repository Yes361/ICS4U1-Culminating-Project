package Utility;

public class Clock {
    private long start = 0;
    private long end = 0;

    public void startWatch() {
        start = System.nanoTime();
    }

    public double endStopWatch() {
        end = System.nanoTime();
        return getTimeElapsed();
    }

    public double getTimeElapsed() {
        return (start - end) / 1e6;
    }
}
