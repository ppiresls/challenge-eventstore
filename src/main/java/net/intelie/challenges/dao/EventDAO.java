package net.intelie.challenges.dao;

import net.intelie.challenges.EventIteratorImplementation;
import net.intelie.challenges.interfaces.EventIterator;
import net.intelie.challenges.interfaces.EventStore;
import net.intelie.challenges.models.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventDAO implements EventStore {
    private static final List<Event> events = new CopyOnWriteArrayList<>();

    @Override
    public void insert(Event event) { events.add(event); }

    @Override
    public void removeAll(String type) { events.removeIf(event -> event.type().equals(type.trim())); }

    @Override
    public EventIterator query(String type, long startTime, long endTime) {
        boolean eventTimestampIsInSearchedInterval = false;
        boolean eventHasSearchedType               = false;
        Collection<Event> queriedEvents            = new ArrayList<>();

        for (Event event : events) {
            eventHasSearchedType               = event.type().equals(type.trim());
            eventTimestampIsInSearchedInterval = startTime <= event.timestamp() &&
                                                 event.timestamp() < endTime;

            if (eventHasSearchedType && eventTimestampIsInSearchedInterval) {
                queriedEvents.add(new Event(event.type(), event.timestamp()));
            }
        }
        return new EventIteratorImplementation(queriedEvents);
    }

    public void remove(Event event) { events.remove(event); }
}
