package com.organizen.ui;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

// Klasse für das Login-Fenster
@PWA(name = "Login Window",
        shortName = "Login Window",
        description = "Login Window for different users.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")  // Einbindung von CSS-Dateien
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@Route("")  // Routing für die Startseite der Anwendung
public class LoginWindow extends VerticalLayout {

    // Konstruktor zur Initialisierung der UI-Komponenten
    public LoginWindow() {
        setSizeFull();  // Setzen der Größe auf volle Höhe
        setAlignItems(Alignment.CENTER);  // Zentrieren der Komponenten
        setJustifyContentMode(JustifyContentMode.CENTER);  // Zentrieren der Komponenten

        // Textfeld für Benutzername
        TextField usernameField = new TextField("User");
        usernameField.addThemeName("bordered");  // Hinzufügen eines benutzerdefinierten Themes

        // Passwortfeld
        PasswordField passwordField = new PasswordField("Password");
        passwordField.addThemeName("bordered");  // Hinzufügen eines benutzerdefinierten Themes

        // Login-Button
        Button loginButton = new Button("Login");
        loginButton.addClickListener(e -> handleLogin(usernameField.getValue(), passwordField.getValue()));  // Event-Handler für den Login-Button

        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);  // Setzen von Button-Varianten für das Aussehen
        loginButton.addClickShortcut(Key.ENTER);  // Hinzufügen einer Tastenkombination, um das Einloggen mit der Enter-Taste zu ermöglichen

        // Hinzufügen der UI-Komponenten zum Layout
        add(usernameField, passwordField, loginButton);
    }

    // Methode zur Behandlung des Login-Vorgangs
    private void handleLogin(String username, String password) {
        // Überprüfen von Benutzername und Passwort
        if ("worker".equals(username) && "worker123".equals(password)) {
            // Weiterleitung zur Arbeitsplatzansicht für den Mitarbeiter
            UI.getCurrent().navigate("workerView");
            Notification.show("Eingeloggt als Worker");
        } else if ("supervisor".equals(username) && "supervisor123".equals(password)) {
            // Weiterleitung zur Ansicht für den Supervisor
            UI.getCurrent().navigate("supervisorView");
            Notification.show("Eingeloggt als Supervisor");
        } else {
            // Benachrichtigung bei falschen Anmeldeinformationen
            Notification.show("Falscher Benutzername oder Passwort", 5000, Notification.Position.MIDDLE);
        }
    }
}