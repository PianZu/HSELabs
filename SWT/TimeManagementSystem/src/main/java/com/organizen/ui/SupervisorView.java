package com.organizen.ui;

import com.organizen.model.WorkTimeEntry;
import com.organizen.db.WorkTimeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;

import java.util.List;

// Ansicht für den Supervisor
@Route("supervisorView")
public class SupervisorView extends VerticalLayout {

    // Grid zur Anzeige von Arbeitszeiteinträgen
    private final Grid<WorkTimeEntry> grid = new Grid<>(WorkTimeEntry.class);
    private final WorkTimeService workTimeService;
    private Select<String> workerSelect; // Auswahl für Mitarbeiter

    // Konstruktor
    public SupervisorView(WorkTimeService workTimeService) {
        this.workTimeService = workTimeService;
        setupWorkerSelect(); // Einrichten der Mitarbeiterauswahl
        setupGrid(); // Einrichten des Grids
        add(workerSelect, grid); // Hinzufügen der Komponenten zum Layout
        updateList("John Doe"); // Liste der Arbeitszeiteinträge für "John Doe" aktualisieren
    }

    // Methode zur Einrichtung der Mitarbeiterauswahl
    private void setupWorkerSelect() {
        workerSelect = new Select<>();
        workerSelect.setLabel("Select Worker"); // Label für die Auswahl
        workerSelect.setItems("John Doe", "Jane Doe"); // Mitarbeiteroptionen
        workerSelect.addValueChangeListener(e -> updateList(e.getValue())); // Ereignishandler für Änderungen in der Auswahl
    }

    // Methode zur Einrichtung des Grids
    private void setupGrid() {
        grid.addColumn(WorkTimeEntry::getUsername).setHeader("Username"); // Spalte für Benutzernamen
        grid.addColumn(WorkTimeEntry::getDate).setHeader("Date"); // Spalte für Datum
        grid.addColumn(WorkTimeEntry::getHours).setHeader("Hours"); // Spalte für Stunden

        // Ereignishandler für Auswahl eines Eintrags im Grid
        grid.asSingleSelect().addValueChangeListener(event -> {
            WorkTimeEntry entry = event.getValue();
            if (entry != null) {
                openEditDialog(entry); // Dialog zur Bearbeitung des Eintrags öffnen
            }
        });
    }

    // Methode zur Aktualisierung der Liste der Arbeitszeiteinträge für einen bestimmten Mitarbeiter
    private void updateList(String workerName) {
        List<WorkTimeEntry> entries = workTimeService.getWorkTimesForWorker(workerName); // Arbeitszeiteinträge abrufen
        grid.setItems(entries); // Einträge im Grid setzen
    }

    // Methode zur Öffnung eines Dialogs zur Bearbeitung eines Arbeitszeiteintrags
    private void openEditDialog(WorkTimeEntry entry) {
        Dialog dialog = new Dialog(); // Dialog erstellen
        dialog.setWidth("400px"); // Breite des Dialogs festlegen

        NumberField hoursField = new NumberField("Hours"); // Feld für die Stundenanzahl
        hoursField.setValue((double) entry.getHours()); // Wert des Feldes setzen

        // Buttons für Speichern und Abbrechen
        Button saveButton = new Button("Save", e -> {
            workTimeService.saveOrUpdateWorkTime(entry.getUsername(), entry.getDate(), hoursField.getValue().floatValue()); // Arbeitszeit aktualisieren
            dialog.close(); // Dialog schließen
            updateList(entry.getUsername()); // Liste aktualisieren
            Notification.show("Updated successfully."); // Benachrichtigung anzeigen
        });

        Button cancelButton = new Button("Cancel", e -> dialog.close()); // Abbrechen-Button

        // Layout für Buttons
        HorizontalLayout buttonsLayout = new HorizontalLayout(saveButton, cancelButton);
        dialog.add(hoursField, buttonsLayout); // Komponenten zum Dialog hinzufügen
        dialog.open(); // Dialog öffnen
    }
}
