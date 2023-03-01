package service;

import dao.*;
import model.Person;
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
    public GetPersonResult getPerson(GetPersonRequest request){
        GetPersonResult result;
        try {
            Database db = new Database();
            Connection conn = db.openConnection();
            Person p = new PersonDao(conn).findById(request.getPersonID());
            if(p == null) throw new DoesNotExistException("Person does not exist");
            result = new GetPersonResult(true, null, p.getAssociatedUsername(), p.getPersonID(), p.getFirstName(), p.getLastName(), Character.toString(p.getGender()), p.getFatherId(), p.getMotherId(), p.getSpouseId());
        }
        catch(DoesNotExistException ex){
            result = new GetPersonResult(false, ex.getMessage(), null, null, null, null, null, null, null, null);
        }
        catch(Exception ex){
            ex.printStackTrace();
            result = new GetPersonResult(false, "Error: getting person data failed", null, null, null, null, null, null, null, null);
        }
        return result;
    }
}
