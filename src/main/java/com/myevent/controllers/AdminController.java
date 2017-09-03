package com.myevent.controllers;

import com.myevent.models.actors.Person;
import com.myevent.models.actors.Speaker;
import com.myevent.models.actors.User;
import com.myevent.models.domain.Event;
import com.myevent.models.domain.Talk;
import com.myevent.repositories.EventsRepository;
import com.myevent.repositories.PeopleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final EventsRepository eventsRepository;
    private final PeopleRepository peopleRepository;
    private final Person admin;

    public AdminController(EventsRepository eventsRepository, PeopleRepository peopleRepository) {
        this.eventsRepository = eventsRepository;
        this.peopleRepository = peopleRepository;
        this.admin = this.peopleRepository.getAdmin();
    }

    @GetMapping("addevent")
    public void addEvent() {
        Event event1 = eventsRepository.addEvent(new Event("Code Europe", "Kraków", LocalDateTime.now()));
        System.out.println("Added: " + event1.getTitle() + " " + event1.getLocalization() + " " + event1.getDate());
    }

    @GetMapping("delevent")
    public void deleteEvent() {
        eventsRepository.deleteEvent("Code Europe");
    }

    @GetMapping("addtalk")
    public void addTalk() {
        Talk talk = new Talk(
                "A Fundamental Formula for Microservices using Docker",
                LocalDateTime.now(), LocalDateTime.now(),
                "Sala Cupertino",
                new Speaker("David", "Hardy", "dh@gmail.com"));
        eventsRepository.addTalk("Code Europe", talk);
    }

    @GetMapping("deltalk")
    public void deleteTalk() {
        eventsRepository.deleteTalk("Code Europe", "Docker");
    }

    @GetMapping("addspeaker")
    public void addSpeaker() {
        peopleRepository.addSpeaker(new Speaker("David", "Hardy", "dh@gmail.com"));
    }

    @GetMapping("delspeaker")
    public void deleteSpeaker() {
        peopleRepository.deleteSpeaker("dh@gmail.com");
    }

    @GetMapping("adduser")
    public void addUser() {
        peopleRepository.addUser(new User("Tom", "Hanks", "th@gmail.com"));
    }

    @GetMapping("deluser")
    public void deleteUser() {
        peopleRepository.deleteUser("th@gmail.com");
    }


}
