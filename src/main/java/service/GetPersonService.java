package service;

import dao.*;
import model.AuthToken;
import model.Person;
import model.User;
import request.GetEventResult;
import request.GetPersonRequest;
import request.GetPersonResult;

import java.sql.Connection;

/**
 * a service that gets data for a single person from the database
 */
public class GetPersonService {

    /**
     * gets a person from the database
     * @param p the request data for the person
     * @return the resulting person data
     */
    public GetPersonResult getPerson(GetPersonRequest request, String authtoken){
        Database db = new Database();
        try {
            Connection conn = db.openConnection();

            // make sure authtoken is valid and that the user is authorized to view this person
            AuthToken token = new AuthTokenDao(conn).findByToken(authtoken);
            if(token == null) throw new AuthorizationException("Invalid authtoken");
            User requester = new UserDao(conn).findByUsername(token.getUsername());
            if(requester == null) throw new AuthorizationException("Invalid authtoken");
            Person p = new PersonDao(conn).findById(request.getPersonID());
            if(p == null) throw new DoesNotExistException("Person does not exist");
            if(p.getAssociatedUsername().equals(requester.getUsername()) == false) throw new AuthorizationException("User is not authorized to view this person");

            db.closeConnection(true);
            return new GetPersonResult(true, null, p.getAssociatedUsername(), p.getPersonID(), p.getFirstName(), p.getLastName(), Character.toString(p.getGender()), p.getFatherID(), p.getMotherID(), p.getSpouseID());
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

    private GetPersonResult failedResult(String message){
        return new GetPersonResult(false, "Error: " + message, null, null, null, null, null, null, null, null);
    }
}
