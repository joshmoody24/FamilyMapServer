package service;

import dao.Database;
import dao.EventDao;
import dao.PersonDao;
import dao.UserDao;
import model.Event;
import model.Person;
import model.User;
import request.LoadRequest;
import request.LoginRequest;
import request.LoginResult;
import request.Result;

import java.sql.Connection;

/**
 * a service that logs in users
 */
public class LoadService {

    /**
     * validates a user and logs them in
     * @param l the user credentials in a loginrequest object
     * @return the login result data
     */
    public Result load(LoadRequest l){
        Database db = new Database();
        try{
            Connection conn = db.openConnection();
            ClearService clearService = new ClearService();
            clearService.clear();

            // create all the objects in the request

            UserDao userDao = new UserDao(conn);
            for(User u : l.getUsers()){
                userDao.create(u);
            }

            PersonDao personDao = new PersonDao(conn);
            for(Person p : l.getPersons()){
                personDao.create(p);
            }

            EventDao eventDao = new EventDao(conn);
            for(Event e : l.getEvents()){
                eventDao.create(e);
            }

            db.closeConnection(true);
            return new Result(true, "Successfully added " + l.getUsers().length + " users, " + l.getPersons().length + " persons, and " + l.getEvents().length + " events to the database.");
        }
        catch(Exception ex){
            ex.printStackTrace();
            db.closeConnection(false);
            return new Result(false, "Error: " + ex.getMessage());
        }
    }
}
