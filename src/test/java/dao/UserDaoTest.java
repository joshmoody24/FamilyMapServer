package dao;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {

    private Database db;
    private User user;
    private UserDao dao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        user = new User("jam436", "joshmoody24", "password123", "joshmoody24@gmail.com",
                "Josh", "Moody", 'M');
        Connection conn = db.getConnection();
        dao = new UserDao(conn);
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
        dao.create(user);
        User compareTest = dao.findById(user.getPersonId());
        assertNotNull(compareTest);
        assertEquals(user, compareTest);
    }

    @Test
    public void createFail() throws DataAccessException {
        dao.create(user);
        assertThrows(DataAccessException.class, () -> dao.create(user));
    }

    @Test
    public void findByUsernamePass() throws DataAccessException {
        dao.create(user);
        User foundPerson = dao.findByUsername(user.getUsername());
        assertNotNull(foundPerson);
        assertEquals(user, foundPerson);
    }

    @Test
    public void findByUsernameFail() throws DataAccessException {
        dao.create(user);
        User foundUser = dao.findByUsername("random username");
        assertNull(foundUser);
    }

    @Test
    public void findByIdPass() throws DataAccessException {
        dao.create(user);
        User foundUser = dao.findById(user.getPersonId());
        assertNotNull(foundUser);
        assertEquals(user, foundUser);
    }

    @Test
    public void findByIdFail() throws DataAccessException {
        dao.create(user);
        User foundUser = dao.findById("random id");
        assertNull(foundUser);
    }

    @Test
    public void clearTest() throws DataAccessException {
        dao.create(user);
        dao.clear();
        User foundPerson = dao.findByUsername(user.getUsername());
        assertNull(foundPerson);
    }
}
