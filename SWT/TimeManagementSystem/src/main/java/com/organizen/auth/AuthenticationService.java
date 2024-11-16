//Implementieren der Authentication
package com.organizen.auth;

import com.organizen.model.Worker;
import com.organizen.model.Supervisor;
import com.organizen.db.WorkTimeService;
import com.organizen.model.Employee;

// Einfache Authentifizierungslogik ohne echtes Sicherheitsmanagement
public class AuthenticationService {
	private final WorkTimeService workTimeService = null;

    public Employee authenticate(String username, String password) {
        // Hardcodierte Benutzerdaten für den Mock-Up
        if ("worker".equals(username) && "worker123".equals(password)) {
            // Erstelle eine neue Instanz von Worker für die Demo
            return new Worker(username, 1, Employee.Role.Worker, 10, 160, 8, this.workTimeService);
        } else if ("supervisor".equals(username) && "supervisor123".equals(password)) {
            // Erstelle eine neue Instanz von Supervisor für die Demo
            return new Supervisor(username, 2, Employee.Role.Supervisor, 100);
        }
        
        // Keine Übereinstimmung: Authentifizierung fehlgeschlagen
        return null;
    }
}