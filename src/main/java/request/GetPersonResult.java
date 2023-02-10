package request;

/**
 * Represents the JSON response for a "get person" request
 */
public class GetPersonResult extends Result {

    /**
     * the username of the associated user
     */
    String associatedUsername;

    /**
     * the unique id of the person
     */
    String personID;

    /**
     * the first name of the person
     */
    String firstName;

    /**
     * the person's last name
     */
    String lastName;

    /**
     * the gender of the person ('m' or 'f')
     */
    String gender;

    /**
     * the id of the person's father (can be null)
     */
    String fatherID;

    /**
     * the id of the person's mother (can be null)
     */
    String motherID;

    /**
     * the id of the person's spouse (can be null)
     */
    String spouseID;

    /**
     * Create a new GetPersonResult
     * @param success whether the operation was successful
     * @param message if not successful, the error message
     * @param associatedUsername the username of the associated user
     * @param personID the id of the person
     * @param firstName the first name of the person
     * @param lastName the last name of the person
     * @param gender the person's gender ('m' or 'f')
     * @param fatherID the id of the person's father
     * @param motherID the id of the person's mother
     * @param spouseID the id of the persn's spouse
     */
    public GetPersonResult(boolean success, String message, String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        super(success, message);
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getPersonID() {
        return personID;
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

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }
}
