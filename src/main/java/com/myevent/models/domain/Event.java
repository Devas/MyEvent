package com.myevent.models.domain;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Event {

    private String title;
    private String localization;
    private LocalDateTime date;
    private HashMap<String, Talk> talks;

    public Event(String title, String localization, LocalDateTime date) {
        this.title = title;
        this.localization = localization;
        this.date = date;
        this.talks = new HashMap<>();
    }

    public String getTitle() {
        return title;
    }

    public String getLocalization() {
        return localization;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Talk getTalk(String talk) {
        return talks.get(talk);
    }

    public HashMap<String, Talk> getAllTalks() {
        return talks;
    }

    public void addTalk(Talk talk) {
        talks.put(talk.getTitle(), talk);
    }

    public void deleteTalk(String talk) {
        talks.remove(talk);
    }

}
