package service;

import dao.AuthTokenDao;
import dao.Database;
import dao.PersonDao;
import dao.UserDao;
import model.AuthToken;
import model.Person;
import model.User;
import request.GetAllPersonsResult;
import request.GetPersonResult;

import java.sql.Connection;
import java.util.List;

/**
 * a service that can get a list of all people from the database
 */
public class GetAllPersonsService {

    /**
     * gets all the people in the database
     * @return the object containing all the person data
     */
    public GetAllPersonsResult getAllPersons(String authtoken){
        Database db = new Database();
        try {
            Connection conn = db.openConnection();
            AuthToken token = new AuthTokenDao(conn).findByToken(authtoken);
            if(token == null) throw new DoesNotExistException("Auth token was not found");

            String username = token.getUsername();
            List<Person> people = new PersonDao(conn).findByAssociatedUsername(username);

            db.closeConnection(true);
            return new GetAllPersonsResult(true, null, people.toArray(new Person[0]));
        }
        catch(DoesNotExistException ex){
            db.closeConnection(true);
            return new GetAllPersonsResult(false, "Error: " + ex.getMessage(), null);
        }
        catch(Exception ex){
            ex.printStackTrace();
            db.closeConnection(false);
            return new GetAllPersonsResult(false, "Error: " + ex.getMessage(), null);
        }
    }
}
