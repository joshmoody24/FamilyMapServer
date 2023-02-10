package request;

/**
 * Represents the JSON request for registering a new user
 */
public class RegisterRequest {

    /**
     * the username of the new user
     */
    String username;

    /**
     * the password of the new user
     */
    String password;

    /**
     * the email address of the new user
     */
    String email;

    /**
     * the first name of the new user
     */
    String firstName;

    /**
     * the last name of the new user
     */
    String lastName;

    /**
     * the gender of the new user ('m' or 'f')
     */
    String gender;

    /**
     * Creates a new RegisterRequest
     * @param username the user's username
     * @param password the user's password
     * @param email the user's email
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param gender the user's gender
     */

    public RegisterRequest(String username, String password, String email, String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }
}
