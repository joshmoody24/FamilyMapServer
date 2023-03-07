package service;

import dao.*;
import generator.EventGenerator;
import generator.PersonGenerator;
import model.Event;
import model.Person;
import model.User;
import request.FillRequest;
import request.FillResult;

import java.sql.Connection;
import java.util.List;

/**
 * a service that can fill the database with generated data
 */
public class FillService {

    /**
     * Populates the database with generated data
     * @param r the fill request
     * @return the result of the fill operation
     */
    public FillResult fill(FillRequest r) throws IllegalArgumentException {
        Database db = new Database();
        try{
            Connection conn = db.openConnection();
            User user = new UserDao(conn).findByUsername(r.getUsername());

            if(r.getGenerations() < 0) throw new IllegalArgumentException("Generations must be non-negative");

            if(user == null) throw new DoesNotExistException("User " + r.getUsername() + " does not exist");
            PersonDao personDao = new PersonDao(conn);
            EventDao eventDao = new EventDao(conn);

            // delete old family tree if necessary
            personDao.clearGenealogyForUser(user);
            eventDao.clearForUser(user.getUsername());

            Person userPerson = personDao.findById(user.getPersonId());
            if(userPerson == null) throw new DoesNotExistException("Person record for user does not exist");

            // the big moment: generating the family tree
            generateParents(userPerson, r.getGenerations(), personDao);

            // generate events using my own birth date as a reference so debugging is conceptually easier for me
            generateEventsForPerson(userPerson, 2000, 2023, personDao, eventDao);

            // pull generated objects back out of database to count them
            List<Person> generatedPeople = personDao.findByAssociatedUsername(r.getUsername());
            List<Event> generatedEvents = eventDao.findForUser(r.getUsername());

            db.closeConnection(true);
            return new FillResult(true, "Successfully added " + generatedPeople.size() + " persons and " + generatedEvents.size() + " events to the database.");
        } catch(Exception e){
            e.printStackTrace();
            db.closeConnection(false);
            return new FillResult(false, "Error: " + e.getMessage());
        }
    }

    /**
     * Generates parents recursively until depth limit is hit
     * @param child the person to generate parents for
     * @param depth starts as the number of generations. The recursion stops after this many levels.
     * @param dao the Dao used to create person records in the database
     * @throws DataAccessException
     */
    public void generateParents(Person child, int depth, PersonDao dao) throws DataAccessException {
        if(depth == 0) return;
        // generate parents
        Person mother = PersonGenerator.generateMother(child);
        Person father = PersonGenerator.generateFather(child);

        // link parents together
        mother.setSpouseID(father.getPersonID());
        father.setSpouseID(mother.getPersonID());

        // link child to parents
        child.setFatherID(father.getPersonID());
        child.setMotherID(mother.getPersonID());

        // save results to database
        dao.create(mother);
        dao.create(father);
        dao.updateParents(child);

        // generate parents for parents
        generateParents(mother, depth-1, dao);
        generateParents(father, depth-1, dao);
    }

    public void generateEventsForPerson(Person person, int birthYear, int currentYear, PersonDao personDao, EventDao eventDao) throws DataAccessException {
        // everyone is born
        eventDao.create(EventGenerator.generateEvent("birth", birthYear, person.getAssociatedUsername(), person.getPersonID()));

        // everyone but user is married
        // and they get married at exactly age 20
        if(person.getSpouseID() != null){
            Event marriage = EventGenerator.generateEvent("marriage", birthYear + 20, person.getAssociatedUsername(), person.getPersonID());

            // if the spouse already has a marriage event, make sure this one matches the location
            List<Event> spouseEvents = eventDao.findForPerson(person.getSpouseID());
            Event spouseMarriage = null;
            for(Event e : spouseEvents){
                if(e.getEventType().equals("marriage")){
                    spouseMarriage = e;
                }
            }
            if(spouseMarriage != null){
                marriage.setCity(spouseMarriage.getCity());
                marriage.setCountry(spouseMarriage.getCountry());
                marriage.setLatitude(spouseMarriage.getLatitude());
                marriage.setLatitude(spouseMarriage.getLongitude());
            }
            eventDao.create(marriage);
        }

        // everyone but user is dead one year after giving birth, sadness
        Event death = EventGenerator.generateEvent("death", birthYear + 22, person.getAssociatedUsername(), person.getPersonID());
        eventDao.create(death);

        // do the same thing for the person's parents, if they exist
        Person mother = personDao.findById(person.getMotherID());
        Person father = personDao.findById(person.getFatherID());

        // parents always have child at exactly age 21
        if(mother != null) generateEventsForPerson(mother, birthYear - 21, currentYear, personDao, eventDao);
        if(father != null) generateEventsForPerson(father, birthYear - 21, currentYear, personDao, eventDao);
    }
}
