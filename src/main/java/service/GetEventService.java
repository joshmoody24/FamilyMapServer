package service;

import dao.*;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import request.GetEventResult;
import request.GetEventRequest;
import request.GetPersonResult;

import java.sql.Connection;

/**
 * A service that gets data for a single event from the database
 */
public class GetEventService {

    /**
     * gets an event from the database
     * @param r the request data
     * @return the event data
     */
    public GetEventResult getEvent(GetEventRequest r, String authtoken){
        Database db = new Database();
        try {
            Connection conn = db.openConnection();

            // make sure authtoken is valid and that the user is authorized to view this event
            AuthToken token = new AuthTokenDao(conn).findByToken(authtoken);
            if(token == null) throw new AuthorizationException("Invalid authtoken");
            User requester = new UserDao(conn).findByUsername(token.getUsername());
            if(requester == null) throw new AuthorizationException("Invalid authtoken");
            Event e = new EventDao(conn).find(r.getEventID());
            if(e == null) throw new DoesNotExistException("Event does not exist");
            if(e.getAssociatedUsername().equals(requester.getUsername()) == false) throw new AuthorizationException("User is not authorized to view this event");

            db.closeConnection(true);
            return new GetEventResult(true, null, e.getAssociatedUsername(), e.getEventID(), e.getPersonID(), e.getLatitude(), e.getLongitude(), e.getCountry(), e.getCity(), e.getEventType(), e.getYear());
        }
        catch(DoesNotExistException ex){
            db.closeConnection(false);
            return failedResult(ex.getMessage());
        }
        catch(AuthorizationException ex){
            db.closeConnection(false);
            return failedResult(ex.getMessage());
        }
        catch(Exception ex){
            ex.printStackTrace();
            db.closeConnection(false);
            return failedResult("Getting person data failed");
        }
    }

    private GetEventResult failedResult(String message){
        return new GetEventResult(false, "Error: " + message, null, null, null, null, null, null, null, null, null);
    }
}
