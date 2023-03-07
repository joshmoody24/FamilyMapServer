package service;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import model.AuthToken;
import model.Event;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.FillRequest;
import request.FillResult;
import request.RegisterRequest;
import request.RegisterResult;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest {
    private String authtoken;
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
    public void hasCorrectFillCounts() {
        FillRequest request = new FillRequest("user123", 4);
        new FillService().fill(request);
        Person[] people = new GetAllPersonsService().getAllPersons(authtoken).getData();
        Event[] events = new GetAllEventsService().getAllEvents(authtoken).getData();
        final int expectedPeople = 31;
        assertEquals(expectedPeople, people.length);
        assertEquals(expectedPeople * 3 - 1, events.length); // user isn't married or dead
    }

    // the best "alternate" test case I can think of
    // for this extremely simple service
    @Test
    public void invalidGenerationCount() {
        FillRequest request = new FillRequest("user123", -1);
        FillResult result = new FillService().fill(request);
        assertFalse(result.isSuccess());
        assertNotNull(result.getMessage());
    }
}
