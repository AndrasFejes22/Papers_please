import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        KostovetskyNaomi.put("", "NATION: United Federation\nDOB: 1924.12.15\nSEX: F\nISS: Haihan\nID#: AT8UD-H2RE3\nEXP: 1985.12.24\nNAME: Smirnov, Sophia");
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
        KostovetskyNaomi.put("passport", "NATION: Republia\nDOB: 1924.12.15\nSEX: F\nISS: Haihan\nID#: AT8UD-H2RE3\nEXP: 1984.12.24\nNAME: Smirnov, Sophia");
        //assertEquals("Cause no trouble.", inspector.inspect(KostovetskyNaomi));//még nem jo
        System.out.println("******************");
        //Deny citizens of Kolechia, Republia + Republia

        Inspector inspector2 = new Inspector();
        inspector2.receiveBulletin("Allow citizens of Arstotzka\nWanted by the State: William Pearl");
        Map<String, String> SmirnovSophia = new HashMap<>();
        SmirnovSophia.put("", "NATION: Republia\nDOB: 1924.12.15\nSEX: F\nISS: Haihan\nID#: AT8UD-H2RE3\nEXP: 1984.12.24\nNAME: Smirnov, Sophia");
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
    public void preliminaryTraining7() {
        Inspector inspector = new Inspector();
        inspector.receiveBulletin("Foreigners require access permit\nWanted by the State: Wilma Harkonnen");
        Map<String, String> KostovetskyNaomi = new HashMap<>();
        KostovetskyNaomi.put("passport", "NATION: Arstotzka\nDOB: 1924.12.15\nSEX: F\nISS: Haihan\nID#: AT8UD-H2RE3\nEXP: 1983-10-15\nNAME: Smirnov, Sophia");
        assertEquals("Glory to Arstotzka.", inspector.inspect(KostovetskyNaomi));//még nem jo
        System.out.println("******************");
        /**
         * EZ SEM JÓ MÉG!
         * KEY: passport
         * VALUE: NATION: Arstotzka
         * DOB: 1939.01.07
         * SEX: F
         * ISS: East Grestin
         * ID#: SK084-HRJQ1
         * EXP: 1985.08.04
         * NAME: Strauss, Cecelia
         * Nation?Arstotzka
         * ORDER: Foreigners require access permit
         * Wanted by the State: Erika Reichenbach
         * !!!expected:<[Glory to Arstotzka].> but was:<[Entry denied: missing required access permit].>
         * */
    }
    @Test
    public void preliminaryTraining8() {
        Inspector inspector = new Inspector();
        inspector.receiveBulletin("Allow citizens of Arstotzka\nWanted by the State: Wilma Harkonnen");
        Map<String, String> KostovetskyNaomi = new HashMap<>();
        KostovetskyNaomi.put("access_permit", "NATION: Obristan\nDOB: 1924.12.15\nSEX: F\nISS: Haihan\nID#: AT8UD-H2RE3\nEXP: 1983-10-15\nNAME: Smirnov, Sophia");
        assertEquals("Entry denied: citizen of banned nation.", inspector.inspect(KostovetskyNaomi));//még nem jo
        System.out.println("******************");
    }

    /**
     * TWO EXPIRES DATE!!!
     * date of entrant: 1984-03-11
     * exp date: 1982-11-22
     * November 25th, 1982
     * KEY: passport access_permit
     * VALUE: NATION: Obristan
     * DOB: 1926.07.26
     * SEX: M
     * ISS: Skal
     * ID#: JNRK1-F0BU3
     * EXP: 1982.06.18
     * NAME: Reed, Damian NAME: Reed, Damian
     * NATION: Obristan
     * ID#: JNRK1-F0BU3
     * PURPOSE: IMMIGRATE
     * DURATION: FOREVER
     * HEIGHT: 167.0cm
     * WEIGHT: 71.0kg
     * EXP: 1984.04.06
     * Nation?Obristan
     * ORDER: Foreigners require access permit
     * Wanted by the State: Olga Borshiki
     * */

    @Test
    public void preliminaryTraining9() {
        Inspector inspector = new Inspector();
        inspector.receiveBulletin("Allow citizens of Arstotzka\nWanted by the State: Wilma Harkonnen");
        Map<String, String> KostovetskyNaomi = new HashMap<>();
        KostovetskyNaomi.put("access_permit", "NATION: Arstotzka\nDOB: 1924.12.15\nSEX: F\nISS: Haihan\nID#: AT8UD-H2RE3\nEXP: 1981-10-15\nNAME: Smirnov, Sophia");
        KostovetskyNaomi.put("passport", "NATION: Arstotzka\nDOB: 1924.12.15\nSEX: F\nISS: Haihan\nID#: AT8UD-H2RE3\nEXP: 1983-02-14\nNAME: Smirnov, Sophia");
        assertEquals("Entry denied: passport expired.", inspector.inspect(KostovetskyNaomi));
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

    //[nationality] mismatch test

    @Test
    void mismatchTest() {
        Inspector inspector = new Inspector();
        inspector.receiveBulletin("Foreigners require access permit\n" +
                "Wanted by the State: Attila Ortiz");
        Map<String, String> josef = new HashMap<>();
        //name mismatch:
        josef.put("passport", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Costanza, Josef2\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1985.03.15");
        josef.put("work pass", "ID#: GC07D-FU8AR\nNATION: United Federation\nNAME: Costanza, Josef\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1985.03.15");
        assertEquals("Detainment: name mismatch.", inspector.inspect(josef));
    }

    @Test
    void expireDatesTest() {
        //a document is considered expired if the expiration date is November 22, 1982 or earlier
        Map<String, String> josef = new HashMap<>();
        Inspector inspector = new Inspector();
        josef.put("passport", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Costanza, Josef\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1985.03.15");
        josef.put("work pass", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Costanza, Josef\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1981.03.15");
        //assertTrue(Inspector.expiredDates(josef));

        Map<String, String> joe = new HashMap<>();
        inspector.receiveBulletin("ORDER: Foreigners require access permit\n" +
                "Wanted by the State: Amalie Feyd");
        joe.put("passport", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Costanza, Josef\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1982-11-22");
        joe.put("access permit", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Costanza, Josef\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1980-11-22");
        //assertTrue(Inspector.expiredDates(joe));


        assertEquals("Entry denied: access permit expired.", inspector.inspect(joe));
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
        assertEquals("Arstotzka", Inspector.extractDataFromPerson(johnDoe, "NATION"));
    }

    @Test
    void extractDataFromPreson2Test() {
        List<String> EXPdatas = new ArrayList<>();
        EXPdatas.add("1982.03.15");
        EXPdatas.add("1983.03.15");

        Map<String, String> johnDoe = new HashMap<>();
        //johnDoe.put("work pass", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Doe, John\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15");
        johnDoe.put("work pass", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Doe, John\nDOB: 1933-11-28\nSEX: M\nISS: East Grestin\nEXP: 1982.03.15");
        johnDoe.put("work pass2", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Doe, JANE\nDOB: 1933-11-28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15");


        assertEquals(EXPdatas, Inspector.extractDataFromPerson2(johnDoe, "EXP"));

        List<String> NAMEdatas = new ArrayList<>();
        NAMEdatas.add("Doe, John");
        NAMEdatas.add("Doe, JANE");

        assertEquals(NAMEdatas, Inspector.extractDataFromPerson2(johnDoe, "NAME"));

    }

}