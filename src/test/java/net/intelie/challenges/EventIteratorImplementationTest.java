package net.intelie.challenges;

import net.intelie.challenges.dao.EventDAO;
import net.intelie.challenges.interfaces.EventIterator;
import net.intelie.challenges.models.Event;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

public class EventIteratorImplementationTest {
    List<Event> events = new ArrayList<>();
    EventDAO eventDao  = new EventDAO();

    @BeforeEach
    public void createEvents(){
        Event event = new Event("type a", 1L);
        events.add(event);
        eventDao.insert(event);

        event = new Event("type a", 2L);
        events.add(event);
        eventDao.insert(event);

        event = new Event("type a", 3L);
        events.add(event);
        eventDao.insert(event);

        event = new Event("type b", 1L);
        events.add(event);
        eventDao.insert(event);

        event = new Event("type b", 2L);
        events.add(event);
        eventDao.insert(event);

        event = new Event("type b", 3L);
        events.add(event);
        eventDao.insert(event);
    }

    @Test
    public void shouldIterateOverElements() {
        EventIteratorImplementation iterator = new EventIteratorImplementation(events);
        boolean hasEvent = iterator.moveNext();
        int eventPosition = 0;
        Event expectedEvent;
        Event actualEvent;

        while (hasEvent) {
            expectedEvent = events.get(eventPosition);
            actualEvent   = iterator.current();

            Assert.assertEquals(expectedEvent.type(), actualEvent.type());
            Assert.assertEquals(expectedEvent.timestamp(), actualEvent.timestamp());
        }
    }

    @Test
    public void tryingToGetCurrentEventWithoutCallMoveNextFirst() {
        EventIterator iterator = eventDao.query("type a", 0L, 4L);
        Assert.assertThrows(IllegalStateException.class, iterator::current);
    }

    @Test
    public void tryingToGetCurrentEventAfterReachingEndOfCollection() {
        EventIteratorImplementation iterator = new EventIteratorImplementation(events);

        // Iterating to reach end of collection!
        while (iterator.moveNext()) System.out.println(iterator.current());

        Assert.assertThrows(IllegalStateException.class, iterator::current);
    }

    @Test
    public void shouldRemoveEvents() {
        EventIterator iterator = eventDao.query("type a", 1L, 4L);

        while (iterator.moveNext()) iterator.remove();

        iterator = eventDao.query("type a", 1L, 4L);
        Assert.assertFalse(iterator.moveNext());
    }

    @Test
    public void tryingToRemoveCurrentEventWithoutCallMoveNextFirst() {
        EventIterator iterator = eventDao.query("type a", 0L, 4L);
        Assert.assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    public void tryingToRemoveEventAfterReachingEndOfCollection() {
        EventIteratorImplementation iterator = new EventIteratorImplementation(events);

        // Iterating to reach end of collection!
        while (iterator.moveNext()) System.out.println(iterator.current());

        Assert.assertThrows(IllegalStateException.class, iterator::remove);
    }
}
