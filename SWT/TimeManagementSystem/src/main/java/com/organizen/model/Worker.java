package com.organizen.model;

import java.util.List;

import com.organizen.db.WorkTimeService;
import com.organizen.patterns.Observer;
import com.organizen.patterns.Subject;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Klasse für einen Mitarbeiter
public class Worker extends Employee implements Subject {
    // Attribute für Urlaubstage, Arbeitsstunden, Flexzeit, Startdatum des Zeitplans und Listen für geplante Arbeitsstunden und Beobachter
    private int vacationDays;
    private int workHours;
    private int flexTime;
    private LocalDate scheduleStartDate;
    private List<Integer> scheduledWorkHours;
    private List<Observer> observers;
    private WorkTimeService workTimeService; // Referenz auf WorkTimeService

    // Konstruktor zur Initialisierung der Attribute
    public Worker(String name, int id, Role role, int vacationDays, int workHours, int flexTime, WorkTimeService workTimeService) {
        super(name, id, role);
        this.vacationDays = vacationDays;
        this.workHours = workHours;
        this.flexTime = flexTime;
        this.scheduleStartDate = LocalDate.now();
        this.scheduledWorkHours = new ArrayList<>(30);
        this.observers = new ArrayList<>();
        this.workTimeService = workTimeService; // WorkTimeService initialisieren
        for (int i = 0; i < 31; i++) {
            scheduledWorkHours.add(0);
        }
    }

    // Methoden
    
    // Implementation der Methoden des Interfaces Subject
    
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
    
    // Methode zum Eintragen der geplanten Arbeitszeiten
    public void scheduleWorkHours(Worker worker, int day, int hours) {
        LocalDate targetDate = scheduleStartDate.plusMonths(1).plusDays(day - 1);
        // Prüft, ob der Tag im aktuellen Monat liegt und nicht in der Vergangenheit liegt
        if (day > 0 && day <= LocalDate.now().lengthOfMonth() && !LocalDate.now().isAfter(targetDate)) {
            scheduledWorkHours.set(day - 1, hours); // Lokale Liste aktualisieren
            workTimeService.saveOrUpdateWorkTime(this.getName(), targetDate, hours); // Benutzername, Datum und Stunden an den Service übergeben
        } else {
            System.out.println("Ungültiges oder vergangenes Datum: " + day);
        }
    }
    
    // Methode zum Ausdrucken der geplanten Arbeitsstunden
    public void printScheduledWorkHours() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate today = scheduleStartDate;
        for (int i = 0; i < scheduledWorkHours.size(); i++) {
            System.out.println("Tag " + (i + 1) + " (" + today.plusDays(i).format(formatter) + "): " + scheduledWorkHours.get(i) + " Stunden");
        }
    }
    
    // Methode zum Zurücksetzen des Zeitplans am Anfang eines neuen Monats
    public void resetSchedule() {
        this.scheduleStartDate = LocalDate.now();
        for (int i = 0; i < 30; i++) {
            scheduledWorkHours.set(i, 0); // Alle Stunden auf 0 zurücksetzen
        }
    }
    
    // Methode zum Bearbeiten der Arbeitsstunden
    public void editWorkHours(int newWorkHours) {
        this.workHours = newWorkHours;
    }

    // Methode zum Senden des monatlichen Arbeitsblatts an den Supervisor
    public void sendMonthlyWorkSheet(Supervisor supervisor, String workSheet) {
        // Arbeitsblatt an den Supervisor senden
        supervisor.receiveMonthlyWorkSheet(this, workSheet);
    }
    
    // Methode zum Bearbeiten des monatlichen Arbeitsblatts
    public void editMonthlyWorkSheet(String newWorkSheet) {
        // Arbeitsblatt bearbeiten
        // Hier kann die Logik für die Bearbeitung des Arbeitsblatts implementiert werden
    }
    
    // Methode zum Nehmen von Gleitzeit
    public void takeFlexTime(int hours) {
        if (this.flexTime >= hours) {
            this.flexTime -= hours;
            System.out.println("Flexzeit von " + hours + " Stunden genommen. Verbleibende Flexzeit: " + this.flexTime);
        } else {
            System.out.println("Nicht genügend Flexzeit verfügbar.");
        }
    }

    // Methode zum Bearbeiten der Urlaubstage
    public void editVacationDays(int newVacationDays) {
        this.vacationDays = newVacationDays;
    }

    // Methode zum Anzeigen von Zeitstatistiken
    public void viewTimeStatics() {
        // Hier kann die Logik für das Anzeigen von Zeitstatistiken implementiert werden
    }
    
    // Methode zum Krankmelden
    public void requestSickLeave() {
         System.out.println(this.getName() + " hat sich krank gemeldet.");
         // Benachrichtigen des Supervisors und HumanResources
         notifyObservers(this.getName() + " ist heute krank.");
    }

    // Methode zum Pausieren
    public void takeBreak(int day) {
        // Hier kann die Logik für das Pausieren implementiert werden
    }

    // Getter-Methode für vacationDays
    public int getVacationDays() {
        return vacationDays;
    }

    // Getter-Methode für workHours
    public int getWorkHours() {
        return workHours;
    }

    // Getter-Methode für flexTime
    public int getFlexTime() {
        return flexTime;
    }
}