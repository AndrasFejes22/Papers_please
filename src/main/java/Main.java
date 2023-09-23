import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Map<String, String> entrant1 = new HashMap<>();
        entrant1.put("passport", "ID#: GC07D-FU8AR\n" +
                "NATION: Arstotzka\n" +
                "NAME: Guyovich, Russian\n" +
                "DOB: 1933.11.28\n" +
                "SEX: M\n" +
                "ISS: East Grestin\n" +
                "EXP: 1983.07.10\n"
        );

        Set<Map.Entry<String, String>> entrySet = entrant1.entrySet();

        for (Map.Entry<String, String> entry : entrySet) {

            System.out.println(entry.getKey() +" ---> " + entry.getValue());
        }

        Map<String, String> guyovich = new HashMap<>();
        guyovich.put("access_permit", "NAME: Guyovich, Russian\nNATION: Obristan\nID#: TE8M1-V3N7R\nPURPOSE: TRANSIT\nDURATION: 14 DAYS\nHEIGHT: 159cm\nWEIGHT: 60kg\nEXP: 1983.07.13");

        Set<Map.Entry<String, String>> entrySet2 = guyovich.entrySet();

        for (Map.Entry<String, String> entry : entrySet2) {

            System.out.println(entry.getKey() +" ---> " + entry.getValue());
        }
    }


}
