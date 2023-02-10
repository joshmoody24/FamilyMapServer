package request;
import model.Person;

/**
 * Represents the JSON response for a "get all persons" request
 */
public class GetAllPersonsResult extends Result {

    /**
     * The person records being sent to the client
     */
    Person[] data;

    /**
     * Creates a new GetAllPersonsResult
     * @param success whether the operation was successful
     * @param message if not successful, the error message
     * @param data the people being sent to the client
     */
    public GetAllPersonsResult(boolean success, String message, Person[] data) {
        super(success, message);
        this.data = data;
    }

    public Person[] getData() {
        return data;
    }
}
