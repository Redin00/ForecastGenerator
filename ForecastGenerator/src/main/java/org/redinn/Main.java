package org.redinn;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        // Programma simulatore meteo, il quale utilizza la generazione casuale per generare le diverse previsioni
        // Per fare cio vengono utilizzate due classi, PrevisioneGiornaliera, che restituisce la previsione per ogni ora del giorno
        // e la classe PrevisioneSettimanale, la quale restituisce le previsioni per tutta la settimana compreso il giorno attuale
        // Entrambe queste classi sono sottoclassi della classe abstract Previsione. Per generare le previsioni si fa uso della classe
        // RandomWeatherGenerator, per semplificare il processo. Per immagazzinare i dati della localita' della quale vogliamo ottenere
        // il meteo. In seguito aggiunta la versione che fa uso dell'API gratuito OpenWeatherAPI per ottenere previsioni non casuali

        String titleText = ColorText.GREEN + "  __  __      _                       ____  _                 _       _             \n" +
                " |  \\/  | ___| |_ ___  ___           / ___|(_)_ __ ___  _   _| | __ _| |_ ___  _ __ \n" +
                " | |\\/| |/ _ \\ __/ _ \\/ _ \\   _____  \\___ \\| | '_ ` _ \\| | | | |/ _` | __/ _ \\| '__|\n" +
                " | |  | |  __/ ||  __/ (_) | |_____|  ___) | | | | | | | |_| | | (_| | || (_) | |   \n" +
                " |_|  |_|\\___|\\__\\___|\\___/          |____/|_|_| |_| |_|\\__,_|_|\\__,_|\\__\\___/|_|   \n" +
                "                                                                                    " + ColorText.RESET;


        // Scrittura del titolo in modo sequenziale (semi-animata)
        char[] titleArray = titleText.toCharArray();
        for(char c : titleArray){
            Thread.sleep(1);
            System.out.print(c);
        }
        System.out.println();


        Scanner input = new Scanner(System.in);
        int scelta = 0;
        boolean loop = true;        // Variabile usata per effettuare diversi loop nel codice
        boolean inputLoop = true;  // Variabile usata per effettuare loop necessari per l'input

        System.out.println(ColorText.GREEN + "Sceglie la versione del programma da utilizzare (0 per uscire dal programma)" + ColorText.RESET);
        System.out.println(ColorText.YELLOW + "1 - Versione con previsioni generate casuali" + ColorText.RESET);
        System.out.println(ColorText.YELLOW + "2 - Versione con previsioni reali, ottenute utilizzando l'API di OpenWeatherMap" + ColorText.RESET);

        // Ciclo per input scelta della modalita' del programma che si desidera eseguire
        while(inputLoop) {

            try {
                System.out.print(ColorText.GREEN + "Inserisci scelta -> " + ColorText.RESET);
                scelta = input.nextInt();
                inputLoop = false;
            } catch (InputMismatchException ex) {
                System.out.println(ColorText.RED + "Errore. Inserisci un numero compreso nelle scelte!" + ColorText.RESET);
                input.nextLine();
            }

            if(!inputLoop && scelta < 0 || scelta > 2) {
                System.out.println(ColorText.RED + "Errore. Inserisci un numero compreso nelle scelte!" + ColorText.RESET);
                inputLoop = true;

            }

        }


        // Switch utilizzato solo per stampare semplici scritte a seconda della scelta
        switch (scelta) {
            case 1:
                System.out.println(ColorText.CYAN + "Scelta programma: generatore casuale...");
                Thread.sleep(500);
                break;
            case 2:
                System.out.println(ColorText.CYAN + "Scelta programma: previsioni reali ottenute con API...." + ColorText.RESET);
                break;
        }


        // ######## Se abbiamo scelto la generazione casuale delle previsioni #######
        if(scelta == 1) {


            double[] tempCoord = new double[2];

            System.out.print("Inserisci la localita' di cui vuoi sapere le previsioni meteo -> ");

            String tempLoc = input.nextLine();
            System.out.println("Inserisci adesso le coordinate della localita': ");

            inputLoop = true;

            while (inputLoop) {
                try {
                    System.out.print(ColorText.GREEN + "Prima coordinata -> " + ColorText.RESET);
                    tempCoord[0] = input.nextDouble();
                    System.out.print(ColorText.GREEN + "Seconda coordinata -> " + ColorText.RESET);
                    tempCoord[1] = input.nextDouble();
                    inputLoop = false;
                } catch (InputMismatchException ex) {
                    System.out.println(ColorText.RED + "Errore! Inserisci coordinate (num. double, es: 3,20)" + ColorText.RESET);
                    input.nextLine();
                }
            }


            Localita localitaMeteo = new Localita(tempLoc, tempCoord);

            System.out.println(ColorText.YELLOW + "Generazione previsioni in corso...." + ColorText.RESET + "\n");
            Thread.sleep(500);


            // Loop con menu selezione per opzioni che offrono interattivita' al programma
            loop = true;
            while (loop) {

                scelta = 0;
                System.out.println(ColorText.PURPLE + "Scegli quale previsione visualizzare (PER USCIRE DAL PROGRAMMA INSERIRE 0): " + ColorText.RESET);
                System.out.println(ColorText.BLUE + "1 -- Previsione giornaliera (Previsione di 24 ore)" + ColorText.RESET);
                System.out.println(ColorText.BLUE + "2 -- Previsione settimanale (Dal giorno attuale fino al prossimo precedente ad oggi)" + ColorText.RESET);
                System.out.println(ColorText.BLUE + "3 -- Cambio localita' | Coordinate e nome" + ColorText.RESET);

                inputLoop = true;
                // Controllo scelta per evitare errori
                while(inputLoop) {

                    try {
                        System.out.print(ColorText.GREEN + "Inserisci scelta -> " + ColorText.RESET);
                        scelta = input.nextInt();
                        inputLoop = false;
                    }
                    catch (InputMismatchException ex) {
                        System.out.println(ColorText.RED + "Errore. Inserisci un numero compreso nelle scelte!" + ColorText.RESET);
                        input.nextLine();
                    }

                    if(!inputLoop && scelta < 0 || scelta > 3) {
                        System.out.println(ColorText.RED + "Errore. Inserisci un numero compreso nelle scelte!" + ColorText.RESET);
                        inputLoop = true;

                    }

                }

                switch (scelta) {
                    case 0:
                        System.out.println(ColorText.YELLOW + "Uscita dal programma in corso...." + ColorText.RESET);
                        loop = false;
                        break;
                    case 1:
                        System.out.println(ColorText.CYAN + "Ecco la previsione giornaliera per la localita' " + localitaMeteo.getNome() + ColorText.CYAN + " | Coordinate = " + ColorText.BLUE + localitaMeteo.getCoordinate() + ColorText.RESET);
                        System.out.println(ColorText.CYAN + "===============================================" + ColorText.RESET);
                        PrevisioneGiornaliera previsioneGiornaliera = new PrevisioneGiornaliera();
                        previsioneGiornaliera.StampaPrevisioni();


                        System.out.println(ColorText.CYAN + "Premere invio per continuare...." + ColorText.RESET);
                        new Scanner(System.in).nextLine();
                        break;

                    case 2:
                        System.out.println(ColorText.CYAN + "Ecco la previsione settimanale per la localita' " + ColorText.BLUE + localitaMeteo.getNome() + ColorText.CYAN + " | Coordinate = " + localitaMeteo.getCoordinate() + ColorText.RESET);
                        System.out.println(ColorText.CYAN + "===============================================" + ColorText.RESET);
                        PrevisioneSettimanale previsioneSettimanale = new PrevisioneSettimanale();
                        previsioneSettimanale.StampaPrevisioni();


                        System.out.println(ColorText.CYAN + "Premere invio per continuare...." + ColorText.RESET);
                        new Scanner(System.in).nextLine();
                        break;
                    case 3:

                        input.nextLine();

                        System.out.println(ColorText.PURPLE + "-------- Cambio Localita' --------" + ColorText.RESET);
                        System.out.print(ColorText.BLUE + "Inserisci nome nuova localita': " + ColorText.RESET);

                        tempLoc = input.nextLine();
                        System.out.println(ColorText.BLUE + "Inserisci adesso le coordinate':" + ColorText.RESET);
                        boolean controlLoop = true;

                        while (controlLoop) {
                            try {
                                System.out.print(ColorText.GREEN + "Prima coordinata -> " + ColorText.RESET);
                                tempCoord[0] = input.nextDouble();
                                System.out.print(ColorText.GREEN + "Seconda coordinata -> " + ColorText.RESET);
                                tempCoord[1] = input.nextDouble();
                                controlLoop = false;
                            } catch (InputMismatchException ex) {
                                System.out.println(ColorText.RED + "Errore! Inserisci coordinate (num. double, es: 3,20)" + ColorText.RESET);
                                input.nextLine();
                            }
                        }

                        localitaMeteo.setNome(tempLoc);
                        localitaMeteo.setCoordinate(tempCoord);

                        break;
                }
            }
        }
        // Se abbiamo scelto le previsioni reali con uso di API
        else if(scelta == 2){

            inputLoop = true;
            String nomeLocalita;
            // Controllo attraverso il metodo della classe PrevisioneAPI se la localita' esiste
            do {
                System.out.print("Inserisci la localita' di cui vuoi sapere le previsioni meteo -> ");
                input.nextLine();
                nomeLocalita = input.nextLine();
            }while (!PrevisioneAPI.localitaEsistente(nomeLocalita));


            // Loop programma
            loop = true;
            while(loop) {
                // Input scelta previsione

                scelta = 0;
                System.out.println(ColorText.PURPLE + "Scegli quale previsione visualizzare (PER USCIRE DAL PROGRAMMA INSERIRE 0): " + ColorText.RESET);
                System.out.println(ColorText.BLUE + "1 -- Previsione giornaliera (Previsione di 24 ore)" + ColorText.RESET);
                System.out.println(ColorText.BLUE + "2 -- Previsione settimanale (Dal giorno attuale fino al prossimo precedente ad oggi)" + ColorText.RESET);
                System.out.println(ColorText.BLUE + "3 -- Cambio localita' | Coordinate e nome" + ColorText.RESET);

                inputLoop = true;
                // Controllo scelta per evitare errori
                while (inputLoop) {
                    try {
                        System.out.print(ColorText.GREEN + "Inserisci scelta -> " + ColorText.RESET);
                        scelta = input.nextInt();
                        inputLoop = false;
                    } catch (InputMismatchException ex) {
                        System.out.println(ColorText.RED + "Errore. Inserisci un numero compreso nelle scelte!" + ColorText.RESET);
                        input.nextLine();
                    }

                    if (!inputLoop && scelta < 0 || scelta > 3) {
                        System.out.println(ColorText.RED + "Errore. Inserisci un numero compreso nelle scelte!" + ColorText.RESET);
                        inputLoop = true;

                    }
                }

                switch (scelta) {
                    case 0:
                        System.out.println(ColorText.YELLOW + "Uscita dal programma in corso...." + ColorText.RESET);
                        loop = false;
                        break;
                    case 1:
                        PrevisioneAPI.StampaPrevisioniGiornaliere(nomeLocalita);

                        System.out.println(ColorText.CYAN + "Premere invio per continuare...." + ColorText.RESET);
                        new Scanner(System.in).nextLine();
                        break;
                    case 2:
                        PrevisioneAPI.StampaPrevisioniSettimanali(nomeLocalita);

                        System.out.println(ColorText.CYAN + "Premere invio per continuare...." + ColorText.RESET);
                        new Scanner(System.in).nextLine();
                        break;
                }

            }
        }



        System.out.println(ColorText.YELLOW + "   ____               _                                               \n" +
                "  / ___|_ __ __ _ ___(_) ___   _ __   ___ _ __                        \n" +
                " | |  _| '__/ _` |_  / |/ _ \\ | '_ \\ / _ \\ '__|                       \n" +
                " | |_| | | | (_| |/ /| |  __/ | |_) |  __/ |                          \n" +
                "  \\____|_|  \\__,_/___|_|\\___| | .__/ \\___|_|                          \n" +
                "   __ ___   _____ _ __   _   _|_|_  __ _| |_ ___                      \n" +
                "  / _` \\ \\ / / _ \\ '__| | | | / __|/ _` | __/ _ \\                     \n" +
                " | (_| |\\ V /  __/ |    | |_| \\__ \\ (_| | || (_) |                    \n" +
                "  \\__,_| \\_/ \\___|_|     \\__,_|___/\\__,_|\\__\\___/                   _ \n" +
                " (_) |  _ __  _ __ ___   __ _ _ __ __ _ _ __ ___  _ __ ___   __ _  | |\n" +
                " | | | | '_ \\| '__/ _ \\ / _` | '__/ _` | '_ ` _ \\| '_ ` _ \\ / _` | | |\n" +
                " | | | | |_) | | | (_) | (_| | | | (_| | | | | | | | | | | | (_| | |_|\n" +
                " |_|_| | .__/|_|  \\___/ \\__, |_|  \\__,_|_| |_| |_|_| |_| |_|\\__,_| (_)\n" +
                "       |_|              |___/                                         " + ColorText.RESET);


    }
}