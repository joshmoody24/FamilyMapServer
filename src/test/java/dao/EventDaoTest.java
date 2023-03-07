package dao;

import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class EventDaoTest {
    private Database db;
    private Event bestEvent;
    private Event secondEvent;
    private EventDao eDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        // Here we can set up any classes or variables we will need for each test
        // lets create a new instance of the Database class
        db = new Database();

        // and a new event with random data
        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        // and a second event
        secondEvent = new Event("Biking_123B", "Gale", "Boris",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        eDao = new EventDao(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        eDao.clear();
    }

    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(false);
    }

    @Test
    public void createPass() throws DataAccessException {
        eDao.create(bestEvent);
        Event compareTest = eDao.find(bestEvent.getEventID());
        assertNotNull(compareTest);
        assertEquals(bestEvent, compareTest);
    }

    @Test
    public void createFail() throws DataAccessException {
        eDao.create(bestEvent);
        assertThrows(DataAccessException.class, () -> eDao.create(bestEvent));
    }

    @Test
    public void findPass() throws DataAccessException {
        eDao.create(bestEvent);
        Event event = eDao.find("Biking_123A");
        assertNotNull(event);
        assertEquals(event.getAssociatedUsername(), "Gale");
    }

    @Test
    public void findFail() throws DataAccessException {
        eDao.create(bestEvent);
        Event event = eDao.find("doesn't exist");
        assertNull(event);
    }

    @Test
    public void findForExistingUser() throws DataAccessException {
        eDao.create(bestEvent);
        eDao.create(secondEvent);
        List<Event> events = eDao.findForUser("Gale");
        assertEquals(events.size(), 2);
    }

    @Test
    public void findForNonexistentUser() throws DataAccessException {
        eDao.create(bestEvent);
        eDao.create(secondEvent);
        List<Event> events = eDao.findForUser("Nonexistent User");
        assertEquals(events.size(), 0);
    }

    @Test
    public void findForExistingPerson() throws DataAccessException {
        eDao.create(bestEvent);
        eDao.create(secondEvent);
        List<Event> events = eDao.findForPerson("Boris");
        assertEquals(events.size(), 1);
        events = eDao.findForPerson("Gale123A");
        assertEquals(events.size(), 1);
    }

    @Test
    public void findForNonexistentPerson() throws DataAccessException {
        eDao.create(bestEvent);
        eDao.create(secondEvent);
        List<Event> events = eDao.findForPerson("Nonexistent Person");
        assertEquals(events.size(), 0);
    }

    @Test
    public void clearTest() throws DataAccessException {
        assertDoesNotThrow(() -> eDao.clear());
        assertNull(eDao.find("Biking_123A"));
    }

    @Test
    public void clearForUserPass() throws DataAccessException {
        // add an event for someone else to make sure it's not deleted
        eDao.create(bestEvent);
        eDao.create(secondEvent);
        eDao.create(new Event("other user", "NOT GALE", "Boris",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016));
        eDao.clearForUser("Gale");
        assertNull(eDao.find("Biking123A"));
        assertNotNull(eDao.find("other user"));
    }

    @Test
    public void clearForNonexistentUser() throws DataAccessException {
        eDao.create(bestEvent);
        assertDoesNotThrow(() -> eDao.clearForUser("Nonexistent person"));
        assertNotNull(eDao.find("Biking_123A"));
    }
}
