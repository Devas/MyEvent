package com.myevent.services.calendar;

import com.myevent.services.AuthorizationService;
import com.myevent.services.Credentials;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Lists;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.model.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

public class CalendarService extends AuthorizationService {

    private com.google.api.services.calendar.Calendar client;
    private final java.util.List<Calendar> addedCalendarsUsingBatch = Lists.newArrayList();

    public CalendarService() {
        try {
            dataStoreFactory = new FileDataStoreFactory(new File(Credentials.CALENDAR_CREDENTIALS_DIR));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void run() {

        try {
            Credential credential = authorize();
            client = new com.google.api.services.calendar.Calendar.Builder(httpTransport, jsonFactory, credential).
                    setApplicationName(Credentials.APP_NAME).
                    build();

            showCalendars();
            addCalendarsUsingBatch();
            Calendar calendar = addCalendar();
            updateCalendar(calendar);
            addEvent(calendar);
            showEvents(calendar);
            deleteCalendarsUsingBatch();
            deleteCalendar(calendar);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    private void showCalendars() throws IOException {
        CalendarView.header("Show Calendars");
        CalendarList feed = client.calendarList().list().execute();
        CalendarView.display(feed);
    }

    private void addCalendarsUsingBatch() throws IOException {
        CalendarView.header("Add Calendars using Batch");
        BatchRequest batch = client.batch();

        // Create the callback.
        JsonBatchCallback<Calendar> callback = new JsonBatchCallback<Calendar>() {

            @Override
            public void onSuccess(Calendar calendar, HttpHeaders responseHeaders) {
                CalendarView.display(calendar);
                addedCalendarsUsingBatch.add(calendar);
            }

            @Override
            public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) {
                System.out.println("Error Message: " + e.getMessage());
            }
        };

        // Create 2 Calendar Entries to insert.
        Calendar entry1 = new Calendar().setSummary("Calendar for Testing 1");
        client.calendars().insert(entry1).queue(batch, callback);

        Calendar entry2 = new Calendar().setSummary("Calendar for Testing 2");
        client.calendars().insert(entry2).queue(batch, callback);

        batch.execute();
    }

    private Calendar addCalendar() throws IOException {
        CalendarView.header("Add Calendar");
        Calendar entry = new Calendar();
        entry.setSummary("Calendar for Testing 3");
        Calendar result = client.calendars().insert(entry).execute();
        CalendarView.display(result);
        return result;
    }

    private Calendar updateCalendar(Calendar calendar) throws IOException {
        CalendarView.header("Update Calendar");
        Calendar entry = new Calendar();
        entry.setSummary("Updated Calendar for Testing");
        Calendar result = client.calendars().patch(calendar.getId(), entry).execute();
        CalendarView.display(result);
        return result;
    }


    private void addEvent(Calendar calendar) throws IOException {
        CalendarView.header("Add Event");
        Event event = newEvent();
        Event result = client.events().insert(calendar.getId(), event).execute();
        CalendarView.display(result);
    }

    private Event newEvent() {
        final long ONE_HOUR_IN_MS = 3600000;
        Event event = new Event();
        event.setSummary("New Event");
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + ONE_HOUR_IN_MS);
        DateTime start = new DateTime(startDate, TimeZone.getTimeZone("UTC"));
        event.setStart(new EventDateTime().setDateTime(start));
        DateTime end = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
        event.setEnd(new EventDateTime().setDateTime(end));
        return event;
    }

    private void showEvents(Calendar calendar) throws IOException {
        CalendarView.header("Show Events");
        Events feed = client.events().list(calendar.getId()).execute();
        CalendarView.display(feed);
    }

    private void deleteCalendarsUsingBatch() throws IOException {
        CalendarView.header("Delete Calendars Using Batch");
        BatchRequest batch = client.batch();
        for (Calendar calendar : addedCalendarsUsingBatch) {
            client.calendars().delete(calendar.getId()).queue(batch, new JsonBatchCallback<Void>() {

                @Override
                public void onSuccess(Void content, HttpHeaders responseHeaders) {
                    System.out.println("Delete is successful!");
                }

                @Override
                public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) {
                    System.out.println("Error Message: " + e.getMessage());
                }
            });
        }

        batch.execute();
    }

    private void deleteCalendar(Calendar calendar) throws IOException {
        CalendarView.header("Delete Calendar");
        client.calendars().delete(calendar.getId()).execute();
    }
}
