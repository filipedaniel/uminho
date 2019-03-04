
/**
 *Actividade Pilates
 *
 */
public class Pilates extends Fitness
{
    private static double indice = 0.116;

    /**
     * Construtor vazio
     */
    public Pilates(){
        super();
    }

    /**
     * Construtor parametrizado
     */
    public Pilates(int id, Data dat, double dur){
        super(id,dat,dur);
    }

    /**
     * Construtor por cápia
     */
    public Pilates(Pilates o){
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
        sb.append("\n ........................................\n");
        sb.append("Actividade: Pilates\n");
        sb.append("\n Data:");
        sb.append(super.getData().toString());
        sb.append("\n Duração: ");
        sb.append(super.getDuracao() + " min");
        return sb.toString();
    }

    @Override
    public Pilates clone() {return new Pilates(this);}

    public boolean equals(Object o){
        Pilates c = (Pilates) o;
        return super.equals(o);
    }  
}
