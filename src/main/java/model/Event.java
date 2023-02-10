package model;

import java.util.Objects;

/**
 * An event in a person's life, e.g. birth, baptism, etc.
 */
public class Event {

    /**
     * A unique identifier for the event
     */
    String eventID;

    /**
     * The username of the person the event is associated with
     */
    String associatedUsername;

    /**
     * Unique identifier of the person the event is associated with
     */
    String personID;

    /**
     * The latitude of the location where the event took place
     */
    float latitude;

    /**
     * The longitude of the location where the event took place
     */
    float longitude;

    /**
     * The country where the event took place
     */
    String country;

    /**
     * The city where the event took place
     */
    String city;

    /**
     * The event type (can be any string, e.g. "baptism")
     */
    String eventType;

    /**
     * The year in which the event took place
     */
    int year;

    /**
     * Creates a new event
     * @param eventID the event id
     * @param username the username of the associated user
     * @param personID the id of the associated person
     * @param latitude the latitude of the event location
     * @param longitude the longitude of the event location
     * @param country the country of the event location
     * @param city the city of the event location
     * @param eventType the type of event
     * @param year the year the event took place
     */
    public Event(String eventID, String username, String personID, Float latitude, Float longitude,
                 String country, String city, String eventType, Integer year) {
        this.eventID = eventID;
        this.associatedUsername = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventID, event.eventID) && Objects.equals(associatedUsername, event.associatedUsername) && Objects.equals(personID, event.personID) && Objects.equals(latitude, event.latitude) && Objects.equals(longitude, event.longitude) && Objects.equals(country, event.country) && Objects.equals(city, event.city) && Objects.equals(eventType, event.eventType) && Objects.equals(year, event.year);
    }
}
