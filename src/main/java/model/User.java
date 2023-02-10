package model;

import java.util.Objects;

/**
 * A user of the application
 */
public class User {

    /**
     * The id of the person this user is associated with
     * (it also functions as the unique identifier for the user
     */
    String personID;

    /**
     * The unique username for this user
     */
    String username;

    /**
     * The user's password
     */
    String password;

    /**
     * The user's email address
     */
    String email;

    /**
     * The user's first name
     */
    String firstName;

    /**
     * The user's last name
     */
    String lastName;

    /**
     * The user's gender ('m' or 'f')
     */
    char gender;

    /**
     * Creates a new user
     * @param personID
     * @param username the user's username
     * @param password the user's password
     * @param email the user's email address
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param gender the user's gender
     */

    public User(String personID, String username, String password, String email, String firstName, String lastName, char gender){
        this.personID = personID;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getPersonId() {
        return personID;
    }

    public void setPersonId(String personId) {
        this.personID = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return gender == user.gender && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(personID, user.personID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, firstName, lastName, gender, personID);
    }
}
