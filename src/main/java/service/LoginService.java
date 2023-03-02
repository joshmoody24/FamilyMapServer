package service;

import dao.AuthTokenDao;
import dao.Database;
import dao.UserDao;
import model.AuthToken;
import model.User;
import request.LoginRequest;
import request.LoginResult;
import request.RegisterResult;

import java.sql.Connection;
import java.util.UUID;

/**
 * a service that logs in users
 */
public class LoginService {

    /**
     * validates a user and logs them in
     * @param l the user credentials in a loginrequest object
     * @return the login result data
     */
    public LoginResult login(LoginRequest l){
        Database db = new Database();
        try {
            Connection conn = db.openConnection();
            UserDao userDao = new UserDao(conn);
            User user = userDao.findByUsername(l.getUsername());

            if(user == null) throw new DoesNotExistException("User with username " + l.getUsername() + " does not exist");
            if(user.getPassword().equals(l.getPassword()) == false){
                System.out.println("password in database (" + user.getPassword() + ") does not match password in request (" + l.getPassword() + ")");
                throw new DoesNotExistException("Invalid password");
            }

            UUID authToken = UUID.randomUUID();
            AuthTokenDao authDao = new AuthTokenDao(conn);
            authDao.create(new AuthToken(authToken.toString(), l.getUsername()));
            db.closeConnection(true);
            return new LoginResult(true, null, authToken.toString(), user.getUsername(), user.getPersonId());
        }
        catch(DoesNotExistException e){
            db.closeConnection(true);
            return new LoginResult(false, "Error: " + e.getMessage(), null, null, null);
        }
        catch(Exception e){
            e.printStackTrace();
            db.closeConnection(false);
            return new LoginResult(false, "Error: " + e.getMessage(), null, null, null);
        }
    }
}
