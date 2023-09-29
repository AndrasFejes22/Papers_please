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
        System.out.println("KEY: "+getkey(person)); //person dokument
        System.out.println("VALUE: "+getValue(person)); //person dokument
        System.out.println("Nation?"+ extractDataFromPerson(person, "NATION"));
        System.out.println("ORDER: " + getBulletin());
        System.out.println("----------------------");
        System.out.println("size: " + person.size());
        String wantedPerson = "";
        Set<Map.Entry<String, String>> entrySet = person.entrySet();

        List<String> documents1 = new ArrayList<>();
        List<String> documents2 = new ArrayList<>();

        for (Map.Entry<String, String> entry : entrySet) {
            documents1.add(entry.getKey());
            documents2.add(entry.getValue());
        }

        String document = "";
        if (getBulletin().contains("passport")) {
            document = "passport";
        }
        if (getBulletin().contains("ID card")) {
            document = "ID card";
        }
        if (getBulletin().contains("access permit")) {//--> csereszabatos legyen az acces_permit-tel
            document = "access permit";
        }
        if (getBulletin().contains("grant_of_asylum")) {
            document = "grant_of_asylum";
        }
        if (getBulletin().contains("work pass")) {
            document = "work pass";
        }
        if (getBulletin().contains("Wanted")) {
            System.out.println("WANTED!");
            wantedPerson = createPersonNameFromBulletin(getBulletin());
            System.out.println("wantedPerson: " + wantedPerson);
            if (wantedPerson(person, wantedPerson)) {
                System.out.println("Wantedperson matching!");
                return "Detainment: Entrant is a wanted criminal.";
            }
        }
        //System.out.println("BANNED?");
        //System.out.println("getbanned: "+getBannedNation(person));
        //if (getBannedNation(person).equals("Deny")) {
        if (person.size() > 1) {
            if (comppareOfDatas(person)) {
                return "Detainment: ID number mismatch.";
            }
        }
        if (getBannedNation(person)) {
            return "Entry denied: citizen of banned nation.";
        } else if (expiredDates(extractDataFromPerson(person, "EXP"))) {
            return "Entry denied: passport expired.";
        /*} else if (person.size() > 1) {
            if (comppareOfDatas(person)) {
                return "Detainment: ID number mismatch.";
            }*/
        //} else if (!documents1.contains(document) && !documents2.contains(document)) {
        } else if (getBulletin().contains(document)&& !getkey(person).contains(document)) {
            return "Entry denied: missing required " + document + ".";
        } else {
            if(extractDataFromPerson(person, "NATION").equals("Arstotzka")){
                return "Glory to Arstotzka.";
            }
            return "Cause no trouble.";
        }
        //return "END";

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

    //public String getBannedNation(Map<String, String> person){
    public boolean getBannedNation(Map<String, String> person){
        //System.out.println(person);
        String data = extractDataFromPerson(person, "NATION");
        System.out.println("data:" + data);
        List<List<String>> nations = nations(getBulletin());
        System.out.println("nations.get(0): " + nations.get(0));
        //System.out.println("nations.get(1): "+nations.get(1));
        System.out.println("----");

        if (getBulletin().contains("Deny")) {
            for (int i = 0; i < nations.get(0).size(); i++) {
                if (nations.get(0).get(i).equals(data)) {
                    return true;
                }
            }

        } else if (getBulletin().contains("Allow")) {
            nations.get(0).add("Arstotzka");
            System.out.println("nations.get(0)_1: " + nations.get(0));
            for (int i = 0; i < nations.get(0).size(); i++) {
                if (nations.get(0).get(i).equals(data)) {
                    return false;
                }
            }

        }
        return true;

        /*
        for (int i = 0; i < nations.get(0).size(); i++) {
            System.out.println("FOR");
            System.out.println("i:"+nations.get(0).get(i));
            System.out.println((nations.get(0).get(i).equals(data) && getBulletin().contains("Deny")));
            if (nations.get(0).get(i).equals(data) && getBulletin().contains("Deny")) {

                System.out.println("getbanned_IF"+nations.get(0).get(i));
                return "Deny";

            } else if(nations.get(0).get(i).equals(data) && getBulletin().contains("Allow")){
                return "Allow";
            }
        }
        return "ERROR";
        */
    }


    // nations method--> HA a "Deny citizens of... vagy "Allow citizens of... részek nem az elején vannak? (mert akkor ebben hogy bulletinArray[0], a 0 nem igaz...)**
    //"Deny citizens of Kolechia, Republia\nWanted by the State: William Pearl"
    //"Deny citizens of Kolechia, Republia"
    public static List<List<String>> nations(String bulletin){
        int numDeny = 0;
        int numAllow = 0;
        List<List<String>> allowedAndDeniedNations = new ArrayList<>();
        List<String> allowedNationsList = new ArrayList<>();
        List<String> deniedNationsList = new ArrayList<>();
        //ORDER: Allow citizens of Antegria, Impor, Kolechia, Obristan, Republia, United Federation
        //example 1: Allow citizens of Obristan
        //example 2: Deny citizens of Kolechia, Republia
        if(bulletin.contains("Allow")) {
            //**akkor itt egy fori-ban meg kell nézni hogy a tömbben melyik stringben van az hogy "Deny"--> int
            String[] bulletinArray = bulletin.split("\n");
            for (int i = 0; i < bulletinArray.length; i++) {
                if(bulletinArray[i].contains("Allow")){
                    numDeny = i;
                }
            }
            String[] bulletinArray2 = bulletinArray[numDeny].split("of");//*és itt [int]
            String[] allovedNationsArray = bulletinArray2[1].split(",");
            for (int i = 0; i < allovedNationsArray.length; i++) {
                allowedNationsList.add(allovedNationsArray[i].trim());
            }
            allowedAndDeniedNations.add(allowedNationsList);

        } else if(bulletin.contains("Deny")){

            String[] bulletinArray = bulletin.split("\n");
            for (int i = 0; i < bulletinArray.length; i++) {
                if(bulletinArray[i].contains("Allow")){
                    numAllow = i;
                }
            }
            String[] bulletinArray2 = bulletinArray[numAllow].split("of");
            String[] deniedNationsArray = bulletinArray2[1].split(",");
            for (int i = 0; i < deniedNationsArray.length; i++) {
                deniedNationsList.add(deniedNationsArray[i].trim());
            }
            allowedAndDeniedNations.add(deniedNationsList);
        }
        return allowedAndDeniedNations;
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

    public static String extractDataFromPerson(Map<String, String> person, String input){
        List<String[]> stringArrayList = new ArrayList<>();
        String string = getDataString(person);
        System.out.println("String input: "+string);
        String[] results = string.split("\n");
        System.out.println("results: "+Arrays.toString(results));

        String extractedData = "";

        for (int i = 0; i < results.length; i++) {
            if(results[i].contains("NAME")){
                String[] pairs = results[i].split(": ");
                extractedData = pairs[1];
            } else {
                String[] pairs = results[i].split(", ");
                stringArrayList.add(pairs[0].split(" "));
            }

        }

        System.out.println("list: ");
        for (int i = 0; i < stringArrayList.size(); i++) {
            System.out.println(Arrays.toString(stringArrayList.get(i)));
        }
        System.out.println("list_0: ");
        System.out.println(Arrays.toString(stringArrayList.get(0)));
        System.out.println("get(0)[0]: " + stringArrayList.get(0)[0]);
        System.out.println("get(0)[1]: " + stringArrayList.get(0)[1]);

        for (int i = 0; i < stringArrayList.size(); i++) {
            for (int j = 0; j < stringArrayList.size(); j++) {

                if(stringArrayList.get(i)[0].contains(input)){
                    extractedData = stringArrayList.get(i)[1].trim();
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
        String[] array = input.split(": ");
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

    public String getkey(Map<String, String> person){
        StringBuilder stringBuilder = new StringBuilder();
        Set<Map.Entry<String, String>> entrySet = person.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            stringBuilder.append(entry.getKey() + " ");
        }
        return stringBuilder.toString();
    }

    public String getValue(Map<String, String> person){
        StringBuilder stringBuilder = new StringBuilder();
        Set<Map.Entry<String, String>> entrySet = person.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            stringBuilder.append(entry.getValue() + " ");
        }
        return stringBuilder.toString();
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
