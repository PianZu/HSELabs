package com.organizen.ui;

import com.organizen.db.WorkTimeService;
import com.organizen.model.Worker;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//Benutzeroberfläche für die Zeittabelle
@Route("worktimesheetview")
public class WorkTimeSheetView extends VerticalLayout {
	
	private final WorkTimeService workTimeService;
    private Worker worker;  // Reference to the Worker
    private Grid<DayHours> grid = new Grid<>(DayHours.class, false);
    private List<DayHours> dayHoursList = new ArrayList<>(); // List to hold day and hours data

    public WorkTimeSheetView(WorkTimeService workTimeService) {
    	this.workTimeService = workTimeService;
        this.worker = new Worker("John Doe", 1, Worker.Role.Worker, 10, 40, 5, this.workTimeService);
        // Redirect to login or handle error}
        initUI(); // Benutzeroberfläche initialisieren
        setSizeFull(); // Größe auf volle Höhe setzen
    }
    // Methode zur Initialisierung der Benutzeroberfläche
    private void initUI() {
        H1 header = new H1("Timetable"); // Überschrift für die Zeittabelle
        setupGrid(); // Grid einrichten
        Button saveButton = new Button("Save Changes", e -> saveWorkHours()); // Button zum Speichern von Änderungen
        saveButton.getStyle().set("margin-top", "20px"); // Abstand zum Header hinzufügen
        
        Button deleteButton = new Button("Delete All Entries", new Icon(VaadinIcon.TRASH)); // Button zum Löschen aller Einträge
        deleteButton.addClickListener(e -> deleteAllEntries(this.worker)); // Ereignishandler für das Löschen aller Einträge
        deleteButton.getStyle().set("position", "absolute"); // Position des Buttons festlegen
        deleteButton.getStyle().set("bottom", "20px");
        deleteButton.getStyle().set("right", "20px");
        
        Button switchViewButton = new Button("Back"); // Button zum Zurückkehren
        switchViewButton.getStyle().set("margin-top", "20px"); // Abstand zum Header hinzufügen
        switchViewButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("workerView"))); // Ereignishandler für das Zurückkehren
        switchViewButton.getStyle().set("position", "absolute"); // Position des Buttons festlegen
        switchViewButton.getStyle().set("top", "20px");
        switchViewButton.getStyle().set("right", "20px");
        add(switchViewButton); // Button zum Layout hinzufügen
        add(deleteButton); // Button zum Layout hinzufügen
        add(header, grid, saveButton); // Header, Grid und Speicherbutton zum Layout hinzufügen
    }
    // Methode zum Einrichten des Grids
    private void setupGrid() {
        grid.addColumn(DayHours::getDayAsString).setHeader("Day"); // Spalte für den Tag
        grid.addColumn(new ComponentRenderer<>(dayHours -> {
            IntegerField hoursField = new IntegerField(); // Feld für die Stundenanzahl
            hoursField.setValue(dayHours.getHours()); // Wert des Feldes setzen
            hoursField.setWidthFull(); // volle Breite setzen
            hoursField.addValueChangeListener(event -> dayHours.setHours(event.getValue())); // Ereignishandler für Änderungen
            return hoursField;
        })).setHeader("Work Hours"); // Spalte für die Arbeitsstunden

        dayHoursList = createDayHoursList(); // Liste der Tagesstunden erstellen
        grid.setItems(dayHoursList); // Einträge im Grid setzen
    }
    // Methode zur Erstellung der Liste der Tagesstunden
    private List<DayHours> createDayHoursList() {
        List<DayHours> localDayHoursList = new ArrayList<>();
        for (DayHours d: localDayHoursList) {
        	d.hours = 0;
        }
        LocalDate startOfMonth = LocalDate.now().plusMonths(1).withDayOfMonth(1); // Erster Tag des aktuellen Monats
        int lengthOfMonth = startOfMonth.lengthOfMonth(); // Anzahl der Tage im aktuellen Monat

        for (int day = 1; day <= lengthOfMonth; day++) {
            localDayHoursList.add(new DayHours(startOfMonth.plusDays(day - 1), 0)); // Tagesstunden hinzufügen
        }
        return localDayHoursList; // Liste zurückgeben
    }
    // Methode zum Speichern der Arbeitsstunden
    private void saveWorkHours() {
        LocalDate startOfMonth = LocalDate.now().plusMonths(1).withDayOfMonth(1); // Erster Tag des aktuellen Monats
        dayHoursList.forEach(dayHours -> {
            if (dayHours.getHours() > 0 && dayHours.getHours() <= 10) {  // Überprüfen, ob die Stunden im gültigen Bereich liegen
                int dayIndex = dayHours.getDate().getDayOfMonth(); // Index des Tages
                worker.scheduleWorkHours(this.worker, dayIndex, dayHours.getHours()); // Arbeitsstunden speichern
            }
        });
        
        
        // Add the button to the layout
    }
    // Klasse zur Darstellung von Tag und Stunden
    public static class DayHours {
        private LocalDate date;
        private int hours;
        // Konstruktor
        public DayHours(LocalDate date, int hours) {
            this.date = date;
            this.hours = hours;
        }
        // Methode zur Rückgabe des Tages als String
        public String getDayAsString() {
            return date.getDayOfMonth() + " - " + date.getMonth().toString();
        }
        // Getter-Methode für Stunden
        public int getHours() {
            return hours;
        }
        // Setter-Methode für Stunden
        public void setHours(int hours) {
            this.hours = hours;
        }
        // Getter-Methode für das Datum
        public LocalDate getDate() {
            return date;
        }
    }
    // Methode zum Löschen aller Einträge
    private void deleteAllEntries(Worker worker) {
        // Call the service method to delete all entries from the database
        workTimeService.deleteWorkerEntries(this.worker);
        
        // Update the UI or perform any other actions as needed
    }
}