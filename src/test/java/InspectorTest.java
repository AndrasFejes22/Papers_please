import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InspectorTest {
    /**
     * bulletin(s) :" Entrants require passport
     * Allow citizens of Arstotzka
     * Wanted by the State: Vadim Khan"
     * entrant's: nation: "Republia"
     * Expected test result: "Entry denied: citizen of banned nation."
     * -----------
     * November 23rd, 1982
     * ORDER: Entrants require passport
     * Allow citizens of Arstotzka
     * Wanted by the State: Otto Vyas
     * [passport]
     * [NATION: United Federation
     * DOB: 1952.03.05
     * SEX: F
     * ISS: Korista City
     * ID#: R2B26-M3PO5
     * EXP: 1981.09.29
     * NAME: Yankov, Cecelia]
     * Expected test result: Entry denied: passport expired--> a document is considered expired if the expiration date is November 22, 1982 or earlier
     * */

    @Test
    public void preliminaryTraining() {
        Inspector inspector = new Inspector();
        //inspector.receiveBulletin("Entrants require passport\nAllow citizens of Arstotzka, Obristan");
        //inspector.receiveBulletin("Citizens of Arstotzka require ID card");
        inspector.receiveBulletin("Workers require work pass");
        Map<String, String> johnDoe = new HashMap<>();
        johnDoe.put("work pass", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Doe, John\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15");


        Map<String, String> josef = new HashMap<>();
        josef.put("passport", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Costanza, Josef\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15");

        Map<String, String> guyovich = new HashMap<>();
        guyovich.put("access_permit", "NAME: Guyovich, Russian\nNATION: Obristan\nID#: TE8M1-V3N7R\nPURPOSE: TRANSIT\nDURATION: 14 DAYS\nHEIGHT: 159cm\nWEIGHT: 60kg\nEXP: 1983.07.13");

        Map<String, String> roman = new HashMap<>();
        roman.put("work pass", "ID#: WK9XA-LKM0Q\nNATION: Impor\nNAME: Dolanski, Roman\nDOB: 1933.01.01\nSEX: M\nISS: Shingleton\nEXP: 1983.05.12");
        roman.put("grant_of_asylum", "NAME: Dolanski, Roman\nNATION: Impor\nID#: WK9XA-LKM0Q\nDOB: 1933.01.01\nHEIGHT: 176cm\nWEIGHT: 71kg\nEXP: 1983.05.12");

        //assertEquals("Glory to Arstotzka.", inspector.inspect(josef));
        //assertEquals("Glory to Arstotzka.", inspector.inspect(johnDoe));
        assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(roman));//még nem jo


    }

    @Test
    public void preliminaryTraining2() {
        Inspector inspector = new Inspector();
        inspector.receiveBulletin("Entrants require passport\nAllow citizens of Arstotzka, Obristan\nWanted by the State: Dimitry Kovacs");
        //inspector.receiveBulletin("Citizens of Arstotzka require ID card");
        //inspector.receiveBulletin("Wanted by the State: Dimitry Kovacs");
        Map<String, String> DimitryKovacs = new HashMap<>();
        DimitryKovacs.put("passport", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Kovacs, Dimitry\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15");
        assertEquals("Detainment: Entrant is a wanted criminal.", inspector.inspect(DimitryKovacs));//még nem jo

    }

    @Test
    public void preliminaryTraining3() {
        Inspector inspector = new Inspector();
        inspector.receiveBulletin("Entrants require passport\nAllow citizens of Arstotzka\nWanted by the State: William Pearl");
        //inspector.receiveBulletin("Citizens of Arstotzka require ID card");
        //inspector.receiveBulletin("Wanted by the State: Dimitry Kovacs");
        Map<String, String> KostovetskyNaomi = new HashMap<>();
        //"passport", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Costanza, Josef\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15"
        KostovetskyNaomi.put("", "NATION: Impor\nDOB: 1924.12.15\nSEX: F\nISS: Haihan\nID#: AT8UD-H2RE3\nEXP: 1981.12.24\nNAME: Smirnov, Sophia");
        assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(KostovetskyNaomi));//még nem jo

    }

    //ORDER: Allow citizens of Antegria, Impor, Kolechia, Obristan, Republia, United Federation
    //example 1: Allow citizens of Obristan
    //example 2: Deny citizens of Kolechia, Republia
    @Test
    public void preliminaryTraining4() {
        Inspector inspector = new Inspector();
        inspector.receiveBulletin("Allow citizens of Antegria, Impor, Kolechia, Obristan, Republia, United Federation\nWanted by the State: William Pearl");
        //inspector.receiveBulletin("Citizens of Arstotzka require ID card");
        //inspector.receiveBulletin("Wanted by the State: Dimitry Kovacs");
        Map<String, String> KostovetskyNaomi = new HashMap<>();
        //"passport", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Costanza, Josef\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15"
        KostovetskyNaomi.put("", "NATION: Republia\nDOB: 1924.12.15\nSEX: F\nISS: Haihan\nID#: AT8UD-H2RE3\nEXP: 1981.12.24\nNAME: Smirnov, Sophia");
        assertEquals("Cause no trouble.", inspector.inspect(KostovetskyNaomi));//még nem jo

    }

    @Test
    void expireDatesTest() {
        assertEquals("Entry denied: passport expired.", Inspector.expiredDates("1981.09.20"));
        assertEquals("Entry denied: passport expired.", Inspector.expiredDates("1981.11.22"));
    }

    @Test
    void wantedPersonTest() {

        Map<String, String> josef = new HashMap<>();
        josef.put("passport", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Costanza, Josef\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15");

        assertTrue(Inspector.wantedPerson(josef, "Josef Costanza"));
        //assertFalse(Inspector.wantedPerson(josef, "Hubert Popovic"));
    }

    @Test
    void createPersonNameTest() {
        assertEquals("Josef Costanza", Inspector.createPersonName("Costanza, Josef"));
        assertEquals("Dimitry Kovacs", Inspector.createPersonName("Kovacs, Dimitry"));
    }

    @Test
    void extractDataFromPresonTest() {
        Map<String, String> johnDoe = new HashMap<>();
        johnDoe.put("work pass", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Doe, John\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15");

        assertEquals("1983.03.15", Inspector.extractDataFromPreson(johnDoe, "EXP"));
    }
}