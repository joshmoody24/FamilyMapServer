package request;

/**
 * Represents the JSON response for a login request
 */
public class LoginRequest {

    /**
     * the id of the user trying to log in
     */
    String username;

    /**
     * the password of the user trying to log in
     */
    String password;

    /**
     * creates a new LoginRequest
     * @param username the username of the user
     * @param password the password of the user
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
