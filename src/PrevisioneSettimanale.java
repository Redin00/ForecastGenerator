import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

public class PrevisioneSettimanale extends Previsione {

    protected int mediaSettimanale;

    public PrevisioneSettimanale(){
        super();
    }

    @Override
    public void rigeneraPrevisioni() {
        minTemp = RandomWeatherGenerator.generatoreTemp();
        do{
            maxTemp = RandomWeatherGenerator.generatoreTemp();
        }while (maxTemp < minTemp);

        umidita = RandomWeatherGenerator.generatoreUmidita();
        vento = RandomWeatherGenerator.generatoreVento();
        statoMeteo = RandomWeatherGenerator.generatoreStato();
    }

    @Override
    public void StampaPrevisioni() throws InterruptedException {
        int giornoAttuale = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        String day;
        for(int i=0; i<7; i++){
            Thread.sleep(100);

            day = giorniSettimana[giornoAttuale-1];
            if(i == 0){
                day += " (Oggi)";
            }
            System.out.println(ColorText.PURPLE + day + ": " + ColorText.YELLOW + "Media temeperatura: " + (minTemp+maxTemp)/2 + "°");
            System.out.println(ColorText.RED + "Max temp: " + maxTemp + "°" + ColorText.BLUE + " <> Min temp: " + minTemp + "°" + ColorText.RESET);
            System.out.println("Stato generale: " + statoMeteo + " | Umidita': " + umidita + "%");
            System.out.println("---------------------------------------------");

            rigeneraPrevisioni();
            giornoAttuale++;
            if(giornoAttuale == 8) {
                giornoAttuale = 1;
            }

            mediaSettimanale += (minTemp+maxTemp)/2;

        }

        System.out.println(ColorText.CYAN + "Media temperatura settimanale: " + mediaSettimanale/7 + "°" + ColorText.RESET);

    }
}
