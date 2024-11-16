package com.organizen.model;

import com.organizen.patterns.Observer;

public class HumanResources extends Employee implements Observer{

    // Konstruktor
    public HumanResources(String name, int id, Role role) {
        super(name, id, role);
    }
    //Notifications für den Supervisor, falls Mitarbeiterzustand sich ändert
    @Override
    public void update(String message) {
        System.out.println("HR Notification: " + message);
    }

    // Methoden
    public void calculateFlexTimeState(Worker worker) {
    }

    public void registerSickness(Worker worker) {
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