import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.ZonedDateTime;

public class PrevisioneGiornaliera extends Previsione{

    public PrevisioneGiornaliera(){
        super();
    }

    @Override
    public void rigeneraPrevisioni() {
        minTemp = RandomWeatherGenerator.generatoreTemp();
        do{
            maxTemp = RandomWeatherGenerator.generatoreTemp();
        }while (maxTemp < minTemp);

        // A differenza delle previsioni settimanali, in questo metodo rigeneriamo solo la temperatura poiche' e' l'unico attributo generico in tutta la giornata
    }

    @Override
    public void StampaPrevisioni() throws InterruptedException {

        rigeneraPrevisioni();

        String giornoOra = " (Oggi)"; // Con questa stringa decidiamo quando inserire la dicitura "oggi" o "domani"

        int oraPrevisione = ZonedDateTime.now().getHour();
        String oraPrevisioneString;
        int temp;
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
                temp = RandomWeatherGenerator.generatoreTemp();
            }while (temp > maxTemp || temp < minTemp);


            System.out.println(ColorText.PURPLE + "Ore " + oraPrevisioneString + giornoOra + " | " + ColorText.YELLOW + "Temperatura attuale: " +  temp + "°");
            System.out.println(ColorText.CYAN + "Velocita' vento: " + df.format(vento) + "m/s " + ColorText.BLUE + "<> " + "Umidita': " + umidita + "%" + ColorText.RESET);
            System.out.println("Stato meteo - " + statoMeteo);
            System.out.println("---------------------------------------------");

            oraPrevisione++;
            if(oraPrevisione == 24){
                oraPrevisione = 0;
                giornoOra = " (Domani)";
            }

            umidita = RandomWeatherGenerator.generatoreUmidita();
            vento = RandomWeatherGenerator.generatoreVento();
            statoMeteo = RandomWeatherGenerator.generatoreStato();

        }
    }
}
