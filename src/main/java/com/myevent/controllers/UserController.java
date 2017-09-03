package com.myevent.controllers;

import com.myevent.services.books.BooksService;
import com.myevent.models.actors.Person;
import com.myevent.repositories.EventsRepository;
import com.myevent.repositories.PeopleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final EventsRepository eventsRepository;
    private final PeopleRepository peopleRepository;
    private final List<Person> users;

    public UserController(EventsRepository eventsRepository, PeopleRepository peopleRepository) {
        this.eventsRepository = eventsRepository;
        this.peopleRepository = peopleRepository;
        this.users = this.peopleRepository.getUsers();
    }

    @GetMapping("downfile")
    public void downloadFile() {

    }

    @GetMapping("downslide")
    public void downloadSlide() {

    }

    @GetMapping("downdoc")
    public void downloadDocument() {

    }

    @GetMapping("addevent")
    public void addEventToCalendar() {

    }

    @GetMapping("delevent")
    public void deleteEventFromCalendar() {

    }

    @GetMapping("addtalk")
    public void addTalkToCalendar() {

    }

    @GetMapping("deltalk")
    public void deleteTalkFromCalendar() {

    }

    @GetMapping("showschedule")
    public void showEventSchedule() {

    }

    @GetMapping("showbooks")
    @ResponseBody
    public List<String> showRecommendedBooks() {
//        eventsRepository.addBook("Code Europe", "Docker", new Book("Effective Java", "Joshua Bloch"));
//        eventsRepository.addBook("Code Europe", "Docker", new Book("0132778041"));

        return new BooksService().queryByIsbn("0132778041");
    }

    public void shareEventReview() {

    }

    public void shareTalkReview() {

    }

}
