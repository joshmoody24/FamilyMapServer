package request;

/**
 * Represents a JSON request for a "get person" operation
 */
public class GetPersonRequest {

    /**
     * the id of the person being requested
     */
    String personID;

    /**
     * Creates a new GetPersonRequest
     * @param personID the id of the person being requested
     */
    public GetPersonRequest(String personID) {
        this.personID = personID;
    }

    public String getPersonID() {
        return personID;
    }

}
