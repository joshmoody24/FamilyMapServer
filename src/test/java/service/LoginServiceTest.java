package service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.LoginResult;
import request.RegisterRequest;
import request.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {

    @BeforeEach
    public void setUp() {
        new ClearService().clear();
        RegisterRequest request = new RegisterRequest("user123", "password123", "email", "Josh", "Moody", "M");
        new RegisterService().register(request);
    }

    @AfterEach
    public void tearDown() {
        new ClearService().clear();
    }

    @Test
    public void LoginPass(){
        LoginRequest request = new LoginRequest("user123", "password123");
        LoginResult result = new LoginService().login(request);
        assertTrue(result.isSuccess());
        assertNotNull(result.getAuthtoken());
        assertEquals("user123", result.getUsername());
    }

    @Test
    public void LoginWithInvalidPassword(){
        LoginRequest request = new LoginRequest("user123", "bad password");
        LoginResult result = new LoginService().login(request);
        assertFalse(result.isSuccess());
        assertNull(result.getAuthtoken());
        assertTrue(result.getMessage().toLowerCase().contains("error"));
    }
}
