package com.myevent.repositories;

import com.myevent.models.googleapi.Book;
import com.myevent.models.domain.Event;
import com.myevent.models.domain.Talk;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class EventsRepository {

    private HashMap<String, Event> events;

    public EventsRepository() {
        events = new HashMap<>();
    }

    public HashMap<String, Event> getAllEvents() {
        return events;
    }

    public Event addEvent(Event event) {
        events.put(event.getTitle(), event);
        return event;
    }

    public Event getEvent(String event) {
        return events.get(event);
    }

    public void deleteEvent(String event) {
        events.remove(event);
    }

    public Talk addTalk(String event, Talk talk) {
        events.get(event).addTalk(talk);
        return talk;
    }

    public Talk getTalk(String event, String talk) {
        return events.get(event).getTalk(talk);
    }

    public void deleteTalk(String event, String talk) {
        events.get(event).deleteTalk(talk);
    }

    public void addBook(String event, String talk, Book book) {
        getTalk(event, talk).getMaterials().addBook(book);
    }

    public void deleteBook(String event, String talk, String isbn) {
        getTalk(event, talk).getMaterials().deleteBook(isbn);
    }

    public void showMap() {

    }

}
