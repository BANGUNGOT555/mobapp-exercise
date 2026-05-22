package com.example.mobapp;

import java.util.UUID;

public class Event {
    private final String id;
    private final String title;
    private final String note;
    private final String date;
    private boolean complete;

    public Event(String title, String note, String date) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.note = note;
        this.date = date;
        this.complete = false;
    }

    public Event(String id, String title, String note, String date, boolean complete) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.date = date;
        this.complete = complete;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}