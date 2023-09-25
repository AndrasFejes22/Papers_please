import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public final static String expireDate = "1981.11.22";

    public void receiveBulletin(String bulletin) {
        this.bulletin= bulletin;
    }

    public String inspect(Map<String, String> person) {
        System.out.println("ORDER: " +getBulletin());
        System.out.println("size: " +person.size());
        String wantedPerson = "";

        //System.out.println(getTokensWithCollection(person));
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
        if(getBulletin().contains("Wanted")){
            wantedPerson = createPersonNameFromBulletin(getBulletin());
            if(wantedPerson(person, wantedPerson)){
                return "Detainment: Entrant is a wanted criminal.";
            }
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
        //Entry denied: citizen of banned nation.
        if(getBannedNation(person)){
            return "Entry denied: citizen of banned nation.";
        }
        if(expiredDates(extractDataFromPreson(person, "EXP"))){
            return "Entry denied: passport expired.";
        }
        if(person.size() > 1){
            if(comppareOfDatas(person)){
                return "Detainment: ID number mismatch.";
            }
        }
        if(!documents1.contains(document) && !documents2.contains(document) ){
            return "Entry denied: missing required " + document + ".";
        } else {
            return "Glory to Arstotzka.";
        }

    }

    public static boolean expiredDates(String date){
        //a document is considered expired if the expiration date is November 22, 1982 or earlier

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate dateOfEntrant = LocalDate.parse(date, formatter);
        System.out.println("date of entrant: "+dateOfEntrant);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate dateOfExpire = LocalDate.parse(expireDate, formatter2);
        System.out.println("exp date: "+dateOfExpire);

        if(dateOfEntrant.isEqual(dateOfExpire) || dateOfEntrant.isBefore(dateOfExpire)){
            return true;
        }
        return false;
    }

    public static boolean comppareOfDatas(Map<String, String> person) {
        List<String[]> stringAttayList = new ArrayList<>();
        String string = getDataString(person);
        String[] results = string.split("\n");
        System.out.println(Arrays.toString(results));
        //System.out.println("results[0]: "+results[0]);
        //System.out.println("results[8]: "+results[8]);
        for (int i = 0; i < results.length; i++) {

            String[] pairs = results[i].split(": ");
            stringAttayList.add(pairs);

        }
        /*
        System.out.println("pairsList: " + Arrays.toString(stringAttayList.get(0)));
        System.out.println("pairsList: " + Arrays.toString(stringAttayList.get(8)));
        System.out.println("pairsList: " + stringAttayList.get(0)[1]);
        System.out.println("pairsList: " + stringAttayList.get(8)[1]);

         */

        for (int i = 0; i < stringAttayList.size(); i++) {
            for (int j = 0; j < stringAttayList.size(); j++) {
                if(stringAttayList.get(i)[0].equals(stringAttayList.get(j)[0]) && (!stringAttayList.get(i)[1].equals(stringAttayList.get(j)[1]))){
                    System.out.println("mismatch: "+stringAttayList.get(i)[1] +" ---> "+ stringAttayList.get(j)[1]);
                    return true;
                }
            }

        }

        return false;
    }

    public static boolean getBannedNation(Map<String, String> person){
        List<String[]> stringAttayList = new ArrayList<>();
        String string = getDataString(person);
        String[] results = string.split("\n");
        System.out.println(Arrays.toString(results));

        for (int i = 0; i < results.length; i++) {
            String[] pairs = results[i].split(": ");
            stringAttayList.add(pairs);
        }

        for (int i = 0; i < stringAttayList.size(); i++) {
            for (int j = 0; j < stringAttayList.size(); j++) {
                if(stringAttayList.get(i)[1].equals(IMPOR) || stringAttayList.get(i)[1].equals(ARSTOTZKA)){ // nem jó, lehet: Allow citizens of Arstotzka is!
                    System.out.println("nation: "+stringAttayList.get(i)[1]);
                    return true;
                }
            }

        }

        return false;
    }

    public static boolean wantedPerson(Map<String, String> person, String wantedPerson){
        List<String[]> stringAttayList = new ArrayList<>();
        String string = getDataString(person);
        String[] results = string.split("\n");
        System.out.println(Arrays.toString(results));

        String personName = "";
        String alteredName = "";

        for (int i = 0; i < results.length; i++) {
            String[] pairs = results[i].split(": ");
            stringAttayList.add(pairs);
        }

        for (int i = 0; i < stringAttayList.size(); i++) {
            for (int j = 0; j < stringAttayList.size(); j++) {
                if(stringAttayList.get(i)[0].equals("NAME")){//nem jo csak kopipésztelve lett
                    personName = stringAttayList.get(i)[1];
                    alteredName = createPersonName(personName);

                    //System.out.println("nation: "+stringAttayList.get(i)[0]);
                    if(alteredName.equals(wantedPerson)) {
                        return true;
                    }
                }
            }

        }

        return false;
    }

    public static String extractDataFromPreson(Map<String, String> person, String input){
        List<String[]> stringAttayList = new ArrayList<>();
        String string = getDataString(person);
        String[] results = string.split("\n");
        System.out.println(Arrays.toString(results));

        String extractedData = "";

        for (int i = 0; i < results.length; i++) {
            String[] pairs = results[i].split(": ");
            stringAttayList.add(pairs);
        }

        for (int i = 0; i < stringAttayList.size(); i++) {
            for (int j = 0; j < stringAttayList.size(); j++) {
                if(stringAttayList.get(i)[0].equals(input)){//nem jo csak kopipésztelve lett
                    extractedData = stringAttayList.get(i)[1];
                }
            }

        }

        return extractedData;
    }

    public static String createPersonName(String input){ //Costanza, Josef
        String[] array = input.split(", ");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(array[1]).append(" ").append(array[0]);
        return stringBuilder.toString();
    }

    public static String createPersonNameFromBulletin(String input){ //Wanted by the State: Hubert Popovic
        String[] array = input.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(array[1]);
        return stringBuilder.toString();
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

    public static String getDataString(Map<String, String> person) {

        StringBuilder stringBuilder = new StringBuilder();
        Set<Map.Entry<String, String>> entrySet = person.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            //str = entry.getValue();
            if(entry.getValue().contains("NAME")){ // a NAME-nál be kell tenni egy sortörést több sor (több pu) esetén
                stringBuilder.append(entry.getValue()).append("\n");
            } else {
                stringBuilder.append(entry.getValue());
            }
        }
        String str = stringBuilder.toString();

        return str;

    }

}
