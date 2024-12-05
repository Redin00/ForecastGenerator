public class Localita {
    private String nome;
    private double[] coordinate;

    public Localita(String nome, double[] coordinate) {
        this.nome = nome;
        this.coordinate = coordinate;
    }

    // Getter per ottenere gli attributi

    public String getNome(){
        return nome;
    }

    public String getCoordinate(){
        return ColorText.BLUE + coordinate[0] + ", " + coordinate[1];
    }


    // Setter per cambiare se si desidera il nome e coordinate localita'

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setCoordinate(double[] coordinate){
        this.coordinate = coordinate;
    }

}
