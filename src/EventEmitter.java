import java.util.*;

public class EventEmitter {
    final private HashMap<String, ArrayList<EventListener>> events = new HashMap<>();

    public void subscribe(String eventName, EventListener listener) {
        if (events.containsKey(eventName)) {
            events.get(eventName).add(listener);
        } else {
            ArrayList<EventListener> functions = new ArrayList<>();
            functions.add(listener);

            events.put(eventName, functions);
        }
    }

    public void unsubscribe(String eventName, EventListener listener) {
        events.remove(eventName, listener);
    }

    public void emit(String eventName, Object ...args) {
        if (!events.containsKey(eventName)) {
            return;
        }

        for (EventListener function : events.get(eventName)) {
            function.onEvent(args);
        }
    }
}
