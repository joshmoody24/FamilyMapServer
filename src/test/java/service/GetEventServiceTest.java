package service;

import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.*;

import static org.junit.jupiter.api.Assertions.*;

public class GetEventServiceTest {
    String authtoken;
    String username;
    String personID;

    @BeforeEach
    public void setUp() {
        new ClearService().clear();
        RegisterRequest request = new RegisterRequest("user123", "password123", "email", "Josh", "Moody", "M");
        RegisterResult result = new RegisterService().register(request);
        authtoken = result.getAuthtoken();
        username = result.getUsername();
        personID = result.getPersonId();
    }

    @AfterEach
    public void tearDown() {
        new ClearService().clear();
    }

    @Test
    public void getEventPass() {

        // just get some random event attached to the user's person record
        // to do some testing on
        Event[] events = new GetAllEventsService().getAllEvents(authtoken).getData();
        Event firstEvent =  null;
        for (Event e : events){
            if(e.getPersonID().equals(personID)) firstEvent = e;
        }
        assertNotNull(firstEvent);

        GetEventRequest request = new GetEventRequest(firstEvent.getEventID());
        GetEventResult result = new GetEventService().getEvent(request, authtoken);
        assertTrue(result.isSuccess());
        assertNotNull(result.getEventID());
        assertEquals(result.getAssociatedUsername(), username);
        assertNull(result.getMessage());
    }

    @Test
    public void getNonexistentEvent() {
        GetEventRequest request = new GetEventRequest("nonexistent id");
        GetEventResult result = new GetEventService().getEvent(request, authtoken);
        assertFalse(result.isSuccess());
        assertNotNull(result.getMessage());
        assertNull(result.getCity());
    }

    @Test
    public void getEventWithoutAuth() {
        GetEventRequest request = new GetEventRequest("this value doesn't matter");
        GetEventResult result = new GetEventService().getEvent(request, "bad auth token");
        assertFalse(result.isSuccess());
        assertNotNull(result.getMessage());
        assertTrue(result.getMessage().toLowerCase().contains("error"));
        assertTrue(result.getMessage().toLowerCase().contains("auth"));
        assertNull(result.getCity());
    }
}
