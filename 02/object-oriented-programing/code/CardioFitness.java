
/**
 *Actividade Cardio Fitness
 *
 */
public class CardioFitness extends Fitness
{
    private static double indice = 0.18;

    /**
     * Construtor vazio
     */
    public CardioFitness(){
        super();
    }

    /**
     * Construtor parametrizado
     */
    public CardioFitness(int id, Data dat, double dur){
        super(id,dat,dur);
    }

    /**
     * Construtor por cápia
     */
    public CardioFitness(CardioFitness o){
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
        sb.append("Actividade: Cardio Fitness\n");
        sb.append("\n Data:");
        sb.append(super.getData().toString());
        sb.append("\n Duração: ");
        sb.append(super.getDuracao() + " min");
        return sb.toString();
    }

    @Override
    public CardioFitness clone() {return new CardioFitness(this);}

    public boolean equals(Object o){
        CardioFitness c = (CardioFitness) o;
        return super.equals(o);
    }  
}
