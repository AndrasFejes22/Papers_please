import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InspectorTest {

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

}