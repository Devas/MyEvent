package com.myevent.controllers;

import com.myevent.models.actors.Person;
import com.myevent.models.googleapi.Book;
import com.myevent.repositories.EventsRepository;
import com.myevent.repositories.PeopleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("speaker")
public class SpeakerController {

    private final EventsRepository eventsRepository;
    private final PeopleRepository peopleRepository;
    private final List<Person> speakers;

    public SpeakerController(EventsRepository eventsRepository, PeopleRepository peopleRepository) {
        this.eventsRepository = eventsRepository;
        this.peopleRepository = peopleRepository;
        this.speakers = this.peopleRepository.getSpeakers();
    }

    @GetMapping("addfile")
    public void addFile() {

    }

    @GetMapping("delfile")
    public void deleteFile() {

    }

    @GetMapping("addslide")
    public void addSlide() {

    }

    @GetMapping("delslide")
    public void deleteSlide() {

    }

    @GetMapping("adddoc")
    public void addDocument() {

    }

    @GetMapping("deldoc")
    public void deleteDocument() {

    }

    @GetMapping("addbook")
    public void addBook() {
        eventsRepository.addBook("Code Europe", "Docker", new Book("Effective Java", "Joshua Bloch"));
        eventsRepository.addBook("Code Europe", "Docker", new Book("0132778041"));
    }

    @GetMapping("delbook")
    public void deleteBook() {
        eventsRepository.deleteBook("Code Europe", "Docker", "0132778041");
    }

}
