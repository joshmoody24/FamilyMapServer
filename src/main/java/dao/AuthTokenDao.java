package dao;

import model.AuthToken;
import model.User;

import java.sql.*;

/**
 * Auth Token Data Access Object. Handles creating and clearing authtokens in the database
 */
public class AuthTokenDao {

    /**
     * The database connection
     */
    private final Connection conn;

    /**
     * Creates an AuthTokenDao
     * @param conn the database connection object
     */
    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Creates a new authtoken and stores it in the database
     * @param token the token object to be stored in the database
     * @throws DataAccessException if a SQL error occurs
     */
    public void create(AuthToken token) throws DataAccessException {

    }

    /**
     * Finds an AuthToken in the database with the given id
     * @param id the unique id of the authtoken
     * @return the found AuthToken
     * @throws DataAccessException if a SQL error occurs
     */
    public AuthToken findById(String id) throws DataAccessException {
        return null;
    }

    /**
     * Finds an AuthToken in the database associated with the given user
     * If multiple exist, it returns the first one it finds
     * @param user the user you want to find an existing AuthToken for
     * @return the found AuthToken
     * @throws DataAccessException if a SQL error occurs
     */
    public AuthToken findByUser(User user) throws DataAccessException {
        return null;
    }

    /**
     * Deletes all the AuthTokens in the database
     */
    public void clear(){

    }
}
