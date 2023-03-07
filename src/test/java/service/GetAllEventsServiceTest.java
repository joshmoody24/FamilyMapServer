package service;

import dao.DataAccessException;
import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.GetAllEventsResult;
import request.RegisterRequest;
import request.RegisterResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetAllEventsServiceTest {

    String authtoken;

    @BeforeEach
    public void setUp() {
        new ClearService().clear();
        RegisterRequest request = new RegisterRequest("user123", "password123", "email", "Josh", "Moody", "M");
        RegisterResult result = new RegisterService().register(request);
        authtoken = result.getAuthtoken();
    }

    @AfterEach
    public void tearDown() {
        new ClearService().clear();
    }

    @Test
    public void getEventsPass() {
        GetAllEventsResult result = new GetAllEventsService().getAllEvents(authtoken);
        assertTrue(result.isSuccess());
        Event[] events = result.getData();
        final int expectedPeople = 31; // 4 generations of people
        final int expectedEvents = expectedPeople * 3 - 1; // 3 events for everyone but user
        assertEquals(events.length, expectedEvents);
    }

    @Test
    public void getEventsInvalidAuth() {
        GetAllEventsResult result = new GetAllEventsService().getAllEvents("bad authtoken");
        assertFalse(result.isSuccess());
        assertNotNull(result.getMessage());
        assertNull(result.getData());
    }

}
