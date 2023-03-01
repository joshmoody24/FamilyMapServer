package model;

import java.util.Objects;

/**
 * Represents an AuthToken used to validate whether a user has the authority to do certain things in the application)
 */
public class AuthToken {
    /**
     * the unique authorization token string
     */
    String authtoken;

    /**
     * the username of the associated user
     */
    String username;

    /**
     * Creates a new AuthToken
     * @param authtoken a unique identifier for the authtoken (uuid)
     * @param username the username of the associated user
     */
    public AuthToken(String authtoken, String username){
        this.authtoken = authtoken;
        this.username = username;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return Objects.equals(authtoken, authToken.authtoken) && Objects.equals(username, authToken.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authtoken, username);
    }
}
