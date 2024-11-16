package com.organizen.model;

import java.util.ArrayList;
import java.util.List;

// Modellklasse für einen Client
public class Client {
    // Liste der Tage
    private List<String> days;

    // Konstruktor, initialisiert die Liste der Tage
    public Client() {
        this.days = new ArrayList<>();
    }

    // Methode zum Verteilen von Benachrichtigungen an Mitarbeiter
    public void distributeNotifications(Employee employee, String message) {
        // Gibt eine Benachrichtigung an einen Mitarbeiter aus
        System.out.println("Benachrichtigung an " + employee.getName() + ": " + message);
    }

    // Methode zum Aktualisieren des Clients
    public void update() {
        // Implementierung der Logik zum Aktualisieren des Clients
        System.out.println("Client wurde aktualisiert.");
    }
}