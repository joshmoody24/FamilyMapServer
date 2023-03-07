package service;

import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.*;

import static org.junit.jupiter.api.Assertions.*;

public class GetPersonServiceTest {
    String authtoken;
    String personID;
    String username;

    @BeforeEach
    public void setUp() {
        new ClearService().clear();
        RegisterRequest request = new RegisterRequest("user123", "password123", "email", "Josh", "Moody", "M");
        RegisterResult result = new RegisterService().register(request);
        authtoken = result.getAuthtoken();
        personID = result.getPersonId();
        username = result.getUsername();
    }

    @AfterEach
    public void tearDown() {
        new ClearService().clear();
    }

    @Test
    public void getPersonPass() {
        GetPersonRequest request = new GetPersonRequest(personID);
        GetPersonResult result = new GetPersonService().getPerson(request, authtoken);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPersonID());
        assertEquals(result.getAssociatedUsername(), username);
        assertNull(result.getMessage());
    }

    @Test
    public void getNonexistentPerson() {
        GetPersonRequest request = new GetPersonRequest("nonexistent id");
        GetPersonResult result = new GetPersonService().getPerson(request, authtoken);
        assertFalse(result.isSuccess());
        assertNotNull(result.getMessage());
        assertNull(result.getGender());
    }

    @Test
    public void getPersonWithoutAuth() {
        GetPersonRequest request = new GetPersonRequest(personID);
        GetPersonResult result = new GetPersonService().getPerson(request, "bad auth");
        assertFalse(result.isSuccess());
        assertNotNull(result.getMessage());
        assertTrue(result.getMessage().toLowerCase().contains("auth"));
        assertNull(result.getGender());
    }
}
