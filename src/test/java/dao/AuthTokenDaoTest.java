package dao;

import model.AuthToken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTokenDaoTest {

    private Database db;
    private AuthToken token;
    private AuthTokenDao dao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        token = new AuthToken("asdf", "joshmoody24");
        Connection conn = db.getConnection();
        dao = new AuthTokenDao(conn);
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
        dao.create(token);
        AuthToken compareTest = dao.findByToken(token.getAuthtoken());
        assertNotNull(compareTest);
        assertEquals(token, compareTest);
    }

    @Test
    public void createFail() throws DataAccessException {
        dao.create(token);
        assertThrows(DataAccessException.class, () -> dao.create(token));
    }

    @Test
    public void findByUserPass() throws DataAccessException {
        dao.create(token);
        AuthToken found = dao.findByUsername(token.getUsername());
        assertNotNull(found);
        assertEquals(token, found);
    }

    @Test
    public void findByUserFail() throws DataAccessException {
        dao.create(token);
        AuthToken found = dao.findByUsername("random username");
        assertNull(found);
    }

    @Test
    public void findByTokenPass() throws DataAccessException {
        dao.create(token);
        AuthToken found = dao.findByToken(token.getAuthtoken());
        assertNotNull(found);
        assertEquals(token, found);
    }

    @Test
    public void findByTokenFail() throws DataAccessException {
        dao.create(token);
        AuthToken found = dao.findByToken("random username");
        assertNull(found);
    }

    @Test
    public void clearTest() throws DataAccessException {
        dao.create(token);
        dao.clear();
        AuthToken found = dao.findByUsername(token.getUsername());
        assertNull(found);
    }
}
