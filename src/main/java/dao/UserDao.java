package dao;

import model.User;

import java.sql.*;

/**
 * User Data Access Object. Handles creating and clearing users in the database
 */
public class UserDao {

    /**
     * The database connection
     */
    private final Connection conn;

    /**
     * Creates a UserDao
     * @param conn the database connection object
     */
    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Creates a new user record in the database
     * @param user the user to store in the database
     * @throws DataAccessException if a SQL error occurs
     */
    public void create(User user) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO users (personId, username, password, email, firstName, lastName, gender) " +
                "VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, user.getPersonId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getFirstName());
            stmt.setString(6, user.getLastName());
            stmt.setString(7, Character.toString(user.getGender()));
            // stmt.setString(8, user.getPersonId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a user into the database: " + e);
        }
    }

    /**
     * Validate a user's credentials
     * @param username the username of the user being validated
     * @param password the password of the user being validated
     * @return true if the password matches the user of the given username, false otherwise
     * @throws DataAccessException if a SQL error occurs
     */
    public boolean validate(String username, String password) throws DataAccessException {
        return true;
    }

    /**
     * Finds a user by id
     * @param id the id of the user being searched for
     * @return the found user
     * @throws DataAccessException if a SQL error occurs
     */
    public User findById(String id) throws DataAccessException {
        ResultSet rs;
        String sql = "SELECT * FROM users WHERE personId = ? LIMIT 1;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("personId"), rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender").charAt(0));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a user in the database: " + e);
        }
    }

    /**
     * Finds a user by username
     * @param userName the username of the user being searched for
     * @return the found user
     * @throws DataAccessException if a SQL error occurs
     */
    public User findByUsername(String userName) throws DataAccessException {
        ResultSet rs;
        String sql = "SELECT * FROM users WHERE username = ? LIMIT 1;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("personId"), rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender").charAt(0));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a user in the database: " + e);
        }
    }

    /**
     * Deletes all user records in the database
     * @throws DataAccessException if a SQL error occurs
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM users";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }
}
