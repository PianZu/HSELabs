package com.test;

import com.organizen.db.WorkTimeService;
import com.organizen.model.Employee.Role; 

import com.organizen.model.Worker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.time.LocalDate;


    public class WorkerTests {

        private Worker worker;

        @BeforeEach
        public void setUp() {
            String name = "Hashmatulla Habibi";
            int id = 1;
            Role role = Role.Worker;  
            int vacationDays = 20;
            int workHours = 40;
            int flexTime = 10;
            WorkTimeService workTimeService = new WorkTimeService(null); 
            
            worker = new Worker(name, id, role, vacationDays, workHours, flexTime, workTimeService);
        }

        @Test
        public void testTakeFlexTimeSufficient() {
            worker.takeFlexTime(5);
            assertEquals(5, worker.getFlexTime(), "Flexzeit nach Abzug von 5 Stunden sollte 5 sein");
        }
        
        @Test
        public void testTakeFlexTimeInsufficient() {
            worker.takeFlexTime(15);
            assertEquals(10, worker.getFlexTime(), "Flexzeit sollte unverändert bleiben bei Versuch, mehr zu nehmen als verfügbar");
        }

        @Test
        public void testTakeFlexTimeExact() {
            worker.takeFlexTime(10);
            assertEquals(0, worker.getFlexTime(), "Flexzeit sollte auf 0 fallen, wenn exakt verfügbare Menge genommen wird");
        }
        
        @Test
        public void testEditWorkHours() {
            worker.editWorkHours(80);
            assertEquals(80, worker.getWorkHours(), "Arbeiterstunden sollte auf 80 geändert werden");
        }
        
        @Test
        public void testEditVacationDays() {
            worker.editVacationDays(50);
            assertEquals(50, worker.getVacationDays(), "Vacationdays sollten auf 50 geändert werden");           
    }
 }