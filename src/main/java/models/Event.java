package models;

public class Event {
    String eventId;
    String associatedUsername;
    String personID;
    String latitude;
    String longitude;
    String country;
    String city;
    String eventType;
    int year;

    public Event(String eventId, Person associatedPerson, String latitude, String longitude, String country, String city, String eventType, int year){
        this.eventId = eventId;
        this.associatedUsername = associatedPerson.associatedUsername;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }
}
