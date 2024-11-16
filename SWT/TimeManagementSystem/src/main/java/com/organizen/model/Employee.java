package com.organizen.model;

// Abstrakte Klasse für einen Mitarbeiter
public abstract class Employee {
    // Attribute für Name, ID und Rolle
    private String name;
    private int id;
    private Role role;
    // Klassenattribut, um auf das Client-Objekt zuzugreifen
    private static Client client;

    // Konstruktor zur Initialisierung der Attribute
    public Employee(String name, int id, Role role) {
        this.name = name;
        this.id = id;
        this.role = role;
    }
    // Statische Methode zum Setzen des Client-Objekts
    public static void setClient(Client client) {
        Employee.client = client;
    }

    // Statische Methode zum Verteilen von Benachrichtigungen an alle Mitarbeiter
    public static void distributeNotifications(Employee employee, String message) {
        // Prüft, ob ein Client-Objekt verfügbar ist
        if (client != null) {
            client.distributeNotifications(employee, message);
        } else {
            System.out.println("Kein Client-Objekt verfügbar.");
        }
    }

    // Statische Methode zum Aktualisieren des Client-Objekts
    public static void updateClient() {
        // Prüft, ob ein Client-Objekt verfügbar ist
        if (client != null) {
            client.update();
        } else {
            System.out.println("Kein Client-Objekt verfügbar.");
        }
    }
    // Getter-Methode für die ID
    public int getID() {
        return id;
    }

    // Getter-Methode für den Namen
    public String getName() {
        return name;
    }

    // Getter-Methode für die Rolle
    public Role getRole() {
        return role;
    }

    // Enumeration für die Rolle
    public enum Role {
        Worker,
        Supervisor,
        HR
    }
}