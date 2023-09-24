import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Inspector inspector = new Inspector();
        inspector.receiveBulletin("Entrants require passport\nAllow citizens of Arstotzka, Obristan");

        String str = "ID#: GC07D-FU8AR\n" +
                "NATION: Arstotzka\n" +
                "NAME: Guyovich, Russian\n" +
                "DOB: 1933.11.28\n" +
                "SEX: M\n" +
                "ISS: East Grestin\n" +
                "EXP: 1983.07.10\n";

        //System.out.println("TOKENS: "+getTokensWithCollection(str));

        System.out.println("-------------------------------------");


        Map<String, String> roman = new HashMap<>();
        roman.put("passport", "ID#: WK9XA-LKM0Q\nNATION: United Federation\nNAME: Dolanski, Roman\nDOB: 1933.01.01\nSEX: M\nISS: Shingleton\nEXP: 1983.05.12");
        roman.put("grant_of_asylum", "NAME: Dolanski, Roman\nNATION: United Federation\nID#: Y3MNC-TPWQ2\nDOB: 1933.01.01\nHEIGHT: 176cm\nWEIGHT: 71kg\nEXP: 1983.09.20");

        System.out.println("inspect roman: ");
        System.out.println(inspector.inspect(roman));

        /*
        System.out.println(Inspector.getTokensWithCollection(roman));

        System.out.println("****************************************");

        Map<String, String> entrant1 = new HashMap<>();
        entrant1.put("passport", "ID#: GC07D-FU8AR\n" +
                "NATION: Arstotzka\n" +
                "NAME: Guyovich, Russian\n" +
                "DOB: 1933.11.28\n" +
                "SEX: M\n" +
                "ISS: East Grestin\n" +
                "EXP: 1983.07.10\n"
        );

        System.out.println("Roman:");

        Set<Map.Entry<String, String>> entrySet = roman.entrySet();

        for (Map.Entry<String, String> entry : entrySet) {

            System.out.println(entry.getKey() +" ---> " + entry.getValue());
        }

        System.out.println();

        System.out.println("guyowitch: ");

        Map<String, String> guyovich = new HashMap<>();
        guyovich.put("access_permit", "NAME: Guyovich, Russian\nNATION: Obristan\nID#: TE8M1-V3N7R\nPURPOSE: TRANSIT\nDURATION: 14 DAYS\nHEIGHT: 159cm\nWEIGHT: 60kg\nEXP: 1983.07.13");

        Set<Map.Entry<String, String>> entrySet2 = guyovich.entrySet();

        for (Map.Entry<String, String> entry : entrySet2) {

            System.out.println(entry.getKey() +" ---> " + entry.getValue());
        }

         */


    }

    public static List<String> getTokensWithCollection(String str) {
        return Collections.list(new StringTokenizer(str, "\n")).stream()
                .map(token -> (String) token)
                .collect(Collectors.toList());
    }


}
