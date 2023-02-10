package models;

public class Person {
    String personId;
    String associatedUsername;
    String firstName;
    String lastName;
    char gender;
    String fatherId;
    String motherId;
    String spouseId;

    public Person(String personId, String associatedUsername, String firstName, String lastName, char gender, String fatherId, String motherId, String spouseId){
        this.personId = personId;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherId = fatherId;
        this.motherId = motherId;
        this.spouseId = spouseId;
    }
}
