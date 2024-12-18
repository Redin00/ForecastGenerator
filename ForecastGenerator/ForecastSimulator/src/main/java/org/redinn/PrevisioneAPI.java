package org.redinn;

import com.google.gson.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.client.methods.HttpOptions;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.util.Calendar;

public class PrevisioneAPI{
    // Classe utilizzata per le previsioni fatte utilizzando il metodo api
    // API UTILIZZATO: OpenWeatherMap ==> Maggiori info: https://api.openweathermap.org/

    private static String nomeLocalitaTrovata; // Stringa usata per immagazzinare il nome della citta' trovata cercando con l'API

    // Stampa delle previsioni giornaliere (24 ore) prese dall'API
    public static void StampaPrevisioniGiornaliere(String nomeLocalita) throws UnirestException, ApiKeyException, InterruptedException {

        JsonObject previsioni = getPrevisioni(nomeLocalita);

        System.out.println("Ecco le previsioni per la localita' " + nomeLocalitaTrovata);

        int oraPrevisione = ZonedDateTime.now().getHour();  // Ora attuale presa dal sistema
        String oraPrevisioneString;     // Stringa per contenere la cifra dell'orario (necessaria per aggiungere lo "0" davanti ad un'orario se la cifra e' una sola)

        // Stampo di 24 ore
        // L'indice (i) e' usato anche per ottenere i dati dal json dell'API
        for(int i=0; i<24; i++){
            Thread.sleep(100);

            // Se l'ora e' costituita da una sola cifra, allora aggiungiamo uno zero davanti a quest'ultima
            if (oraPrevisione % 10 == oraPrevisione){
                oraPrevisioneString = "0" + String.valueOf(oraPrevisione);
            }
            else{
                oraPrevisioneString = String.valueOf(oraPrevisione);
            }

            System.out.println(ColorText.PURPLE + "Ore " + oraPrevisioneString + " | " + ColorText.YELLOW + "Temperatura stimata: " + previsioni.get("hourly").getAsJsonArray().get(i).getAsJsonObject().get("temp").toString() + "°");
            System.out.println(ColorText.CYAN + "Velocita' vento: " + previsioni.get("hourly").getAsJsonArray().get(i).getAsJsonObject().get("wind_speed").toString() + "m/s " + ColorText.BLUE + "<> " + "Umidita': " + previsioni.get("hourly").getAsJsonArray().get(i).getAsJsonObject().get("humidity").toString() + "%" + ColorText.RESET);
            System.out.println("Stato meteo - " + traduzioneStringa(previsioni.get("hourly").getAsJsonArray().get(i).getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").toString()));
            System.out.println("---------------------------------------------");

            oraPrevisione++;
            if(oraPrevisione == 24){
                oraPrevisione = 0;
            }

        }


    }

    // Stampa delle previsioni settimanali prese dall'API
    public static void StampaPrevisioniSettimanali(String nomeLocalita) throws ApiKeyException, UnirestException, InterruptedException {

        JsonObject previsioni = getPrevisioni(nomeLocalita);
        String[] giorniSettimana = {"Domenica", "Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato"};

        System.out.println("Ecco le previsioni per la localita' " + nomeLocalitaTrovata);

        // Stampa previsioni per 7 giorni
        int giornoAttuale = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        String day; // Variabile usata per contenere il nome del giorno della settimana

        for(int i=0; i<7; i++){
            Thread.sleep(100);

            day = giorniSettimana[giornoAttuale-1];
            if(i == 0){
                day += " (Oggi)";
            }

            System.out.println(ColorText.PURPLE + day + ": " + ColorText.YELLOW + "Media temeperatura: " + previsioni.get("daily").getAsJsonArray().get(i).getAsJsonObject().get("temp").getAsJsonObject().get("day").toString() + "°");
            System.out.println(ColorText.RED + "Max temp: " + previsioni.get("daily").getAsJsonArray().get(i).getAsJsonObject().get("temp").getAsJsonObject().get("max").toString() + "°" + ColorText.BLUE + " <> Min temp: " + previsioni.get("daily").getAsJsonArray().get(0).getAsJsonObject().get("temp").getAsJsonObject().get("min").toString() + "°" + ColorText.CYAN + " | " + "Vento: " + previsioni.get("daily").getAsJsonArray().get(i).getAsJsonObject().get("wind_speed").toString() + "m/s" + ColorText.RESET);
            System.out.println("Stato generale: " + traduzioneStringa(previsioni.get("daily").getAsJsonArray().get(i).getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").toString()) + " | Umidita': " + previsioni.get("daily").getAsJsonArray().get(i).getAsJsonObject().get("humidity").toString() + "%");
            System.out.println("---------------------------------------------");

            // Reimpostamento del giorno se e' terminata la settimana (cioe' siamo arrivati a domenica)
            giornoAttuale++;
            if(giornoAttuale == 8) {
                giornoAttuale = 1;
            }

        }

    }

    // Metodo usato per ottenere le previsioni dall'API di OpenWeather
    private static JsonObject getPrevisioni(String nomeLocalita) throws UnirestException, ApiKeyException {

        // Per ottenere la citta' dall'API, dobbiamo prima estrarre le coordinate dalla ricerca, e poi ricontattare l'api con le coordinate memorizzate
        // Questo e' quindi un processo abbastanza complicato

        HttpResponse<JsonNode> response = Unirest.get("https://api.openweathermap.org/data/2.5/find?q=" + nomeLocalita + "&appid=5796abbde9106b7da4febfae8c44c232&units=metric").asJson();

        if(response.getStatus() == 401){
            throw new ApiKeyException("Errore. API Key probabilmente errata/non valida.");
        }

        // Conversione della risposta http ricevuta in Json per ottenere poi tutte le informazioni necessarie
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response.getBody().toString());
        JsonObject jsonObject = je.getAsJsonObject();

        String lon, lat;
        lat = jsonObject.get("list").getAsJsonArray().get(0).getAsJsonObject().get("coord").getAsJsonObject().get("lat").toString();
        lon = jsonObject.get("list").getAsJsonArray().get(0).getAsJsonObject().get("coord").getAsJsonObject().get("lon").toString();

        nomeLocalitaTrovata = jsonObject.get("list").getAsJsonArray().get(0).getAsJsonObject().get("name").toString();

        // Variabile contenente le previsioni ottenute passando la latitudine e longitudine (coordinate)
        HttpResponse<JsonNode> finalResponse = Unirest.get("https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&units=metric&appid=5796abbde9106b7da4febfae8c44c232").asJson();

        if(finalResponse.getStatus() == 401){
            throw new ApiKeyException("Errore. API Key probabilmente errata/non valida.");
        }

        je = jp.parse(finalResponse.getBody().toString());

        return je.getAsJsonObject();
    }

    // Metodo utilizzato per determinare se esiste la localita cercata
    public static boolean localitaEsistente(String nomeLocalita) throws ApiKeyException, UnirestException{

        HttpResponse<JsonNode> response = Unirest.get("https://api.openweathermap.org/data/2.5/find?q=" + nomeLocalita + "&appid=5796abbde9106b7da4febfae8c44c232&units=metric").asJson();

        if(response.getStatus() == 401){
            throw new ApiKeyException("Errore. API Key probabilmente errata/non valida.");
        }

        // Conversione della risposta http ricevuta in Json per ottenere poi tutte le informazioni necessarie
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response.getBody().toString());
        JsonObject jsonObject = je.getAsJsonObject();

        // Count corrisponde nel json alla quantita' di localita' trovate con la stringa cercata inserite da input
        if(jsonObject.get("count").toString().equalsIgnoreCase("0")){
            return false;
        }

        return true;
    }

    // Metodo usato poiché il testo restituito del meteo e' in lingua inglese
    private static String traduzioneStringa(String inputString) throws UnirestException{

        // Sostituiamo gli spazi con "%20", che indica lo spazio nella query, e rimuoviamo i caratteri (")
        inputString = inputString.replace(" ", "%20").replace("\"", "");

        HttpResponse<JsonNode> response = Unirest.get("https://translate.googleapis.com/translate_a/single?client=gtx&sl=en&tl=it&dt=t&q=" + inputString).asJson();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonArray = new JsonParser().parse(response.getBody().toString()).getAsJsonArray();

        return jsonArray.get(0).getAsJsonArray().get(0).getAsJsonArray().get(0).toString().replace("\"", "");
    }
}
