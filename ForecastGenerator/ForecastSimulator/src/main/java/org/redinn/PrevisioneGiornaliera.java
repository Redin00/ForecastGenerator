package org.redinn;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.ZonedDateTime;

public class PrevisioneGiornaliera extends PrevisioneGenerata {

    // Classe che si occupa di generare

    public PrevisioneGiornaliera(){
        super();
    }

    @Override
    public void rigeneraPrevisioni() {
        minTemp = GeneratorePrevisioni.generatoreTemp();
        do{
            maxTemp = GeneratorePrevisioni.generatoreTemp();
        }while (maxTemp < minTemp || maxTemp-25 > minTemp); // Impostiamo un limite di 25 gradi massimi per l'escursione termica

        // A differenza delle previsioni settimanali, in questo metodo rigeneriamo solo la temperatura poiche' e' l'unico attributo generico in tutta la giornata
    }

    @Override
    public void StampaPrevisioni() throws InterruptedException {

        // Usiamo questo metodo quando dobbiamo generare delle nuove previsioni giornaliere
        rigeneraPrevisioni();

        int oraPrevisione = ZonedDateTime.now().getHour();  // Ora attuale presa dal sistema
        String oraPrevisioneString;     // Stringa per contenere la cifra dell'orario (necessaria per aggiungere lo "0" davanti ad un'orario se la cifra e' una sola
        int temp;   // Variabile usata per la generazione della temperatura di ogni ora
        int mediaGiornaliera = 0;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        for(int i=0; i<24; i++){
            Thread.sleep(100);

            // Se l'ora e' costituita da una sola cifra, allora aggiungiamo uno zero davanti a quest'ultima


            if (oraPrevisione % 10 == oraPrevisione){
                oraPrevisioneString = "0" + String.valueOf(oraPrevisione);
            }
            else{
                oraPrevisioneString = String.valueOf(oraPrevisione);
            }

            // Generazione temperatura per ogni ora, tenendo conto della massima e della minima giornaliera totale
            do{
                temp = GeneratorePrevisioni.generatoreTemp();
            }while (temp > maxTemp || temp < minTemp);

            mediaGiornaliera += temp;

            System.out.println(ColorText.PURPLE + "Ore " + oraPrevisioneString + " | " + ColorText.YELLOW + "Temperatura stimata: " +  temp + "°");
            System.out.println(ColorText.CYAN + "Velocita' vento: " + df.format(vento) + "m/s " + ColorText.BLUE + "<> " + "Umidita': " + umidita + "%" + ColorText.RESET);
            System.out.println("Stato meteo - " + statoMeteo);
            System.out.println("---------------------------------------------");

            oraPrevisione++;
            if(oraPrevisione == 24){
                oraPrevisione = 0;
            }

            umidita = GeneratorePrevisioni.generatoreUmidita();
            vento = GeneratorePrevisioni.generatoreVento();
            statoMeteo = GeneratorePrevisioni.generatoreStato((minTemp+maxTemp)/2);

        }

        System.out.println(ColorText.CYAN + "Media temperatura giornaliera: " + mediaGiornaliera/24 + "°" + ColorText.RESET);
    }
}