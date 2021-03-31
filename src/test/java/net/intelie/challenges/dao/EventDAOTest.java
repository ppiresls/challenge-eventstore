package net.intelie.challenges.dao;

import net.intelie.challenges.interfaces.EventIterator;
import net.intelie.challenges.models.Event;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class EventDAOTest {
    @BeforeEach
    public static void createEvents() {
        EventDAO eventDao = new EventDAO();

        eventDao.insert(new Event("type a", 1L));
        eventDao.insert(new Event("type a", 2L));
        eventDao.insert(new Event("type b", 1L));
        eventDao.insert(new Event("type b", 2L));
    }

    @Test
    public void testingInsert() {
        int numberOfEventsBeforeInsert = 0;
        int numberOfEventsAfterInsert  = 0;
        EventDAO eventDao              = new EventDAO();

        EventIterator iterator = eventDao.query("type a", 1L, 3L);
        while (iterator.moveNext()) numberOfEventsBeforeInsert++;

        eventDao.insert(new Event("type a", 1L));
        iterator = eventDao.query("type a", 1L, 2L);

        while (iterator.moveNext()) numberOfEventsAfterInsert++;

        int expectedNumberOfInsertedEvents = 1;
        int actualNumberOfInsertedEvents   = numberOfEventsAfterInsert - numberOfEventsBeforeInsert;

        Assert.assertEquals(expectedNumberOfInsertedEvents, actualNumberOfInsertedEvents);
    }

    @Test
    public void removeAllEventsOfCertainType() {
        EventDAO eventDao = new EventDAO();

        eventDao.removeAll("type a");

        int expectedNumberOfEventsAfterRemoving = 0;
        int actualNumberOfEventsAfterRemoving   = 0;

        EventIterator iterator = eventDao.query("type a", 1L, 3L);
        while (iterator.moveNext()) actualNumberOfEventsAfterRemoving++;

        Assert.assertEquals(expectedNumberOfEventsAfterRemoving, actualNumberOfEventsAfterRemoving);
    }

    @Test
    public void removeSpecificEvent() {
        EventDAO eventDao      = new EventDAO();
        String toBeDeletedType = "tobe_deleted_type";
        Event eventToBeDeleted = new Event(toBeDeletedType, 0L);

        eventDao.insert(eventToBeDeleted);
        EventIterator iterator = eventDao.query(toBeDeletedType, 0L, 1L);

        boolean eventHasBeenSaved = false;
        Event currentEvent;

        while (iterator.moveNext()) {
            currentEvent = iterator.current();
            if (currentEvent.type().equals(toBeDeletedType)) eventHasBeenSaved = true;
        }

        Assert.assertTrue(eventHasBeenSaved);

        eventDao.remove(eventToBeDeleted);
        iterator = eventDao.query(toBeDeletedType, 0L, 1L);

        boolean eventHasBeenDeleted = true;

        while (iterator.moveNext()) {
            currentEvent = iterator.current();
            if (currentEvent.type().equals(toBeDeletedType)) eventHasBeenDeleted = false;
        }

        Assert.assertTrue(eventHasBeenDeleted);
    }

    @Test
    public void queryingEventsOfACertainTypeInACertainTimestampRange() {
        int expectedNumberOfQueriedEvents = 1;
        int actualNumberOfQueriedEvents   = 0;
        EventDAO eventDao                 = new EventDAO();

        eventDao.insert(new Event("type a", 1L));

        EventIterator iterator = eventDao.query("type a", 0L, 2L);
        while (iterator.moveNext()) actualNumberOfQueriedEvents++;

        Assert.assertEquals(expectedNumberOfQueriedEvents, actualNumberOfQueriedEvents);
    }
}
