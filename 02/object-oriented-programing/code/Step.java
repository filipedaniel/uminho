
/**
 *Actividade Step
 *
 */
public class Step extends Fitness
{
    private static double indice = 0.142;

    /**
     * Construtor vazio
     */
    public Step(){
        super();
    }

    /**
     * Construtor parametrizado
     */
    public Step(int id, Data dat, double dur){
        super(id,dat,dur);
    }

    /**
     * Construtor por cápia
     */
    public Step(Step o){
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
        sb.append("Actividade: Step\n");
        sb.append("\n Data:");
        sb.append(super.getData().toString());
        sb.append("\n Duração: ");
        sb.append(super.getDuracao() + " min");
        return sb.toString();}

    @Override
    public Step clone() {return new Step(this);}

    public boolean equals(Object o){
        Step c = (Step) o;
        return super.equals(o);
    }  
}
