package dao;

import java.sql.Connection;
import model.Person;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a person into the database: " + e);
        }
    }

    /**
     * Used to update a person's parents after the person's family tree has been generated
     * @param person the person to update parents for (assuming their fatherID or motherID has been changed).
     * @throws DataAccessException
     */
    public void updateParents(Person person) throws DataAccessException {
        String sql = "UPDATE persons SET motherId = ?, fatherId = ? WHERE personId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getMotherID());
            stmt.setString(2, person.getFatherID());
            stmt.setString(3, person.getPersonID());
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

    /**
     * Find all people for a given user
     * @param username the user to find people for
     * @return list of people associated with the user
     * @throws DataAccessException
     */
    public List<Person> findByAssociatedUsername(String username) throws DataAccessException {
        List<Person> people = new ArrayList<>();
        String event;
        ResultSet rs;
        String sql = "SELECT * FROM persons WHERE associatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Person person = new Person(rs.getString("personId"), rs.getString("associatedUsername"), rs.getString("firstName"),
                        rs.getString("lastName"), rs.getString("gender").charAt(0), rs.getString("fatherId"),
                        rs.getString("motherId"), rs.getString("spouseId"));
                people.add(person);
            }
            return people;
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

    /**
     * Clears the database of all person records associated with a user (except the user's own person record)
     * @param user the user to clear out
     * @throws DataAccessException
     */
    public void clearGenealogyForUser(User user) throws DataAccessException {
        String sql = "DELETE FROM Persons where associatedUsername = ? and personId != ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPersonId());
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing persons table for user " + user.getUsername() + ": " + e);
        }
    }
}
