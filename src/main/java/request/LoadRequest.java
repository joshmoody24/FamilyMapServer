package request;
import model.User;
import model.Person;
import model.Event;

/**
 * Represents the JSON request for a "load" operation
 */
public class LoadRequest {

    /**
     * all the data for the users being loaded
     */
    User[] users;

    /**
     * all the data for the people being loaded
     */
    Person[] persons;

    /**
     * all the data for the events being loaded
     */
    Event[] events;

    /**
     * Creates a new LoadRequest
     * @param users the data for the users being loaded
     * @param persons the data for the people being loaded
     * @param events the data for the events being loaded
     */

    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
