import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Inspector {

    public String bulletin;
    Map<String, String> person;

    public String getBulletin() {
        return bulletin;
    }

    public Map<String, String> getPerson() {
        return person;
    }

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
        String document = "";
        if(getBulletin().contains("passport")){
            document = "passport";
        }
        if(getBulletin().contains("access permit")){
            document = "access permit";
        }
        if(getBulletin().contains("access permit")){
            document = "access permit";
        }
        if(getBulletin().contains("grant_of_asylum")){
            document = "grant_of_asylum";
        }

        Set<Map.Entry<String, String>> entrySet = person.entrySet();

        List<String> documents1 = new ArrayList<>();
        List<String> documents2 = new ArrayList<>();

        for (Map.Entry<String, String> entry : entrySet) {

            documents1.add(entry.getKey());
            documents2.add(entry.getValue());
        }
        System.out.println(documents1);
        System.out.println(documents2);
        if(!documents1.contains(document) && !documents2.contains(document) ){
            return "Entry denied: missing required " + document + ".";
        } else {
            return "Glory to Arstotzka.";
        }

    }
}
