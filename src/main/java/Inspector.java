import java.util.*;
import java.util.stream.Collectors;

public class Inspector {

    public String bulletin;
    //Map<String, String> person;

    public String getBulletin() {
        return bulletin;
    }

    //public Map<String, String> getPerson() {
        //return person;
    //}

    /**
     * String bulletin = "Entrants require passport\n" +
     *     "Allow citizens of Arstotzka, Obristan";
     *
     * Inspector inspector = new Inspector();
     * inspector.receiveBulletin(bulletin);
     *
     * Map<String, String> entrant1 = new HashMap<>();
     * entrant1.put("passport", "ID#: GC07D-FU8AR\n" +
     *     "NATION: Arstotzka\n" +
     *     "NAME: Guyovich, Russian\n" +
     *     "DOB: 1933.11.28\n" +
     *     "SEX: M\n" +
     *     "ISS: East Grestin\n" +
     *     "EXP: 1983.07.10\n"
     * );
     *
     * inspector.inspect(entrant1); // "Glory to Arstotzka."
     * */

    //There are a total of 7 countries: Arstotzka, Antegria, Impor, Kolechia, Obristan, Republia, and United Federation.

    public final static String ARSTOTZKA = "Arstotzka";
    public final static String ANTEGRIA = "Antegria";
    public final static String IMPOR = "Impor";
    public final static String KOLECHIA = "Kolechia";
    public final static String OBRISTAN = "Obristan";
    public final static String REPUBLIA = "Republia";
    public final static String UNITED_FEDERATION = "United Federation";

    public void receiveBulletin(String bulletin) {
        this.bulletin= bulletin;
    }

    public String inspect(Map<String, String> person) {
        System.out.println("ORDER: " +getBulletin());
        System.out.println("size: " +person.size());
        System.out.println(getTokensWithCollection(person));
        String document = "";
        if(getBulletin().contains("passport")){
            document = "passport";
        }
        if(getBulletin().contains("ID card")){
            document = "ID card";
        }
        if(getBulletin().contains("access permit")){
            document = "access permit";
        }
        if(getBulletin().contains("grant_of_asylum")){
            document = "grant_of_asylum";
        }
        if(getBulletin().contains("work pass")){
            document = "work pass";
        }

        Set<Map.Entry<String, String>> entrySet = person.entrySet();

        List<String> documents1 = new ArrayList<>();
        List<String> documents2 = new ArrayList<>();

        for (Map.Entry<String, String> entry : entrySet) {

            //System.out.println(entry.getKey() +" ---> " + entry.getValue());

            documents1.add(entry.getKey());
            documents2.add(entry.getValue());
        }

        //System.out.println("dok1: "+documents1);
        //System.out.println("dok2: "+documents2);



        //String[] array = documents2.toArray(new String[0]);
        //System.out.println("array: " + Arrays.toString(array));
        System.out.println("--------------------------------");
        //System.out.println(documents1);
        //System.out.println(documents2);
        if(!documents1.contains(document) && !documents2.contains(document) ){
            return "Entry denied: missing required " + document + ".";
        } else {
            return "Glory to Arstotzka.";
        }

    }

    public static List<String> getTokensWithCollection(Map<String, String> person) {

        StringBuilder stringBuilder = new StringBuilder();
        Set<Map.Entry<String, String>> entrySet = person.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            //str = entry.getValue();
            stringBuilder.append(entry.getValue());
        }
        String str = stringBuilder.toString();

        return Collections.list(new StringTokenizer(str, "\n")).stream()
                .map(token -> (String) token)
                .collect(Collectors.toList());
    }
}
