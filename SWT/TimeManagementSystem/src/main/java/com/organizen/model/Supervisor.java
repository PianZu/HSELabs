package com.organizen.model;

import com.organizen.patterns.Observer;

public class Supervisor extends Employee implements Observer {
    private int team;

    // Konstruktor
    public Supervisor(String name, int id, Role role, int team) {
        super(name, id, role);
        this.team = team;
    }
    //Notifications, falls sich Mitarbeiterzustand ändert
    @Override
    public void update(String message) {
        System.out.println("Supervisor Notification: " + message);
    }

    // Methoden
    public void manageVacationRequests(Worker worker) {
    }

    // Methode zum Empfangen des monatlichen Arbeitsblatts vom Worker
    public void receiveMonthlyWorkSheet(Worker worker, String workSheet) {
        // Arbeitsblatt erhalten
        // Hier kannst du die Logik für den Empfang und die Überprüfung des Arbeitsblatts implementieren
        // Beispiel: Supervisor prüft das Arbeitsblatt und gibt Feedback an den Worker
        System.out.println("Supervisor hat das monatliche Arbeitsblatt von " + worker.getName() + " erhalten.");
        System.out.println("Bitte überprüfen und genehmigen Sie das Arbeitsblatt.");
    }

    // Methode zum Genehmigen des monatlichen Arbeitsblatts des Workers
    public void approveMonthlyWorkSheet(Worker worker) {
        // Genehmige das Arbeitsblatt des Workers
        // Hier kannst du die Logik für die Genehmigung des Arbeitsblatts implementieren
        System.out.println("Das monatliche Arbeitsblatt von " + worker.getName() + " wurde genehmigt.");
    }
    
    public int getFlexTime(Worker worker) {
        return worker.getFlexTime();
    }

    public int getVacationDays(Worker worker) {
        return worker.getVacationDays();
    }

    public void sendNotifications() {
    }
}