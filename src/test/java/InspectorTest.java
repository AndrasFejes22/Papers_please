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
        inspector.receiveBulletin("Entrants require passport\nAllow citizens of Arstotzka, Obristan");
        //inspector.receiveBulletin("Citizens of Arstotzka require ID card");
        //inspector.receiveBulletin("Workers require work pass");
        Map<String, String> johnDoe = new HashMap<>();
        johnDoe.put("work pass", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Doe, John\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15");


        Map<String, String> josef = new HashMap<>();
        josef.put("passport", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Costanza, Josef\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15");

        Map<String, String> guyovich = new HashMap<>();
        guyovich.put("access_permit", "NAME: Guyovich, Russian\nNATION: Obristan\nID#: TE8M1-V3N7R\nPURPOSE: TRANSIT\nDURATION: 14 DAYS\nHEIGHT: 159cm\nWEIGHT: 60kg\nEXP: 1983.07.13");

        Map<String, String> roman = new HashMap<>();
        roman.put("work pass", "ID#: WK9XA-LKM0Q\nNATION: Impor\nNAME: Dolanski, Roman\nDOB: 1933.01.01\nSEX: M\nISS: Shingleton\nEXP: 1983.05.12");
        roman.put("grant_of_asylum", "NAME: Dolanski, Roman\nNATION: Impor\nID#: Y3MNC-TPWQ2\nDOB: 1933.01.01\nHEIGHT: 176cm\nWEIGHT: 71kg\nEXP: 1983.05.12");

        //assertEquals("Glory to Arstotzka.", inspector.inspect(josef));
        //assertEquals("Glory to Arstotzka.", inspector.inspect(johnDoe));
        assertEquals("Detainment: ID number mismatch.", inspector.inspect(roman));//még nem jo

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
    public void preliminaryTraining4() {//value nem sortöréssel van nem jó ( valamelyik nem jó vagy sortörés van vagy space)
        Inspector inspector = new Inspector();
        inspector.receiveBulletin("Allow citizens of Antegria, Impor, Kolechia, Obristan, Republia, United Federation\nWanted by the State: Wilma Harkonnen");
        Map<String, String> KostovetskyNaomi = new HashMap<>();
        KostovetskyNaomi.put("", "NATION: United Federation\nDOB: 1924.12.15\nSEX: F\nISS: Haihan\nID#: AT8UD-H2RE3\nEXP: 1981.12.24\nNAME: Smirnov, Sophia");
        assertEquals("Cause no trouble.", inspector.inspect(KostovetskyNaomi));//még nem jo
        System.out.println("******************");
    }
    @Test
    public void preliminaryTraining5() {//value nem sortöréssel van nem jó ( valamelyik nem jó vagy sortörés van vagy space)
        Inspector inspector = new Inspector();
        inspector.receiveBulletin("Allow citizens of Antegria, Impor, Kolechia, Obristan, Republia, United Federation\nWanted by the State: Wilma Harkonnen");
        Map<String, String> KostovetskyNaomi = new HashMap<>();
        KostovetskyNaomi.put("", "NATION: United Federation\nDOB: 1924.12.15\nSEX: F\nISS: Haihan\nID#: AT8UD-H2RE3\nEXP: 1981-10-15\nNAME: Smirnov, Sophia");
        assertEquals("Entry denied: passport expired.", inspector.inspect(KostovetskyNaomi));//még nem jo
        System.out.println("******************");
    }



    @Test
    public void preliminaryTraining6() {

        Inspector inspector = new Inspector();
        inspector.receiveBulletin("Entrants require passport\nAllow citizens of Antegria, Impor, Kolechia, Obristan, Republia, United Federation\nWanted by the State: William Pearl");
        Map<String, String> KostovetskyNaomi = new HashMap<>();
        KostovetskyNaomi.put("passport", "NATION: Republia\nDOB: 1924.12.15\nSEX: F\nISS: Haihan\nID#: AT8UD-H2RE3\nEXP: 1981.12.24\nNAME: Smirnov, Sophia");
        assertEquals("Cause no trouble.", inspector.inspect(KostovetskyNaomi));//még nem jo
        System.out.println("******************");
        //Deny citizens of Kolechia, Republia + Republia

        Inspector inspector2 = new Inspector();
        inspector2.receiveBulletin("Deny citizens of Kolechia, Republia\nWanted by the State: William Pearl");
        Map<String, String> SmirnovSophia = new HashMap<>();
        SmirnovSophia.put("", "NATION: Republia\nDOB: 1924.12.15\nSEX: F\nISS: Haihan\nID#: AT8UD-H2RE3\nEXP: 1981.12.24\nNAME: Smirnov, Sophia");
        assertEquals("Entry denied: citizen of banned nation.", inspector2.inspect(SmirnovSophia));
        //Deny citizens of Kolechia, Republia + Arstotzka
        /*
        Inspector inspector3 = new Inspector();
        inspector3.receiveBulletin("Deny citizens of Kolechia, Republia\nWanted by the State: William Pearl");
        Map<String, String> joe = new HashMap<>();
        joe.put("", "NATION: Arstotzka\nDOB: 1924.12.15\nSEX: F\nISS: Haihan\nID#: AT8UD-H2RE3\nEXP: 1981.12.24\nNAME: Smirnov, Sophia");
        assertEquals("Glory to Arstotzka.", inspector3.inspect(joe));

         */


    }

    @Test
    public void preliminaryTraining7() {//value nem sortöréssel van nem jó ( valamelyik nem jó vagy sortörés van vagy space)
        Inspector inspector = new Inspector();
        inspector.receiveBulletin("Foreigners require access permit\nWanted by the State: Wilma Harkonnen");
        Map<String, String> KostovetskyNaomi = new HashMap<>();
        KostovetskyNaomi.put("access_permit", "NATION: United Federation\nDOB: 1924.12.15\nSEX: F\nISS: Haihan\nID#: AT8UD-H2RE3\nEXP: 1983-10-15\nNAME: Smirnov, Sophia");
        assertEquals("Cause no trouble.", inspector.inspect(KostovetskyNaomi));//még nem jo
        System.out.println("******************");
    }
    @Test
    public void preliminaryTraining8() {//value nem sortöréssel van nem jó ( valamelyik nem jó vagy sortörés van vagy space)
        Inspector inspector = new Inspector();
        inspector.receiveBulletin("Allow citizens of Arstotzka\nWanted by the State: Wilma Harkonnen");
        Map<String, String> KostovetskyNaomi = new HashMap<>();
        KostovetskyNaomi.put("access_permit", "NATION: Obristan\nDOB: 1924.12.15\nSEX: F\nISS: Haihan\nID#: AT8UD-H2RE3\nEXP: 1983-10-15\nNAME: Smirnov, Sophia");
        assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(KostovetskyNaomi));//még nem jo
        System.out.println("******************");
    }


    @Test
    void nationsTest() {
        String bulletin = "Allow citizens of Antegria, Impor, Kolechia, Obristan, Republia, United Federation";
        System.out.println(Inspector.nations(bulletin));
        System.out.println("------------------");
        String bulletin2 = "Deny citizens of Antegria, Impor, Kolechia, Obristan, Republia";
        System.out.println(Inspector.nations(bulletin2));
    }

    @Test
    void expireDatesTest() {
        //a document is considered expired if the expiration date is November 22, 1982 or earlier
        Map<String, String> josef = new HashMap<>();
        josef.put("passport", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Costanza, Josef\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1981.03.15");
        assertTrue(Inspector.expiredDates(josef));

        Map<String, String> joe = new HashMap<>();
        joe.put("passport", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Costanza, Josef\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1981-03-15");
        assertTrue(Inspector.expiredDates(joe));


        //assertEquals("Entry denied: passport expired.", Inspector.expiredDates("1981.11.22"));
    }

    @Test
    void wantedPersonTest() {

        Map<String, String> josef = new HashMap<>();
        josef.put("passport", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Costanza, Josef\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15");

        assertTrue(Inspector.wantedPerson(josef, "Josef Costanza"));
        //assertFalse(Inspector.wantedPerson(josef, "Hubert Popovic"));
    }

    //new test:

    @Test
    void compareOfDataTest() {
        Map<String, String> josef = new HashMap<>();
        josef.put("passport", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Costanza, Josef\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15");
        josef.put("access_permit", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Costanza, Josef\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1984.01.27");

        assertFalse(Inspector.comppareOfDatas(josef));

        Map<String, String> joe = new HashMap<>();
        joe.put("passport", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Doe, Joe\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15");
        joe.put("access_permit", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Doe, Jane\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1984.01.27");

        //assertTrue(Inspector.comppareOfDatas(joe));


    }

    /**
     * All documents are current (ie. none have expired) -- a document is considered expired if the expiration date is November 22, 1982 or earlier
     * azaz az EXP lehet mismatch ha nem expired!
     * KEY: passport access_permit
     * VALUE: NATION: Kolechia
     * DOB: 1940.09.15
     * SEX: F
     * ISS: Yurko City
     * ID#: WZ06L-DA5RB
     * EXP: 1984.01.27
     * NAME: Owsianka, Jessica NAME: Owsianka, Jessica
     * NATION: Kolechia
     * ID#: WZ06L-DA5RB
     * PURPOSE: TRANSIT
     * DURATION: 14 DAYS
     * HEIGHT: 188.0cm
     * WEIGHT: 102.0kg
     * EXP: 1983.01.13
     * Nation?Kolechia
     * ORDER: Foreigners require access permit
     * Wanted by the State: Isabella Anderson
     * */



    @Test
    void createPersonNameTest() {
        assertEquals("Josef Costanza", Inspector.createPersonName("Costanza, Josef"));
        assertEquals("Dimitry Kovacs", Inspector.createPersonName("Kovacs, Dimitry"));
    }

    @Test
    void extractDataFromPresonTest() {
        Map<String, String> johnDoe = new HashMap<>();
        //johnDoe.put("work pass", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Doe, John\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15");
        johnDoe.put("work pass", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Doe, John\nDOB: 1933-11-28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15");
        johnDoe.put("work pass2", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Doe, John\nDOB: 1933-11-28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15");

        //assertEquals("1983.03.15", Inspector.extractDataFromPerson(johnDoe, "EXP"));
        //assertEquals("Arstotzka", Inspector.extractDataFromPerson(johnDoe, "NATION"));
        assertEquals("Doe, John", Inspector.extractDataFromPerson(johnDoe, "NAME"));
        assertEquals("1933-11-28", Inspector.extractDataFromPerson(johnDoe, "DOB"));
    }
}