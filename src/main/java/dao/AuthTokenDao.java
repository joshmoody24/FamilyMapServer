package dao;

import model.AuthToken;
import model.Person;
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
        String sql = "INSERT INTO authtokens (authtoken, username) " +
                "VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, token.getAuthtoken());
            stmt.setString(2, token.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an authtoken into the database: " + e);
        }
    }

    /**
     * Finds an AuthToken in the database with the given id
     * @param id the unique id of the authtoken
     * @return the found AuthToken
     * @throws DataAccessException if a SQL error occurs
     */
    public AuthToken findById(String id) throws DataAccessException {
        String event;
        ResultSet rs;
        String sql = "SELECT * FROM authtokens WHERE authtoken = ? LIMIT 1;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                AuthToken token = new AuthToken(rs.getString("authtoken"), rs.getString("username"));
                return token;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an authtoken in the database: " + e);
        }
    }

    /**
     * Finds an AuthToken in the database associated with the given user
     * If multiple exist, it returns the first one it finds
     * @param user the user you want to find an existing AuthToken for
     * @return the found AuthToken
     * @throws DataAccessException if a SQL error occurs
     */
    public AuthToken findByUser(User user) throws DataAccessException {
        String event;
        ResultSet rs;
        String sql = "SELECT * FROM authtokens WHERE username = ? LIMIT 1;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            rs = stmt.executeQuery();
            if (rs.next()) {
                AuthToken token = new AuthToken(rs.getString("authtoken"), rs.getString("username"));
                return token;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an authtoken in the database: " + e);
        }
    }

    /**
     * Deletes all the AuthTokens in the database
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM authtokens";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing authtokens table: " + e);
        }
    }
}
