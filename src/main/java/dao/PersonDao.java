package dao;

import java.sql.Connection;
import model.Person;
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

    }

    /**
     * Finds a person in the database with the given id
     * @param id the id of the person being searched for
     * @return the found person
     * @throws DataAccessException if a SQL error occurs
     */
    public Person findById(String id) throws DataAccessException {
        return null;
    }

    /**
     * Deletes all person records from the database
     */
    public void clear(){

    }
}
