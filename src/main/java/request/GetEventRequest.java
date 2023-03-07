package request;

/**
 * Represents the request for a "get event" request
 */
public class GetEventRequest {
    /**
     * The id for the event being requested
     */
    String eventID;

    /**
     * Creates a new GetEventRequest
     * @param eventID the id of the event being requested
     */
    public GetEventRequest(String eventID) {
        this.eventID = eventID;
    }

    public String getEventID() {
        return eventID;
    }
}
