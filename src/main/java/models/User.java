package models;

public class User {
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    char gender;
    String personId;

    public User(String username, String password, String email, String firstName, String lastName, char gender){
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }
}
