/*
* Clock
*
* Description:
* Handles time related jobs
*
*  */

package Utility;

import java.util.ArrayList;
import java.util.HashMap;

public class Clock {
    private HashMap<String, ArrayList<Long>> times = new HashMap<>();

    /**
     * startWatch starts a timer that is identified by the specified identifier
     *
     * @param identifier the identifier
     */
    public void startWatch(String identifier) {
        // Adding the time this was called to times
        ArrayList<Long> timesForIdentifier = new ArrayList<>();
        timesForIdentifier.add(System.nanoTime());

        times.put(identifier, timesForIdentifier);
    }

    public void stopWatch(String identifier) {
        // Removes the identifier from the times
        double timeElapsed = getTimeElapsed(identifier);
        times.remove(identifier);
    }

    // TODO: Adding pause functionality
//    public void pause(String identifier) {
//        times.get(identifier).add(System.nanoTime());
//    }

    /**
     * getTimeElapsed gets the time elapsed for the time track identified by the specified
     * identifier
     */
    public double getTimeElapsed(String identifier) {
        long timeElapsed = System.nanoTime() - times.get(identifier).getFirst();
//        long timeElapsed = 0;
//        ArrayList<Long> timeForIdentifier = times.get(identifier);
//
//        for (int i = 0;i < timeForIdentifier.size() - 1;i+=2) {
//            timeElapsed += timeForIdentifier.get(i + 1) - timeForIdentifier.get(i);
//        }
//
//        return timeElapsed / 1e6;

        return timeElapsed / 1e6;
    }

    public String formatTime(String identifier) {
        double millis = getTimeElapsed(identifier);
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        return String.format("%02d : %02d", minutes, seconds % 60);
    }
}
