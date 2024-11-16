package com.organizen.db;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.organizen.model.WorkTimeEntry;
import com.organizen.model.Worker;

import java.time.LocalDate;
import java.util.List;

//Service-Klasse zur Interaktion mit der Datenbank für Arbeitszeitdaten
@Service
public class WorkTimeService {
	// Instanz der NamedParameterJdbcTemplate-Klasse für die Datenbankinteraktion
    private final NamedParameterJdbcTemplate jdbcTemplate;
    // Konstruktor zur Initialisierung der Instanz
    public WorkTimeService(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    // Methode zur Abrufung von Arbeitszeitdaten für einen bestimmten Mitarbeiter
    public List<WorkTimeEntry> getWorkTimesForWorker(String workerName) {
        String sql = "SELECT * FROM timetable WHERE Username = :username";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", workerName);

        return jdbcTemplate.query(
            sql,
            params,
            // Mapping-Funktion, um das Abfrageergebnis in Objekte umzuwandeln
            (rs, rowNum) -> new WorkTimeEntry(
                rs.getString("Username"),
                rs.getDate("Date").toLocalDate(),
                rs.getFloat("Hours")
            )
        );
    }
    
    // Methode zum Speichern oder Aktualisieren von Arbeitszeitdaten
    public void saveOrUpdateWorkTime(String username, LocalDate date, float hours) {
        // SQL-Befehle für Update und Insert
        String updateSql = "UPDATE timetable SET Date = :Date WHERE Hours = :Hours AND Username = :Username";
        String insertSql = "INSERT INTO timetable (Date, Hours, Username) VALUES (:Date, :Hours, :Username)";
        
        // Mappe Parameter
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("Username", username);
        params.addValue("Date", java.sql.Date.valueOf(date));  // LocalDate in java.sql.Date konvertieren
        params.addValue("Hours", hours);  // Erwartet jetzt einen Float

        // Führe Update aus
        int updated = jdbcTemplate.update(updateSql, params);

        // Falls keine Zeilen aktualisiert wurden, führe ein Insert aus
        if (updated == 0) {
            jdbcTemplate.update(insertSql, params);
        }
    }

    // Methode zum Löschen von Einträgen für einen Mitarbeiter
    public void deleteWorkerEntries(Worker worker) {
        String sql = "DELETE FROM timetable WHERE Username = :username";
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", worker.getName());

        jdbcTemplate.update(sql, params);
    }
}
