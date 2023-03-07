package service;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import model.AuthToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {

    @BeforeEach
    public void setUp() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.openConnection();
        AuthTokenDao dao = new AuthTokenDao(conn);
        dao.clear();
        db.closeConnection(true); // must be closed or service gets locked out
    }

    @Test
    public void clearWhenNotEmpty() throws DataAccessException {
        // insert some data
        Database db = new Database();
        Connection conn = db.openConnection();
        AuthTokenDao dao = new AuthTokenDao(conn);
        dao.create(new AuthToken("asdf", "username123"));
        assertNotNull(dao.findByToken("asdf"));
        db.closeConnection(true); // must be closed or service gets locked out

        // clear everything to start out
        assertDoesNotThrow(() -> new ClearService().clear());

        // make sure data was cleared
        conn = db.openConnection();
        AuthToken result = new AuthTokenDao(conn).findByToken("asdf");
        assertNull(result);
        db.closeConnection(false);
    }

    // the best "alternate" test case I can think of
    // for this extremely simple service
    @Test
    public void clearWhenEmpty() throws DataAccessException {
        // insert some data
        Database db = new Database();
        Connection conn = db.openConnection();
        AuthTokenDao dao = new AuthTokenDao(conn);
        dao.create(new AuthToken("asdf", "username123"));
        assertNotNull(dao.findByToken("asdf"));
        db.closeConnection(true); // must be closed or service gets locked out

        // clear everything to start out
        assertDoesNotThrow(() -> new ClearService().clear());
        // and do it again
        assertDoesNotThrow(() -> new ClearService().clear());

        // make sure data was cleared
        conn = db.openConnection();
        AuthToken result = new AuthTokenDao(conn).findByToken("asdf");
        assertNull(result);
        db.closeConnection(false);
    }
}
