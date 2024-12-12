package org.redinn;

public abstract class PrevisioneGenerata {

    // Classe astratta che definisce la struttura base di una previsione, quindi massima e minima temperatura, umidita, velocita' vento, e lo stato (es: soleggiato)

    protected int minTemp;
    protected int maxTemp;
    protected int umidita;
    protected double vento;
    protected String statoMeteo;

    protected static final String[] giorniSettimana = {"Domenica", "Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato"};

    public PrevisioneGenerata(){
        minTemp = GeneratorePrevisioni.generatoreTemp();
        do{
            maxTemp = GeneratorePrevisioni.generatoreTemp();
        }while (maxTemp < minTemp || maxTemp-25 > minTemp);     // Impostiamo un limite di 25 gradi massimi per l'escursione termica

        umidita = GeneratorePrevisioni.generatoreUmidita();
        vento = GeneratorePrevisioni.generatoreVento();
        statoMeteo = GeneratorePrevisioni.generatoreStato((minTemp+maxTemp)/2);
    }

    public abstract void rigeneraPrevisioni();

    public abstract void StampaPrevisioni() throws InterruptedException;
}