package com.myevent.helpers;

import com.myevent.models.actors.Admin;
import com.myevent.models.actors.Person;
import com.myevent.models.actors.Speaker;

import java.util.ArrayList;
import java.util.List;

public class PersonGenerator {

    private List<Person> admins = new ArrayList<>();
    private List<Person> speakers = new ArrayList<>();
    private List<Person> users = new ArrayList<>();

    public PersonGenerator() {
        generateAdmins();
        generateSpeakers();
        generateUsers();
    }

    private void generateAdmins() {
        admins.add(new Admin("admin-A", "admin-A", "admin-A@gmail.com"));
    }

    private void generateSpeakers() {
        speakers.add(new Speaker("speaker-A", "speaker-A", "speaker-A@gmail.com"));
        speakers.add(new Speaker("speaker-B", "speaker-B", "speaker-B@gmail.com"));
        speakers.add(new Speaker("speaker-C", "speaker-C", "speaker-C@gmail.com"));
    }

    private void generateUsers() {
        speakers.add(new Speaker("user-A", "user-A", "user-A@gmail.com"));
        speakers.add(new Speaker("user-B", "user-B", "user-B@gmail.com"));
        speakers.add(new Speaker("user-C", "user-C", "user-C@gmail.com"));
        speakers.add(new Speaker("user-1", "user-1", "user-1@gmail.com"));
        speakers.add(new Speaker("user-2", "user-2", "user-2@gmail.com"));
        speakers.add(new Speaker("user-3", "user-3", "user-3@gmail.com"));
    }

    public Person getAdmin(int index) {
        return admins.get(index);
    }

    public Person getSpeaker(int index) {
        return speakers.get(index);
    }

    public Person getUser(int index) {
        return users.get(index);
    }
}
