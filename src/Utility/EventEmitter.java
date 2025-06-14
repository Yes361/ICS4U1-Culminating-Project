/*
* EventEmitter is inspired by the NodeJS event system
*
*
*  */

package Utility;

import java.util.*;

public class EventEmitter {
    final private HashMap<String, ArrayList<EventListener>> events = new HashMap<>();

    // Subscribes an eventListener to an event specified by the eventName
    public void subscribe(String eventName, EventListener listener) {
        // if there are already other listeneres, add this one
        if (events.containsKey(eventName)) {
            events.get(eventName).add(listener);
        } else {
            // if there are no events by the name of eventName, create one
            // and add this to it

            ArrayList<EventListener> functions = new ArrayList<>();
            functions.add(listener);

            events.put(eventName, functions);
        }
    }

    // Removes a listener from the specified event
    public void unsubscribe(String eventName, EventListener listener) {
        events.remove(eventName, listener);
    }

    // Alerts the subscribers to the event
    public void emit(String eventName, Object ...args) {
        if (!events.containsKey(eventName)) {
            return;
        }

        for (EventListener function : events.get(eventName)) {
            function.onEvent(args);
        }
    }
}
