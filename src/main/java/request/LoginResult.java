package request;


/**
 * represents the JSON response to a client who tried to log in
 */
public class LoginResult extends Result {

    /**
     * the auth token of the validated user
     */
    String authtoken;

    /**
     * the username of the user
     */
    String username;

    /**
     * The id of the person record associated with the user
     */
    String personID;

    /**
     * creates a new LoginResult
     * @param success whether the operation was successful
     * @param message if not successful, the error message
     * @param authtoken the authtoken of the user
     * @param username the username of the user
     * @param personId the id of the person record associated with the user
     */

    public LoginResult(boolean success, String message, String authtoken, String username, String personId) {
        super(success, message);
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personId;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonID() {
        return personID;
    }
}
