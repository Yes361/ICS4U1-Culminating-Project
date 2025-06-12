package Utility;

import java.util.ArrayList;
import java.util.HashMap;

public class Clock {
    private HashMap<String, ArrayList<Long>> times = new HashMap<>();

    public void startWatch(String identifier) {
        ArrayList<Long> timesForIdentifier = new ArrayList<>();
        timesForIdentifier.add(System.nanoTime());

        times.put(identifier, timesForIdentifier);
    }

    public void stopWatch(String identifier) {
        double timeElapsed = getTimeElapsed(identifier);
        times.remove(identifier);
    }

    public void pause(String identifier) {
        times.get(identifier).add(System.nanoTime());
    }

    public double getTimeElapsed(String identifier) {
        long timeElapsed = 0;
        ArrayList<Long> timeForIdentifier = times.get(identifier);

        for (int i = 0;i < timeForIdentifier.size() - 1;i+=2) {
            timeElapsed += timeForIdentifier.get(i + 1) - timeForIdentifier.get(i);
        }

        return timeElapsed / 1e6;
    }
}
