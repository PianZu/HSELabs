package com.organizen.model;

import java.time.LocalDate;

//Klasse für einen Arbeitszeit-Eintrag
public class WorkTimeEntry {
 // Attribute für Benutzername, Datum und Stunden
 private String username;
 private LocalDate date;
 private float hours;

 // Konstruktor zur Initialisierung der Attribute
 public WorkTimeEntry(String username, LocalDate date, float hours) {
     this.username = username;
     this.date = date;
     this.hours = hours;
 }

    // Getter und Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }
}