package com.example.mobapp;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventStorage {
    private static final String PREFS_NAME = "daily_events_prefs";
    private static final String KEY_EVENTS = "events";

    private final SharedPreferences preferences;

    public EventStorage(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public List<Event> getEvents() {
        return parseEvents(preferences.getString(KEY_EVENTS, "[]"));
    }

    public void addEvent(Event event) {
        List<Event> events = getEvents();
        events.add(0, event);
        saveEvents(events);
    }

    public void setComplete(String eventId, boolean complete) {
        List<Event> events = getEvents();

        for (Event event : events) {
            if (event.getId().equals(eventId)) {
                event.setComplete(complete);
                break;
            }
        }

        saveEvents(events);
    }

    private void saveEvents(List<Event> events) {
        JSONArray array = new JSONArray();

        for (Event event : events) {
            try {
                JSONObject object = new JSONObject();
                object.put("id", event.getId());
                object.put("title", event.getTitle());
                object.put("note", event.getNote());
                object.put("date", event.getDate());
                object.put("complete", event.isComplete());
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        preferences.edit().putString(KEY_EVENTS, array.toString()).apply();
    }

    private List<Event> parseEvents(String json) {
        List<Event> events = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(json);

            for (int index = 0; index < array.length(); index++) {
                JSONObject object = array.getJSONObject(index);

                Event event = new Event(
                        object.getString("id"),
                        object.getString("title"),
                        object.optString("note", ""),
                        object.optString("date", ""),
                        object.optBoolean("complete", false)
                );

                events.add(event);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return events;
    }
}