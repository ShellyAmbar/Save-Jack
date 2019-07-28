package com.shellyambar.thegame.Questions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class CountryQuestion {

    private static volatile CountryQuestion instance;

    public static CountryQuestion getInstance() {
        if (instance == null) {
            synchronized (CountryQuestion.class) {
                if (instance == null) {
                    instance = new CountryQuestion();
                }
            }
        }

        return instance;
    }

    private Random rand;
    private String country;
    private String capital;
    private HashMap<String, String> countriesCapitals;

    private CountryQuestion() {
        rand = new Random();

        countriesAndCapitals();
    }

    private void countriesAndCapitals() {
        countriesCapitals = new HashMap<>();

        countriesCapitals.put("argentina", "bouenos_aires");
        countriesCapitals.put("australia", "canberra");
        countriesCapitals.put("austria", "vienna");
        countriesCapitals.put("belgium", "brussels");
        countriesCapitals.put("bosnia", "sarayevo");
        countriesCapitals.put("brazil", "brasilia");
        countriesCapitals.put("bulgaria", "sofia");
        countriesCapitals.put("canada", "ottawa");
        countriesCapitals.put("chile", "santiago");
        countriesCapitals.put("china", "beijing");
        countriesCapitals.put("colombia", "bogota");
        countriesCapitals.put("croatia", "zagreb");
        countriesCapitals.put("cuba", "havana");
        countriesCapitals.put("cyprus", "nicosia");
        countriesCapitals.put("czech_republic", "prague");
        countriesCapitals.put("denmark", "copenhagen");
        countriesCapitals.put("egypt", "cairo");
        countriesCapitals.put("ethiopia", "addis_ababa");
        countriesCapitals.put("finland", "helsinki");
        countriesCapitals.put("france", "paris");
        countriesCapitals.put("germany", "berlin");
        countriesCapitals.put("greece", "athens");
        countriesCapitals.put("hungary", "budapest");
        countriesCapitals.put("india", "new_delhi");
        countriesCapitals.put("iran", "tehran");
        countriesCapitals.put("iraq", "baghdad");
        countriesCapitals.put("ireland", "dublin");
        countriesCapitals.put("israel", "jerusalem");
        countriesCapitals.put("italy", "rome");
        countriesCapitals.put("jamaica", "kingston");
        countriesCapitals.put("japan", "tokyo");
        countriesCapitals.put("jordan", "amman");
        countriesCapitals.put("kenya", "nairobi");
        countriesCapitals.put("kuwait", "kuwait_city");
        countriesCapitals.put("latvia", "riga");
        countriesCapitals.put("lebanon", "beirut");
        countriesCapitals.put("luxembourg", "luxembourg");
        countriesCapitals.put("malaysia", "kuala_lumpur");
        countriesCapitals.put("mexico", "mexico_city");
        countriesCapitals.put("monaco", "monaco");
        countriesCapitals.put("morocco", "rabat");
        countriesCapitals.put("nepal", "katmandu");
        countriesCapitals.put("netherlands", "amsterdam");
        countriesCapitals.put("new_zealand", "wellington");
        countriesCapitals.put("norway", "oslo");
        countriesCapitals.put("peru", "lima");
        countriesCapitals.put("philippines", "manila");
        countriesCapitals.put("poland", "warsaw");
        countriesCapitals.put("portugal", "lisbon");
        countriesCapitals.put("romania", "bucharest");
        countriesCapitals.put("russia", "moscow");
        countriesCapitals.put("san_marino", "san_marino");
        countriesCapitals.put("saudi_arabia", "riyadh");
        countriesCapitals.put("serbia", "belgrade");
        countriesCapitals.put("singapore", "singapore");
        countriesCapitals.put("slovakia", "bratislava");
        countriesCapitals.put("south_korea", "seoul");
        countriesCapitals.put("spain", "madrid");
        countriesCapitals.put("sweden", "stockholm");
        countriesCapitals.put("switzerland", "bern");
        countriesCapitals.put("thailand", "bangkok");
        countriesCapitals.put("tunisia", "tunis");
        countriesCapitals.put("turkey", "ankara");
        countriesCapitals.put("ukraine", "kiev");
        countriesCapitals.put("united_kingdom", "london");
        countriesCapitals.put("united_states", "washington");
        countriesCapitals.put("uruguay", "monteviedo");
        countriesCapitals.put("uzbekistan", "tashkent");
        countriesCapitals.put("vatican_city", "vatican_city");
    }

    public void CreateCountryQuestion(){
        int randNum = rand.nextInt(countriesCapitals.size());

        country = countriesCapitals.keySet().toArray()[randNum].toString();
        capital = countriesCapitals.get(country);
    }

    public ArrayList<String> Options() {
        ArrayList<String> options = new ArrayList<>();

        options.add(country);

        addWrongOption(options);
        addWrongOption(options);
        addWrongOption(options);

        Collections.shuffle(options);

        return options;
    }

    private void addWrongOption(ArrayList<String> options) {
        int randNum = rand.nextInt(countriesCapitals.size());
        String randCountry = countriesCapitals.keySet().toArray()[randNum].toString();

        if(randCountry.equals(country) || options.contains(randCountry)) {
            addWrongOption(options);
        }
        else {
            options.add(randCountry);
        }
    }

    public String getCountry() {
        return country;
    }

    public String getCapital() { return capital; }

    public HashMap<String, String> getCountriesCapitals() {
        return countriesCapitals;
    }

}
