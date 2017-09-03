package com.myevent.repositories;

import com.myevent.models.actors.Admin;
import com.myevent.models.actors.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * All Person objects are uniquely identified by email.
 */
@Repository
public class PeopleRepository {

    private List<Person> speakers;
    private List<Person> users;
    private Admin admin;

    public PeopleRepository() {
        this.speakers = new ArrayList<>();
        this.users = new ArrayList<>();
        this.admin = new Admin("John", "Smith", "johnsmith@gmail.com");
    }

    public List<Person> getSpeakers() {
        return speakers;
    }

    public List<Person> getUsers() {
        return users;
    }

    public Person getAdmin() {
        return admin;
    }

    public void addSpeaker(Person person) {
        speakers.add(person);
    }

    public void deleteSpeaker(String email) {
        speakers.removeIf(person -> person.getEmail().equals(email));
    }

    public void addUser(Person person) {
        users.add(person);
    }

    public void deleteUser(String email) {
        users.removeIf(person -> person.getEmail().equals(email));
    }

}
