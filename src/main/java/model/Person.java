package model;

import java.util.Objects;

/**
 * A person in a geneological tree
 */
public class Person {

    /**
     * The unique identifier for this person
     */
    String personID;

    /**
     * The associated username of this person
     */
    String associatedUsername;

    /**
     * The persons's first name
     */
    String firstName;

    /**
     * the person's last name
     */
    String lastName;

    /**
     * the person's gender ('m' or 'f')
     */
    char gender;

    /**
     * The id of the person's father (can be null)
     */
    String fatherID;

    /**
     * The id of the person's mother (can be null)
     */
    String motherID;

    /**
     * the id of the person's spouse (can be null)
     */
    String spouseID;

    /**
     * Creates a new person
     * @param personID the person's id
     * @param associatedUsername the username of the user associated with the person
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @param gender the person's gender
     * @param fatherId the person's father
     * @param motherId the person's mother
     * @param spouseId the person's spouse
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, char gender, String fatherId, String motherId, String spouseId){
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherId;
        this.motherID = motherId;
        this.spouseID = spouseId;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID, personID);
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return gender == person.gender && Objects.equals(associatedUsername, person.associatedUsername) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(fatherID, person.fatherID) && Objects.equals(motherID, person.motherID) && Objects.equals(spouseID, person.spouseID) && Objects.equals(personID, person.personID);
    }

}
