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
    public FillResult fill(FillRequest r){
        Database db = new Database();
        try{
            Connection conn = db.openConnection();
            User user = new UserDao(conn).findByUsername(r.getUsername());
            if(user == null) throw new DoesNotExistException("User does not exist");
            PersonDao personDao = new PersonDao(conn);

            personDao.clearGenealogyForUser(user);

            Person userPerson = personDao.findById(user.getPersonId());
            if(userPerson == null) throw new DoesNotExistException("Person record for user does not exist");

            System.out.println("Filling genealogy for " + r.getUsername());

            generateParents(userPerson, r.getGenerations(), personDao);
            EventDao eventDao = new EventDao(conn);
            generateEventsForPerson(userPerson, 2000, 2023, personDao, eventDao);
            List<Person> generatedPeople = personDao.findByAssociatedUsername(r.getUsername());
            List<Event> generatedEvents = eventDao.findForUser(r.getUsername());
            db.closeConnection(true);
            return new FillResult(true, "Successfully added " + generatedPeople.size() + " and " + generatedEvents.size() + " events to the database.");
        } catch(Exception e){
            e.printStackTrace();
            db.closeConnection(false);
            return new FillResult(false, "Error: " + e.getMessage());
        }
    }

    public void generateParents(Person child, int depth, PersonDao dao) throws DataAccessException {
        if(depth == 0) return;
        Person mother = PersonGenerator.generateMother(child);
        Person father = PersonGenerator.generateFather(child);
        mother.setSpouseID(father.getPersonID());
        father.setSpouseID(mother.getPersonID());
        child.setFatherID(father.getPersonID());
        child.setMotherID(mother.getPersonID());
        dao.create(mother);
        dao.create(father);
        dao.updateParents(child);
        generateParents(mother, depth-1, dao);
        generateParents(father, depth-1, dao);
    }

    public void generateEventsForPerson(Person person, int birthYear, int currentYear, PersonDao personDao, EventDao eventDao) throws DataAccessException {
        // everyone is born
        eventDao.create(EventGenerator.generateEvent("birth", birthYear, person.getAssociatedUsername(), person.getPersonID()));
        // everyone but user is married
        // and they get married at exactly age 20
        if(person.getSpouseID() != null){
            eventDao.create(EventGenerator.generateEvent("marriage", birthYear + 20, person.getAssociatedUsername(), person.getPersonID()));
        }
        // people die at age 80 always, compared to currentYear
        if(currentYear - birthYear > 80){
            eventDao.create(EventGenerator.generateEvent("death", birthYear + 80, person.getAssociatedUsername(), person.getPersonID()));
        }

        // do the same thing for the person's parents, if they exist
        Person mother = personDao.findById(person.getMotherID());
        Person father = personDao.findById(person.getFatherID());
        // people have kids at exactly age 21
        if(mother != null) generateEventsForPerson(mother, birthYear - 21, currentYear, personDao, eventDao);
        if(father != null) generateEventsForPerson(father, birthYear - 21, currentYear, personDao, eventDao);
    }
}
