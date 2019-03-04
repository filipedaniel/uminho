
/**
 *Actividade Hidroginástica
 *
 */
public class Hidroginastica extends Aquatica
{
    private static double indice = 0.066;

    /**
     * Construtor vazio
     */
    public Hidroginastica(){
        super();
    }

    /**
     * Construtor parametrizado
     */
    public Hidroginastica(int id, Data dat, double dur){
        super(id,dat,dur);
    }

    /**
     * Construtor por cápia
     */
    public Hidroginastica(Hidroginastica o){
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
        sb.append("\n ........................................ \n");
        sb.append("Actividade: Hidroginástica\n");
        sb.append("\n Data: ");
        sb.append(super.getData().toString());
        sb.append("\n Duração: ");
        sb.append(super.getDuracao() + " min");
        return sb.toString();
    }

    @Override
    public Hidroginastica clone() {return new Hidroginastica(this);}

    public boolean equals(Object o){
        Hidroginastica c = (Hidroginastica) o;
        return super.equals(o);
    }
}
