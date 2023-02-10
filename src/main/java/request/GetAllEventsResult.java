package request;
import model.Event;

/**
 * Represents the JSON response for a "get all events" request
 */
public class GetAllEventsResult extends Result {

    /**
     * List of events being sent to the client
     */
    Event[] data;

    /**
     * Creates a new GetAllEventsResult
     * @param success whether the operation was successful
     * @param message if not successful, the error message
     * @param data the events to send to client
     */
    public GetAllEventsResult(boolean success, String message, Event[] data) {
        super(success, message);
        this.data = data;
    }

    public Event[] getData() {
        return data;
    }
}
