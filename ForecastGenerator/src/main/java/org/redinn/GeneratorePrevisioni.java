package org.redinn;
import java.util.Random;

public class GeneratorePrevisioni {

    // Classe contenente i metodi necessari per generare le previsioni casualmente e i gli attributi necessari

    private static final Random random = new Random();
    private static final String[] statiMeteo = {"Soleggiato", "Nuvoloso", "Parzialmente nuvoloso", "Pioggerella", "Pioggia", "Nebbia", "Temporale", "Neve", "Tempesta di neve", "Grandine"};

    public static int generatoreTemp(){
        return random.nextInt(71)-25; // Massima generabile: 45 gradi e minima -25
    }

    public static int generatoreUmidita(){
        return random.nextInt(101); // Umidita' in percentuale, quindi generiamo da 0 a 100
    }

    public static double generatoreVento(){
        return random.nextDouble()*20;      // Generazione velocita' vento, da 0 a 20 m/s
    }

    // Con questo metodo verra' generato lo stato generale previsto, cio' implica quindi un controllo sulla media della temperatura per evitare previsioni insensate
    public static String generatoreStato(int mediaTemp){

        if(mediaTemp > 30){
            return statiMeteo[random.nextInt(3)];
        }
        else if(mediaTemp > 25){
            return statiMeteo[random.nextInt(5)];
        }
        else if(mediaTemp <= 0){
            return statiMeteo[random.nextInt(10)];
        }
        else{
            return statiMeteo[random.nextInt(7)];
        }

    }
}
