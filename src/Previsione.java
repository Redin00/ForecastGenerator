public abstract class Previsione {

    protected int minTemp;
    protected int maxTemp;
    protected int umidita;
    protected double vento;
    protected String statoMeteo;

    protected static final String[] giorniSettimana = {"Domenica", "Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato"};

    public Previsione(){
        minTemp = RandomWeatherGenerator.generatoreTemp();
        do{
            maxTemp = RandomWeatherGenerator.generatoreTemp();
        }while (maxTemp < minTemp);

        umidita = RandomWeatherGenerator.generatoreUmidita();
        vento = RandomWeatherGenerator.generatoreVento();
        statoMeteo = RandomWeatherGenerator.generatoreStato();
    }

    public abstract void rigeneraPrevisioni();

    public abstract void StampaPrevisioni() throws InterruptedException;
}