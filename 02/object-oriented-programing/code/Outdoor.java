/**
 *Classe abstract é a classe para actividades do tipo Outdoor
 *
 */
import java.io.Serializable;

public abstract class Outdoor extends Actividade implements Kilometragem, Serializable{
    private double distancia;
    private double altMax, altMin;
    private String condMet;
    /**
     * Construtor vazio
     */
    public Outdoor(){
        super();
        this.distancia = 0;
        this.altMax = 0;
        this.altMin = 0;
        this.condMet = "";
    }

    /**
     * Construtor parametrizado
     */
    public Outdoor(int id, Data dat, double dur, double d, double aMax, 
    double aMin, String cM){
        super(id,dat,dur);
        this.distancia = d;
        this.altMax = aMax;
        this.altMin = aMin;
        this.condMet = cM;
    }

    /**
     * Construtor por cápia
     */
    public Outdoor(Outdoor o){
        super(o);
        this.distancia = o.getDist();
        this.altMax = o.getAltMax();
        this.altMin = o.getAltMin();
        this.condMet = o.getCondMet();
    }

    public double getDist() {
        return this.distancia;
    }

    public double getAltMax() {
        return this.altMax;
    }

    public double getAltMin() {
        return this.altMin;
    }

    public String getCondMet() {
        return this.condMet;
    }

    public void setDistancia(double dis) {this.distancia = dis;}

    public void setAltMax(double aMax) {this.altMax = aMax;}

    public void setAltMin(double aMin) {this.altMin = aMin;}

    public void setCondMet(String conMet) {this.condMet = conMet;}

    /**
     * Devolve os quilometros percorridos na actividade
     */
    @Override
    public double kilometros(){
        return this.distancia;
    }

    /**
     * Devolve as calorias queimadas de cada actividade do tipo Outdoor
     */
    @Override
    public abstract double calorias();

    @Override
    public boolean equals(Object o){

        Outdoor c = (Outdoor) o;

        return  (super.equals(o) &&
            (this.getDist() == c.getDist()) &&
            (this.getAltMax() == c.getAltMax()) &&
            (this.getAltMin() == c.getAltMin()) &&
            (this.getCondMet().equals(c.getCondMet())));
    }

    public abstract Outdoor clone();

    @Override
    public abstract String toString();

}