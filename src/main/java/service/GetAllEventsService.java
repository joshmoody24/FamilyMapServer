package service;

import dao.AuthTokenDao;
import dao.Database;
import dao.EventDao;
import dao.PersonDao;
import model.AuthToken;
import model.Event;
import model.Person;
import request.GetAllEventsResult;
import request.GetAllPersonsResult;
import request.GetPersonResult;

import java.sql.Connection;
import java.util.List;


/**
 * a service that can get a list of all events in the database
 */
public class GetAllEventsService {

    /**
     * gets all events from the database and returns a result object
     * @return the object containing the data for all the events
     */
    public GetAllEventsResult getAllEvents(String authtoken){
        GetAllEventsResult result;
        Database db = new Database();
        try {
            Connection conn = db.openConnection();
            AuthToken token = new AuthTokenDao(conn).findByToken(authtoken);
            if(token == null) throw new DoesNotExistException("Auth token was not found");
            String username = token.getUsername();
            List<Event> events = new EventDao(conn).findForUser(username);
            db.closeConnection(true);
            return new GetAllEventsResult(true, null, events.toArray(new Event[0]));
        }
        catch(DoesNotExistException ex){
            db.closeConnection(true);
            return new GetAllEventsResult(false, "Error: " + ex.getMessage(), null);
        }
        catch(Exception ex){
            ex.printStackTrace();
            db.closeConnection(false);
            return new GetAllEventsResult(false, "Error: " + ex.getMessage(), null);
        }
    }
}
