import java.util.Random;

public class RandomWeatherGenerator {

    private static final Random random = new Random();
    private static final String[] statiMeteo = {"Soleggiato", "Nuvoloso", "Pioggia", "Temporale", "Neve", "Nebbia", "Vento forte", "Pioggerella", "Grandine", "Parzialmente nuvoloso"};

    public static int generatoreTemp(){
        return random.nextInt(71)-25; // Massima generabile: 45 gradi e minima -25
    }

    public static int generatoreUmidita(){
        return random.nextInt(101);
    }

    public static double generatoreVento(){
        return random.nextDouble()*20;
    }

    public static String generatoreStato(){
        return statiMeteo[random.nextInt(10)];
    }
}
