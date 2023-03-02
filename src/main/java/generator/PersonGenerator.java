package generator;

import com.google.gson.Gson;
import model.Person;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

public class PersonGenerator {

    /**
     *
     * @param username
     * @param male
     * @param lastName if null, random last name
     * @return
     */
    public static Person generatePerson(String username, boolean male, String lastName){
        try {
            // load the JSON person generator data
            Gson gson = new Gson();
            Reader readerMale = Files.newBufferedReader(Paths.get("./json/mnames.json"));
            Reader readerFemale = Files.newBufferedReader(Paths.get("./json/fnames.json"));
            Reader readerSurname = Files.newBufferedReader(Paths.get("./json/snames.json"));
            String[] maleNames = gson.fromJson(readerMale, String[].class);
            String[] femaleNames = gson.fromJson(readerFemale, String[].class);
            String[] lastNames = gson.fromJson(readerSurname, String[].class);
            readerMale.close();
            readerFemale.close();
            readerSurname.close();

            // select names based on gender
            String[] firstNames = femaleNames;
            if(male) firstNames = maleNames;
            String lastname = lastName;
            if(lastName == null) lastname = getRandomString(lastNames);

            // convert boolean gender into char
            char gender = 'F';
            if(male) gender = 'M';

            return new Person(UUID.randomUUID().toString(), username, getRandomString(firstNames), lastname, gender, null, null, null);

        } catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static Person generateFather(Person child){
        Person father = generatePerson(child.getAssociatedUsername(), true, child.getLastName());
        child.setFatherID(father.getPersonID());
        return father;
    }

    public static Person generateMother(Person child){
        Person mother = generatePerson(child.getAssociatedUsername(), false, null);
        child.setMotherID(mother.getPersonID());
        return mother;
    }

    public static String getRandomString(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }
}
