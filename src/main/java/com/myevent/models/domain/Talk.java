package com.myevent.models.domain;

import com.myevent.models.actors.Person;
import com.myevent.repositories.MaterialsRepository;

import java.time.LocalDateTime;

public class Talk {

    private String title;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private String room;
    private Person speaker;

    private MaterialsRepository materials;

    public Talk(String title, LocalDateTime timeStart, LocalDateTime timeEnd, String room, Person speaker) {
        this.title = title;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.room = room;
        this.speaker = speaker;
        this.materials = new MaterialsRepository();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalDateTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Person getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Person speaker) {
        this.speaker = speaker;
    }

    public MaterialsRepository getMaterials() {
        return materials;
    }

}
