package request;

/**
 * Represents the JSON response for a "get event" request
 */
public class GetEventResult extends Result {

    /**
     * the associated username for the event
     */
    String associatedUsername;

    /**
     * the unique id of the event
     */
    String eventID;

    /**
     * the unique id of the person associated with the event
     */
    String personID;

    /**
     * latitude of the event
     */
    String latitude;

    /**
     * longitude of the event
     */
    String longitude;

    /**
     * country of the event
     */
    String country;

    /**
     * city of the event
     */
    String city;

    /**
     * type of the event
     */
    String eventType;

    /**
     * year the event took place
     */
    int year;


    /**
     * Creates a new GetEventResult
     * @param success whether the operation was successful
     * @param message if not successful, the error message
     * @param associatedUsername the username of the user connected to the event
     * @param eventID the id of the event
     * @param personID the id of the person connected to the event
     * @param latitude the latitude of the event
     * @param longitude the longitude of the vent
     * @param country the country of the event
     * @param city the city of the event
     * @param eventType the type of event
     * @param year the year the event took place
     */
    public GetEventResult(boolean success, String message, String associatedUsername, String eventID, String personID, String latitude, String longitude, String country, String city, String eventType, int year) {
        super(success, message);
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }
    public String getPersonID() {
        return personID;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }

}
