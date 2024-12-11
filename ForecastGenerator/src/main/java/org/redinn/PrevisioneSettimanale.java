package org.redinn;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;

public class PrevisioneSettimanale extends Previsione {

    // Classe per effettuare la previsione settimanale, la quale parte dal giorno stesso (oggi) ed effettua la previsione di 7 giorni

    protected int mediaSettimanale;

    public PrevisioneSettimanale(){
        super();
    }

    @Override
    public void rigeneraPrevisioni() {
        minTemp = GeneratorePrevisioni.generatoreTemp();
        do{
            maxTemp = GeneratorePrevisioni.generatoreTemp();
        }while (maxTemp < minTemp || maxTemp-25 > minTemp); // Impostiamo un limite di 25 gradi massimi per l'escursione termica

        umidita = GeneratorePrevisioni.generatoreUmidita();
        vento = GeneratorePrevisioni.generatoreVento();
        statoMeteo = GeneratorePrevisioni.generatoreStato((minTemp+maxTemp)/2);
    }

    @Override
    public void StampaPrevisioni() throws InterruptedException {
        int giornoAttuale = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        String day; // Variabile usata per contenere il nome del giorno della settimana

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        for(int i=0; i<7; i++){
            Thread.sleep(100);

            day = giorniSettimana[giornoAttuale-1];
            if(i == 0){
                day += " (Oggi)";
            }
            System.out.println(ColorText.PURPLE + day + ": " + ColorText.YELLOW + "Media temeperatura: " + (minTemp+maxTemp)/2 + "°");
            System.out.println(ColorText.RED + "Max temp: " + maxTemp + "°" + ColorText.BLUE + " <> Min temp: " + minTemp + "°" + ColorText.CYAN + " | " + "Vento: " + df.format(vento) + "m/s" + ColorText.RESET);
            System.out.println("Stato generale: " + statoMeteo + " | Umidita': " + umidita + "%");
            System.out.println("---------------------------------------------");

            rigeneraPrevisioni();

            // Reimpostamento del giorno se e' terminata la settimana (cioe' siamo arrivati a domenica)
            giornoAttuale++;
            if(giornoAttuale == 8) {
                giornoAttuale = 1;
            }

            // Calcolo media settimanale
            mediaSettimanale += (minTemp+maxTemp)/2;

        }

        System.out.println(ColorText.CYAN + "Media temperatura settimanale: " + mediaSettimanale/7 + "°" + ColorText.RESET);

    }
}
