package request;

/**
 * Represents the JSON response after a user has been registered
 */
public class RegisterResult extends Result {


    /**
     * the auth token of the validated user
     */
    String authtoken;

    /**
     * the username of the validated user
     */
    String username;

    /**
     * the id of the validated user's person record
     */
    String personID;

    /**
     * Creates a new RegisterResult
     * @param success whether the operation was successful
     * @param message if not successful, the error message
     * @param authtoken the auth token of the validated user
     * @param username the username of the validated user
     * @param personId the id of the person record attached to the validated user
     */
    public RegisterResult(boolean success, String message, String authtoken, String username, String personId) {
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

    public String getPersonId() {
        return personID;
    }
}
