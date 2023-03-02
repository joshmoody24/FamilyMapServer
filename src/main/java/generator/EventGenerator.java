package generator;

import com.google.gson.Gson;
import model.Event;
import model.Location;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class EventGenerator {
    public static Event generateEvent(String eventType, int year, String username, String personId){
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("./json/locations.json"));
            Location[] locations = gson.fromJson(reader, Location[].class);
            Location location = getRandomLocation(locations);
            reader.close();
            return new Event(UUID.randomUUID().toString(), username, personId, location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), eventType, year);
        } catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static Location getRandomLocation(Location[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }
}
