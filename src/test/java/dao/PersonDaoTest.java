package dao;

import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

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
        List<Person> foundPersons = dao.findByAssociatedUsername(person.getAssociatedUsername());
        assertNotNull(foundPersons);
        assertEquals(1, foundPersons.size());
    }

    @Test
    public void findByUsernameFail() throws DataAccessException {
        dao.create(person);
        List<Person> foundPersons = dao.findByAssociatedUsername("nonexistent username");
        assertNotNull(foundPersons);
        assertEquals(0, foundPersons.size());
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
    public void updateParentsPass() throws DataAccessException {
        dao.create(person);
        Person mom = new Person("mom123", "joshmoody24", "April", "Moody",
            'F', null, null, null);
        Person dad = new Person("dad123", "joshmoody24", "Dallan", "Moody",
                'M', null, null, null);
        person.setMotherID(mom.getPersonID());
        person.setFatherID(dad.getPersonID());
        dao.create(mom);
        dao.create(dad);
        dao.updateParents(person);

        // fetch child from database again to see if it updated correctly
        Person child = dao.findById(person.getPersonID());
        assertNotNull(child);
        assertEquals("mom123", child.getMotherID());
        assertEquals("dad123", child.getFatherID());
    }

    @Test
    public void updateParentsWithNullValues() throws DataAccessException {
        dao.create(person);
        person.setMotherID(null);
        person.setFatherID(null);
        assertDoesNotThrow(() -> dao.updateParents(person));

        // fetch child from database again to see if it updated correctly
        Person foundPerson = dao.findById(person.getPersonID());
        assertNotNull(foundPerson);
        assertEquals(null, foundPerson.getMotherID());
        assertEquals(null, foundPerson.getFatherID());
    }

    @Test
    public void clearTest() throws DataAccessException {
        dao.create(person);
        dao.clear();
        Person foundPerson = dao.findById(person.getPersonID());
        assertNull(foundPerson);
    }

    @Test
    public void clearGenealogyForUserPass() throws DataAccessException {
        Person differentUser = new Person("asdf", "anotherguy", "April", "Moody",
                'F', null, null, null);
        Person mom = new Person("mom123", "joshmoody24", "April", "Moody",
                'F', null, null, null);
        person.setMotherID(mom.getPersonID());
        dao.create(mom);
        dao.create(person);
        dao.create(differentUser);

        dao.clearGenealogyForUser(person.getAssociatedUsername(), person.getPersonID());
        Person foundMom = dao.findById(mom.getPersonID());
        assertNull(foundMom);
        Person foundChild = dao.findById(person.getPersonID());
        assertNotNull(foundChild); // we don't delete user's personal record
        List<Person> anotherGuyPersons = dao.findByAssociatedUsername("anotherguy");
        assertEquals(1, anotherGuyPersons.size());
    }

    @Test
    public void clearGenealogyForNonexistentUser() throws DataAccessException {
        dao.create(person);
        dao.clearGenealogyForUser("invalid username", "invalid person id");
        Person foundPerson = dao.findById("jam436");
        assertNotNull(foundPerson);
        assertEquals("jam436", foundPerson.getPersonID());
    }
}
