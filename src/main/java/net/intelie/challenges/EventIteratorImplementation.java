package net.intelie.challenges;

import net.intelie.challenges.dao.EventDAO;
import net.intelie.challenges.interfaces.EventIterator;
import net.intelie.challenges.models.Event;

import java.util.Collection;

public class EventIteratorImplementation implements EventIterator {
    private Collection<Event> events;
    private Boolean moveNextMethodHasBeenCalledAtLeastOnce = false;
    private Boolean moveNextMethodLastResult               = null;
    private int currentEventPosition                       = -1;
    private final String illegalStateExceptionMessage      = "The method moveNext was never called " +
                                                             "or it's last result was false";

    public EventIteratorImplementation(Collection<Event> events) { this.events = events; }

    @Override
    public boolean moveNext() {
        moveNextMethodHasBeenCalledAtLeastOnce = true;
        boolean hasReachedEndOfCollection = currentEventPosition == events.size()-1;

        if (hasReachedEndOfCollection) {
            moveNextMethodLastResult = false;
            return false;
        }

        moveNextMethodLastResult = true;
        currentEventPosition++;
        return true;
    }

    @Override
    public Event current() {
        if (!moveNextMethodHasBeenCalledAtLeastOnce || (moveNextMethodLastResult == false)) {
            throw new IllegalStateException(illegalStateExceptionMessage);
        }

        return (Event) events.toArray()[currentEventPosition];
    }

    @Override
    public void remove() {
        if (!moveNextMethodHasBeenCalledAtLeastOnce || (moveNextMethodLastResult == false)) {
            throw new IllegalStateException(illegalStateExceptionMessage);
        }

        Event currentEvent = current();
        events.remove(currentEvent);
        (new EventDAO()).remove(currentEvent);
        currentEventPosition--;
    }

    @Override
    public void close() {
        moveNextMethodHasBeenCalledAtLeastOnce = false;
        moveNextMethodLastResult               = null;
        currentEventPosition                   = -1;
        events                                 = null;
    }
}
