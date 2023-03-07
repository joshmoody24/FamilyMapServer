package service;

import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.Test;
import passoffresult.LoadResult;
import request.LoadRequest;
import request.Result;

import static org.junit.jupiter.api.Assertions.*;

public class LoadServiceTest {

    @Test
    public void loadPass(){
        Person mom = new Person("mom123", "joshmoody24", "April", "Moody",
                'F', null, null, null);
        Person dad = new Person("dad123", "joshmoody24", "Dallan", "Moody",
                'M', null, null, null);
        Person child = new Person("child123", "joshmoody24", "Josh", "Moody",
                'F', dad.getPersonID(), mom.getPersonID(), null);
        User user = new User("child123", "joshmoody24", "password123",
                "joshmoody24@gmail.com","Josh", "Moody", 'M');
        Event event = new Event("event123", "joshmoody24", "mom123", 100f, 200f,
                "USA", "Spanish Fork", "death", 2050);
        LoadRequest request = new LoadRequest(new User[] {user}, new Person[] {mom,dad,child}, new Event[] {event});
        Result result = new LoadService().load(request);
        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().toLowerCase().contains("successfully added"));
        assertTrue(result.getMessage().toLowerCase().contains("3 persons"));
        assertTrue(result.getMessage().toLowerCase().contains("1 users"));
        assertTrue(result.getMessage().toLowerCase().contains("1 events"));
    }

    // should NOT treat null same way as empty array
    // we want it to fail
    @Test
    public void loadWithNullValues(){
        LoadRequest request = new LoadRequest(null,null,null);
        Result result = new LoadService().load(request);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().toLowerCase().contains("error"));
    }
}
