package com.myevent.services.calendar;

import com.google.api.services.calendar.model.*;

public class CalendarView {

  public static void header(String name) {
    System.out.println();
    System.out.println("============== " + name + " ==============");
    System.out.println();
  }

  public static void display(CalendarList feed) {
    if (feed.getItems() != null) {
      for (CalendarListEntry entry : feed.getItems()) {
        System.out.println();
        System.out.println("-----------------------------------------------");
        display(entry);
      }
    }
  }

  public static void display(Events feed) {
    if (feed.getItems() != null) {
      for (Event entry : feed.getItems()) {
        System.out.println();
        System.out.println("-----------------------------------------------");
        display(entry);
      }
    }
  }

  public static void display(CalendarListEntry entry) {
    System.out.println("ID: " + entry.getId());
    System.out.println("Summary: " + entry.getSummary());
    if (entry.getDescription() != null) {
      System.out.println("Description: " + entry.getDescription());
    }
  }

  public static void display(Calendar entry) {
    System.out.println("ID: " + entry.getId());
    System.out.println("Summary: " + entry.getSummary());
    if (entry.getDescription() != null) {
      System.out.println("Description: " + entry.getDescription());
    }
  }

  public static void display(Event event) {
    if (event.getStart() != null) {
      System.out.println("Start Time: " + event.getStart());
    }
    if (event.getEnd() != null) {
      System.out.println("End Time: " + event.getEnd());
    }
  }
}
