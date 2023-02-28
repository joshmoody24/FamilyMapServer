package dao;

import java.sql.Connection;
import model.Person;
import model.User;

import javax.xml.crypto.Data;
import java.sql.*;

/**
 * Person Data Access Object. Handles creating and clearing people records in the database
 */
public class PersonDao {

    /**
     * The database connection
     */
    private final Connection conn;

    /**
     * Creates a PersonDao
     * @param conn the database connection object
     */
    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Stores a person in the database
     * @param person the person to create a database record for
     * @throws DataAccessException if a SQL error occurs
     */
    public void create(Person person) throws DataAccessException {
        String sql = "INSERT INTO persons (personId, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID) " +
                "VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, Character.toString(person.getGender()));
            stmt.setString(6, person.getFatherId());
            stmt.setString(7, person.getMotherId());
            stmt.setString(8, person.getSpouseId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a person into the database: " + e);
        }
    }

    /**
     * Finds a person in the database with the given id
     * @param id the id of the person being searched for
     * @return the found person
     * @throws DataAccessException if a SQL error occurs
     */
    public Person findById(String id) throws DataAccessException {
        String event;
        ResultSet rs;
        String sql = "SELECT * FROM persons WHERE personId = ? LIMIT 1;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Person person = new Person(rs.getString("personId"), rs.getString("associatedUsername"), rs.getString("firstName"),
                        rs.getString("lastName"), rs.getString("gender").charAt(0), rs.getString("fatherId"),
                        rs.getString("motherId"), rs.getString("spouseId"));
                return person;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a person in the database: " + e);
        }
    }

    public Person findByAssociatedUsername(String username) throws DataAccessException {
        String event;
        ResultSet rs;
        String sql = "SELECT * FROM persons WHERE associatedUsername = ? LIMIT 1;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Person person = new Person(rs.getString("personId"), rs.getString("associatedUsername"), rs.getString("firstName"),
                    rs.getString("lastName"), rs.getString("gender").charAt(0), rs.getString("fatherId"),
                    rs.getString("motherId"), rs.getString("spouseId"));
                return person;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a person in the database: " + e);
        }
    }

    /**
     * Deletes all person records from the database
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Persons";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing persons table: " + e);
        }
    }
}
