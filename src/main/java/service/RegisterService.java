package service;

import dao.AuthTokenDao;
import dao.Database;
import dao.PersonDao;
import dao.UserDao;
import model.AuthToken;
import model.Person;
import model.User;
import request.RegisterRequest;
import request.RegisterResult;

import java.sql.Connection;
import java.util.UUID;

/**
 * a service that registers new users
 */
public class RegisterService {

    /**
     * registers a new user
     * @param r the request data
     * @return the result data
     */
    public RegisterResult register(RegisterRequest r){
        Database db = new Database();
        try {
            System.out.println("Registering user: " + r.getUsername());
            Connection conn = db.openConnection();
            UserDao userDao = new UserDao(conn);
            UUID personId = UUID.randomUUID();
            userDao.create(new User(personId.toString(), r.getUsername(), r.getPassword(), r.getEmail(), r.getFirstName(), r.getLastName(), r.getGender().charAt(0)));
            new PersonDao(conn).create(new Person(personId.toString(), r.getUsername(), r.getFirstName(), r.getLastName(), r.getGender().charAt(0), null, null, null));
            UUID authToken = UUID.randomUUID();
            AuthTokenDao authDao = new AuthTokenDao(conn);
            authDao.create(new AuthToken(authToken.toString(), r.getUsername()));
            db.closeConnection(true);
            return new RegisterResult(true, null, authToken.toString(), r.getUsername(), personId.toString());
        }
        catch(Exception e){
            e.printStackTrace();
            db.closeConnection(false);
            return new RegisterResult(false, "Error: " + e.getMessage(), null, null, null);
        }
    }
}
