import java.awt.*;
import java.io.IOException;
import java.sql.SQLSyntaxErrorException;
import java.time.temporal.Temporal;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        // Programma simulatore meteo, il quale utilizza la generazione random per generare le diverse previsioni
        // Per fare ciò vengono utilizzate due classi, PrevisioneGiornaliera, che restituisce la previsione per ogni ora del giorno
        // e la classe PrevisioneSettimanale, la quale restituisce le previsioni per tutta la settimana compreso il giorno attuale
        // Entrambe queste classi sono sottoclassi della classe abstract Previsione. Per generare le previsioni si fa uso della classe
        // RandomWeatherGenerator, per semplificare il processo. Per immagazzinare i dati della localita' della quale vogliamo ottenere
        // il meteo

        String titleText = ColorText.GREEN + "  __  __      _                       ____  _                 _       _             \n" +
                " |  \\/  | ___| |_ ___  ___           / ___|(_)_ __ ___  _   _| | __ _| |_ ___  _ __ \n" +
                " | |\\/| |/ _ \\ __/ _ \\/ _ \\   _____  \\___ \\| | '_ ` _ \\| | | | |/ _` | __/ _ \\| '__|\n" +
                " | |  | |  __/ ||  __/ (_) | |_____|  ___) | | | | | | | |_| | | (_| | || (_) | |   \n" +
                " |_|  |_|\\___|\\__\\___|\\___/          |____/|_|_| |_| |_|\\__,_|_|\\__,_|\\__\\___/|_|   \n" +
                "                                                                                    " + ColorText.RESET;

        char[] titleArray = titleText.toCharArray();
        for(char c : titleArray){
            Thread.sleep(1);
            System.out.print(c);
        }
        System.out.println();

        Scanner input = new Scanner(System.in);
        double[] tempCoord = new double[2];

        System.out.print("Inserisci la localita' di cui vuoi sapere le previsioni meteo -> ");

        String tempLoc = input.nextLine();
        System.out.println("Inserisci adesso le coordinate della localita': ");

        boolean loop = true;

        while(loop) {
            try
            {
                System.out.print(ColorText.GREEN + "Prima coordinata -> " + ColorText.RESET);
                tempCoord[0] = input.nextDouble();
                System.out.print(ColorText.GREEN + "Seconda coordinata -> " + ColorText.RESET);
                tempCoord[1] = input.nextDouble();
                loop = false;
            }
            catch (InputMismatchException ex) {
                System.out.println(ColorText.RED + "Errore! Inserisci coordinate (num. double, es: 3,20)" + ColorText.RESET);
                input.nextLine();
            }
        }


        Localita localitaMeteo = new Localita(tempLoc, tempCoord);

        System.out.println(ColorText.YELLOW + "Generazione previsioni in corso...." + ColorText.RESET + "\n");
        Thread.sleep(500);


        loop = true;
        while (loop){

            int choose;
            System.out.println(ColorText.PURPLE + "Scegli quale previsione visualizzare (PER USCIRE DAL PROGRAMMA INSERIRE 0): " + ColorText.RESET);
            System.out.println(ColorText.BLUE + "1 -- Previsione giornaliera (Previsione di 24 ore)" + ColorText.RESET);
            System.out.println(ColorText.BLUE + "2 -- Previsione settimanale (Dal giorno attuale fino al prossimo precedente ad oggi)" + ColorText.RESET);
            System.out.println(ColorText.BLUE + "3 -- Cambio localita' | Coordinate e nome" + ColorText.RESET);

            choose = input.nextInt();

            switch (choose) {
                case 0:
                    System.out.println(ColorText.YELLOW + "Uscita dal programma in corso...." + ColorText.RESET);
                    loop = false;
                    break;
                case 1:
                    System.out.println(ColorText.CYAN + "Ecco la previsione giornaliera per la localita' " + localitaMeteo.getNome() + ColorText.CYAN + " | Coordinate = " + ColorText.BLUE + localitaMeteo.getCoordinate() + ColorText.RESET);
                    System.out.println(ColorText.CYAN + "--------------------------------------" + ColorText.RESET);
                    PrevisioneGiornaliera previsioneGiornaliera = new PrevisioneGiornaliera();
                    previsioneGiornaliera.StampaPrevisioni();


                    System.out.println(ColorText.CYAN + "Premere invio per continuare...." + ColorText.RESET);
                    new Scanner(System.in).nextLine();
                    break;

                case 2:
                    System.out.println(ColorText.CYAN + "Ecco la previsione settimanale per la localita' " + ColorText.BLUE + localitaMeteo.getNome() + ColorText.CYAN + " | Coordinate = " + localitaMeteo.getCoordinate() + ColorText.RESET);
                    System.out.println(ColorText.CYAN + "--------------------------------------" + ColorText.RESET);
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

                    while(controlLoop) {
                        try
                        {
                            System.out.print(ColorText.GREEN + "Prima coordinata -> " + ColorText.RESET);
                            tempCoord[0] = input.nextDouble();
                            System.out.print(ColorText.GREEN + "Seconda coordinata -> " + ColorText.RESET);
                            tempCoord[1] = input.nextDouble();
                            controlLoop = false;
                        }
                        catch (InputMismatchException ex) {
                            System.out.println(ColorText.RED + "Errore! Inserisci coordinate (num. double, es: 3,20)" + ColorText.RESET);
                            input.nextLine();
                        }
                    }

                    localitaMeteo.setNome(tempLoc);
                    localitaMeteo.setCoordinate(tempCoord);

                    break;
                default:
                    System.out.println(ColorText.RED + "Errore! Inserisci un valore compreso nelle scelte!!" + ColorText.RESET);
                    break;
            }
        }


    }
}