package dao;

import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDaoTest {

    private Database db;
    private Person person;
    private PersonDao dao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        person = new Person("jam436", "joshmoody24", "Josh", "Moody",
                'M', null, null, null);
        Connection conn = db.getConnection();
        dao = new PersonDao(conn);
        dao.clear();
    }

    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(false);
    }

    @Test
    public void createPass() throws DataAccessException {
        dao.create(person);
        Person compareTest = dao.findById(person.getPersonID());
        assertNotNull(compareTest);
        assertEquals(person, compareTest);
    }

    @Test
    public void createFail() throws DataAccessException {
        dao.create(person);
        assertThrows(DataAccessException.class, () -> dao.create(person));
    }

    @Test
    public void findByUsernamePass() throws DataAccessException {
        dao.create(person);
        Person foundPerson = dao.findByAssociatedUsername(person.getAssociatedUsername());
        assertNotNull(foundPerson);
        assertEquals(person, foundPerson);
    }

    @Test
    public void findByUsernameFail() throws DataAccessException {
        dao.create(person);
        Person foundPerson = dao.findByAssociatedUsername("random username");
        assertNull(foundPerson);
    }

    @Test
    public void findByIdPass() throws DataAccessException {
        dao.create(person);
        Person foundPerson = dao.findById(person.getPersonID());
        assertNotNull(foundPerson);
        assertEquals(person, foundPerson);
    }

    @Test
    public void findByIdFail() throws DataAccessException {
        dao.create(person);
        Person foundPerson = dao.findById("random id");
        assertNull(foundPerson);
    }

    @Test
    public void clearTest() throws DataAccessException {
        dao.create(person);
        dao.clear();
        Person foundPerson = dao.findByAssociatedUsername(person.getAssociatedUsername());
        assertNull(foundPerson);
    }
}
