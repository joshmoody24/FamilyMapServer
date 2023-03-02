package service;
import dao.*;
import request.ClearResult;

import java.sql.Connection;

/**
 * a service that can clear out the database
 */
public class ClearService {

    /**
     * Deletes everything in the database
     * @return the result of the clear operation
     */
    public ClearResult clear(){
        Database db = new Database();
        try {
            Connection conn = db.openConnection();
            new AuthTokenDao(conn).clear();
            new EventDao(conn).clear();
            new PersonDao(conn).clear();
            new UserDao(conn).clear();
            db.closeConnection(true);
            return new ClearResult(true, "Clear succeeded");
        }
        catch(Exception ex){
            ex.printStackTrace();
            db.closeConnection(false);
            return new ClearResult(false, "Error: clearing database failed");
        }
    }
}
