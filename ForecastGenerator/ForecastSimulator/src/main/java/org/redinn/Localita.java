package org.redinn;

public class Localita {

    // Classe utilizzata per la creazione di una localita' con nome e coordinate a scelta dell'utente
    // Utilizzata per la generazione di previsioni casuali

    private String nome;

    // Array per le coordinate (le quali saranno rispettivamente latitudine e longitudine)
    private double[] coordinate;

    public Localita(String nome, double[] coordinate) {
        this.nome = nome;
        this.coordinate = coordinate;
    }

    // Metodi getter

    public String getNome(){
        return nome;
    }

    public String getCoordinate(){
        return ColorText.BLUE + coordinate[0] + ", " + coordinate[1];
    }

    // Metodi setter

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCoordinate(double[] coordinate){
        this.coordinate = coordinate;
    }


}
