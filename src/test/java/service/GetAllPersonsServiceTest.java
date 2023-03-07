package service;

import model.Event;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.GetAllEventsResult;
import request.GetAllPersonsResult;
import request.RegisterRequest;
import request.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GetAllPersonsServiceTest {
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
    public void getPersonsPass() {
        GetAllPersonsResult result = new GetAllPersonsService().getAllPersons(authtoken);
        assertTrue(result.isSuccess());
        Person[] persons = result.getData();
        final int expectedPeople = 31; // 4 generations of people
        assertEquals(persons.length, expectedPeople);
    }

    @Test
    public void getPersonsInvalidAuth() {
        GetAllEventsResult result = new GetAllEventsService().getAllEvents("bad authtoken");
        assertFalse(result.isSuccess());
        assertNotNull(result.getMessage());
        assertNull(result.getData());
    }
}
