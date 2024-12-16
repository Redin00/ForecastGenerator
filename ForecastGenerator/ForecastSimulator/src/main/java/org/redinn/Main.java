package org.redinn;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        // Programma simulatore meteo, il quale utilizza la generazione casuale per generare le diverse previsioni
        // Per fare cio vengono utilizzate due classi, PrevisioneGiornaliera, che restituisce la previsione per ogni ora del giorno
        // e la classe PrevisioneSettimanale, la quale restituisce le previsioni per tutta la settimana
        // Entrambe queste classi sono sottoclassi della classe abstract PrevisioneGenerata. Per generare le previsioni si fa uso della classe
        // GeneratorePrevisioni, in modo da semplificare il processo. Per immagazzinare i dati della localita' della quale vogliamo ottenere
        // il meteo, si usa la classe Localita. In seguito aggiunta la versione che fa uso dell'API gratuito OpenWeatherMap per ottenere previsioni non casuali, ma bensi reali

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
        int scelta = 0;  // Variabile usata per scelte di menu nel programma
        boolean mainLoop = true;    // Variabile usata per effettuare il loop principale del programma, che permette il cambio di modalita' del programma
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

        // Eliminiamo la "\n" che la funzione nextInt() dalla classe Scanner rilascia, andando a far saltare l'input della funzione nextLine()
        input.nextLine();



        // Switch utilizzato solo per stampare semplici scritte a seconda della scelta eseguita
        switch (scelta) {
            case 1:
                System.out.println(ColorText.CYAN + "Scelta programma: generatore casuale..." + ColorText.RESET);
                Thread.sleep(500);
                break;
            case 2:
                System.out.println(ColorText.CYAN + "Scelta programma: previsioni reali ottenute con API...." + ColorText.RESET);
                break;
        }

        // Loop per l'esecuzione continua del programma, permettendo il cambio di modalita' di programma

        // ------------------------------ LOOP PROGRAMMA CONTINUO, INTERROTO SOLAMENTE SE LA SCELTA = 0 ------------------------------
        while(mainLoop) {
            // ######## Se abbiamo scelto la generazione casuale delle previsioni #######
            if (scelta == 1) {

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

                // Eliminiamo la "\n" che la funzione nextDouble() dalla classe Scanner rilascia, andando a far saltare l'input della funzione nextLine()
                input.nextLine();

                Localita localitaMeteo = new Localita(tempLoc, tempCoord);

                System.out.println(ColorText.YELLOW + "Caricamento programma in corso...." + ColorText.RESET + "\n");
                Thread.sleep(200);

                // Loop con menu selezione per opzioni che offrono interattivita' al programma
                loop = true;
                while (loop) {

                    scelta = 0;
                    System.out.println(ColorText.PURPLE + "Scegli quale previsione visualizzare (PER USCIRE DAL PROGRAMMA INSERIRE 0): " + ColorText.RESET);
                    System.out.println(ColorText.BLUE + "1 -- Previsione giornaliera (Previsione di 24 ore)" + ColorText.RESET);
                    System.out.println(ColorText.BLUE + "2 -- Previsione settimanale (Dal giorno attuale fino al prossimo precedente ad oggi)" + ColorText.RESET);
                    System.out.println(ColorText.BLUE + "3 -- Cambio localita' | Coordinate e nome" + ColorText.RESET);
                    System.out.println(ColorText.BLUE + "4 -- Cambio modalita' programma (==> Previsioni reali)" + ColorText.RESET);

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

                        if (!inputLoop && scelta < 0 || scelta > 4) {
                            System.out.println(ColorText.RED + "Errore. Inserisci un numero compreso nelle scelte!" + ColorText.RESET);
                            inputLoop = true;

                        }

                    }

                    // Eliminiamo la "\n" che la funzione nextInt() dalla classe Scanner rilascia, andando a far saltare l'input della funzione nextLine()
                    input.nextLine();

                    switch (scelta) {
                        case 0:
                            System.out.println(ColorText.YELLOW + "Uscita dal programma in corso...." + ColorText.RESET);
                            loop = false;
                            mainLoop = false;
                            break;
                        case 1:
                            System.out.println(ColorText.CYAN + "Ecco la previsione giornaliera per la localita' " + localitaMeteo.getNome() + ColorText.CYAN + " | Coordinate = " + ColorText.BLUE + localitaMeteo.getCoordinate() + ColorText.RESET);
                            System.out.println(ColorText.CYAN + "===============================================" + ColorText.RESET);
                            PrevisioneGiornaliera previsioneGiornaliera = new PrevisioneGiornaliera();
                            previsioneGiornaliera.StampaPrevisioni();


                            System.out.println(ColorText.CYAN + "Premere invio per continuare...." + ColorText.RESET);
                            input.nextLine();
                            break;

                        case 2:
                            System.out.println(ColorText.CYAN + "Ecco la previsione settimanale per la localita' " + ColorText.BLUE + localitaMeteo.getNome() + ColorText.CYAN + " | Coordinate = " + localitaMeteo.getCoordinate() + ColorText.RESET);
                            System.out.println(ColorText.CYAN + "===============================================" + ColorText.RESET);
                            PrevisioneSettimanale previsioneSettimanale = new PrevisioneSettimanale();
                            previsioneSettimanale.StampaPrevisioni();


                            System.out.println(ColorText.CYAN + "Premere invio per continuare...." + ColorText.RESET);
                            input.nextLine();
                            break;
                        case 3:

                            System.out.println(ColorText.PURPLE + "-------- Cambio Localita' --------" + ColorText.RESET);
                            System.out.print(ColorText.BLUE + "Inserisci nome nuova localita': " + ColorText.RESET);

                            tempLoc = input.nextLine();

                            System.out.println(ColorText.BLUE + "Inserisci adesso le coordinate':" + ColorText.RESET);

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

                            // Eliminiamo la "\n" che la funzione nextInt() e nextDouble() dalla classe Scanner rilasciano, andando a far saltare l'input della funzione nextLine()
                            input.nextLine();

                            localitaMeteo.setNome(tempLoc);
                            localitaMeteo.setCoordinate(tempCoord);

                            break;
                        case 4:

                            String temp = "";

                            do {
                                System.out.print(ColorText.GREEN + "Sei sicuro di voler cambiare la modalita' programma in previsioni reali? (S/N): " + ColorText.RESET);
                                temp = input.nextLine();

                            } while (!temp.equalsIgnoreCase("S") && !temp.equalsIgnoreCase("N"));

                            if (temp.equalsIgnoreCase("S")) {
                                System.out.println(ColorText.YELLOW + "Cambio modalita' in previsioni reali..." + ColorText.RESET);
                                Thread.sleep(500);

                                // Annulliamo il loop della modalita' attuale del programma e cambiamo scelta così da poter accedere all'altra modalita'
                                loop = false;
                                scelta = 2;

                                System.out.println(ColorText.YELLOW + "=============== Cambio modalita' eseguito! (Previsioni reali) ===============" + ColorText.RESET);

                            }
                            else {
                                System.out.println(ColorText.RED + "Cambio modalita' annullato..." + ColorText.RESET);
                                Thread.sleep(500);
                            }

                            break;

                    }
                }
            }
            // Se abbiamo scelto le previsioni reali con uso di API
            else if (scelta == 2) {

                inputLoop = true;
                String nomeLocalita = "";
                // Controllo attraverso il metodo della classe PrevisioneAPI se la localita' esiste

                inputLoop = true;
                while (inputLoop) {
                    System.out.print("Inserisci la localita' di cui vuoi sapere le previsioni meteo -> ");
                    nomeLocalita = input.nextLine();


                    // Cambiamo gli spazi con "(%20) che serve per indicare gli spazi nella query
                    nomeLocalita = nomeLocalita.replace(" ", "%20");

                    if (nomeLocalita.length() < 3) {
                        System.out.println(ColorText.RED + "Errore! Inserisci almeno 3 caratteri per trovare una citta' valida!" + ColorText.RESET);
                    } else if (!PrevisioneAPI.localitaEsistente(nomeLocalita)) {
                        System.out.println(ColorText.RED + "Errore! Localita' inserita non esistente! Inserirne una nuova!" + ColorText.RESET);
                    } else {
                        inputLoop = false;
                    }
                }

                System.out.println(ColorText.YELLOW + "Caricamento programma in corso...." + ColorText.RESET + "\n");
                Thread.sleep(200);

                // Loop previsioni reali
                loop = true;
                while (loop) {
                    // Input scelta previsione

                    scelta = 0;
                    System.out.println(ColorText.PURPLE + "Scegli quale previsione visualizzare (PER USCIRE DAL PROGRAMMA INSERIRE 0): " + ColorText.RESET);
                    System.out.println(ColorText.BLUE + "1 -- Previsione giornaliera (Previsione di 24 ore)" + ColorText.RESET);
                    System.out.println(ColorText.BLUE + "2 -- Previsione settimanale (Dal giorno attuale fino al prossimo precedente ad oggi)" + ColorText.RESET);
                    System.out.println(ColorText.BLUE + "3 -- Cambio localita' | Coordinate e nome" + ColorText.RESET);
                    System.out.println(ColorText.BLUE + "4 -- Cambio modalita' programma (==> Generazione casuale previsioni)" + ColorText.RESET);

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

                        if (!inputLoop && scelta < 0 || scelta > 4) {
                            System.out.println(ColorText.RED + "Errore. Inserisci un numero compreso nelle scelte!" + ColorText.RESET);
                            inputLoop = true;

                        }
                    }

                    // Eliminiamo la "\n" che la funzione nextInt() dalla classe Scanner rilascia, andando a far saltare l'input della funzione nextLine()
                    input.nextLine();

                    switch (scelta) {
                        case 0:
                            System.out.println(ColorText.YELLOW + "Uscita dal programma in corso...." + ColorText.RESET);
                            loop = false;
                            mainLoop = false;
                            break;
                        case 1:
                            PrevisioneAPI.StampaPrevisioniGiornaliere(nomeLocalita);

                            System.out.println(ColorText.CYAN + "Premere invio per continuare...." + ColorText.RESET);
                            input.nextLine();
                            break;
                        case 2:
                            PrevisioneAPI.StampaPrevisioniSettimanali(nomeLocalita);

                            System.out.println(ColorText.CYAN + "Premere invio per continuare...." + ColorText.RESET);
                            input.nextLine();
                            break;
                        case 3:

                            System.out.println(ColorText.PURPLE + "-------- Cambio Localita' --------" + ColorText.RESET);

                            inputLoop = true;
                            while (inputLoop) {
                                System.out.print("Inserisci la nuova localita' -> ");
                                nomeLocalita = input.nextLine();

                                // Cambiamo gli spazi con "(%20) che serve per indicare gli spazi nell'url
                                nomeLocalita = nomeLocalita.replace(" ", "%20");

                                if (nomeLocalita.length() < 3) {
                                    System.out.println(ColorText.RED + "Errore! Inserisci almeno 3 caratteri per trovare una citta' valida!" + ColorText.RESET);
                                } else if (!PrevisioneAPI.localitaEsistente(nomeLocalita)) {
                                    System.out.println(ColorText.RED + "Errore! Localita' inserita non esistente! Inserirne una nuova!" + ColorText.RESET);
                                } else {
                                    inputLoop = false;
                                }
                            }

                            break;
                        case 4:

                            String temp = "";

                            do {
                                System.out.print(ColorText.GREEN + "Sei sicuro di voler cambiare la modalita' programma in previsioni generate casualmente? (S/N): " + ColorText.RESET);
                                temp = input.nextLine();

                            } while (!temp.equalsIgnoreCase("S") && !temp.equalsIgnoreCase("N"));

                            if (temp.equalsIgnoreCase("S")) {
                                System.out.println(ColorText.YELLOW + "Cambio modalita' in generazione casuale previsioni..." + ColorText.RESET);
                                Thread.sleep(500);

                                // Annulliamo il loop della modalita' attuale del programma e cambiamo scelta così da poter accedere all'altra modalita'
                                loop = false;
                                scelta = 1;

                                System.out.println(ColorText.YELLOW + "=============== Cambio modalita' eseguito! (Previsioni generate casualmente) ===============" + ColorText.RESET);

                            }
                            else {
                                System.out.println(ColorText.RED + "Cambio modalita' annullato..." + ColorText.RESET);
                                Thread.sleep(500);
                            }

                            break;
                    }

                }
            }
        }

        input.close();

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
