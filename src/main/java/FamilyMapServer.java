import dao.DataAccessException;
import dao.Database;
import dao.PersonDao;
import dao.UserDao;
import model.Person;
import model.User;
import java.util.UUID;

import java.sql.*;

public class FamilyMapServer {
    public static void main(String[] args){

        Database db = new Database();
        Connection conn = null;
        try {
            conn = db.openConnection();
        } catch(DataAccessException ex){
            System.out.println(ex);
            System.exit(1);
        }

        System.out.println("Database connected");

        UserDao ud = new UserDao(conn);
        PersonDao pd = new PersonDao(conn);
        try {
            String joshId = UUID.randomUUID().toString();
            pd.create(new Person(joshId, "joshmoody24", "Josh", "Moody", 'M', null, null, null));
            Person created = pd.findByAssociatedUsername("joshmoody24");
            ud.create(new User(joshId, "joshmoody24", "password123", "joshmoody24@gmail.com", "Josh", "Moody", 'M'));
        } catch(DataAccessException ex){
            System.out.println(ex);
        }

        System.out.println("Queries executed");
        db.closeConnection(true);
    }
}
