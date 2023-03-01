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
        ClearResult result;
        try {
            Database db = new Database();
            Connection conn = db.openConnection();
            new AuthTokenDao(conn).clear();
            new EventDao(conn).clear();
            new PersonDao(conn).clear();
            new UserDao(conn).clear();
            result = new ClearResult(true, "Clear succeeded");
        }
        catch(Exception ex){
            ex.printStackTrace();
            result = new ClearResult(false, "Error: clearing database failed");
        }
        return result;
    }
}
