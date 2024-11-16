package com.organizen.ui;

import com.organizen.db.WorkTimeService;
import com.organizen.model.Worker;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import org.springframework.beans.factory.annotation.Autowired;

// Benutzeroberfläche für Mitarbeiteransicht
@Route("workerView")
public class WorkerView extends VerticalLayout {

    private final WorkTimeService workTimeService;
    private Worker worker;

    // Konstruktor
    @Autowired
    public WorkerView(WorkTimeService workTimeService) {
        this.workTimeService = workTimeService;
        this.worker = new Worker("John Doe", 1, Worker.Role.Worker, 10, 40, 5, this.workTimeService); // Mitarbeiterobjekt initialisieren

        initUI(); // Benutzeroberfläche initialisieren
    }

    // Methode zur Initialisierung der Benutzeroberfläche
    private void initUI() {
        setSizeFull(); // Größe auf volle Höhe setzen
        setDefaultHorizontalComponentAlignment(Alignment.CENTER); // Standardausrichtung der horizontalen Komponenten auf Zentriert setzen
        setJustifyContentMode(JustifyContentMode.CENTER); // Ausrichtung des Inhalts auf Zentriert setzen
        setAlignItems(Alignment.CENTER); // Ausrichtung der Komponenten auf Zentriert setzen
        
        // Willkommensnachricht
        H1 companyText = new H1("STC-Company"); // Überschrift für Firmenname
        Span welcomeText = new Span("Welcome " + worker.getName() + "!"); // Begrüßungstext mit dem Namen des Mitarbeiters
        welcomeText.getElement().getStyle().set("font-size", "24px"); // Größe des Texts ähnlich wie H1 setzen

        add(companyText); // Firmenname hinzufügen
        add(welcomeText); // Willkommensnachricht hinzufügen

        // Navigationsbutton
        addNavigateButton(); // Navigationsbutton hinzufügen
    }

    // Methode zum Hinzufügen des Navigationsbuttons
    private void addNavigateButton() {
        Button switchViewButton = new Button("Timetable"); // Button für die Zeittabelle
        switchViewButton.getStyle().set("margin-top", "20px"); // Abstand zwischen der Willkommensnachricht und dem Button hinzufügen
        switchViewButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("worktimesheetview"))); // Ereignishandler für das Navigieren zur Zeittabelle
        add(switchViewButton); // Button hinzufügen
    }
}