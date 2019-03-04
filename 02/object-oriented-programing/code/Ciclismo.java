/**
 *Actividade Ciclismo
 *
 */
public class Ciclismo extends Outdoor{

    private static double indice = 0.133;

    /**
     * Construtor vazio
     */
    public Ciclismo(){
        super();
    }

    /**
     * Construtor parametrizado
     */
    public Ciclismo(int id, Data dat, double dur, double d, double aMax, double aMin, String cM){
        super(id,dat,dur,d,aMax,aMin,cM);
    }

    /**
     * Construtor por cápia
     */
    public Ciclismo(Ciclismo o){
        super(o);
    }

    /**
     * Devolve as calorias quimadas nesta actividade
     */
    @Override
    public double calorias() {
        return indice * 70 * super.getDuracao();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n ........................................\n ");
        sb.append("Actividade: Ciclismo\n");
        sb.append("\n Data: ");
        sb.append(super.getData().toString());
        sb.append("\n Duração: ");
        sb.append(super.getDuracao() + " min");
        sb.append("\n Distancia: ");
        sb.append(super.getDist() + " kms");
        sb.append("\n Altitude Maxima: ");
        sb.append(super.getAltMax() + " m");
        sb.append("\n Altitude Minima: ");
        sb.append(super.getAltMin() + " m");
        sb.append("\n Condição Atmosférica: ");
        sb.append(super.getCondMet());
        return sb.toString();
    }

    @Override
    public Ciclismo clone() {return new Ciclismo(this);}

    public boolean equals(Object o){

        Ciclismo c = (Ciclismo) o;

        return super.equals(o);
    }
}
