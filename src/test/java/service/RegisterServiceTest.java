package service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import request.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {

    @BeforeEach
    public void setUp() {
        new ClearService().clear();
    }

    @AfterEach
    public void tearDown() {
        new ClearService().clear();
    }

    @Test
    public void RegisterPass() {
        RegisterRequest request = new RegisterRequest("user123", "password123", "email", "Josh", "Moody", "M");
        RegisterResult result = new RegisterService().register(request);
        assertTrue(result.isSuccess());
        assertNotNull(result.getAuthtoken());
        assertEquals("user123", result.getUsername());
    }

    @Test
    public void RegisterWithoutUsername() {
        RegisterRequest request = new RegisterRequest(null, "password123", "email", "Josh", "Moody", "M");
        RegisterResult result = new RegisterService().register(request);
        assertFalse(result.isSuccess());
        assertNull(result.getAuthtoken());
        assertTrue(result.getMessage().toLowerCase().contains("error"));
    }
}
