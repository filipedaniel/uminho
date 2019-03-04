
/**
 *Actividade Natação
 *
 */
public class Natacao extends Aquatica
{
    private static double indice = 0.133;

    /**
     * Construtor vazio
     */
    public Natacao(){
        super();
    }

    /**
     * Construtor parametrizado
     */
    public Natacao(int id, Data dat, double dur){
        super(id,dat,dur);
    }

    /**
     * Construtor por cápia
     */
    public Natacao(Natacao o){
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
        sb.append("Actividade: Natação\n");
        sb.append("\n Data: ");
        sb.append(super.getData().toString());
        sb.append("\n Duração: ");
        sb.append(super.getDuracao() + " min");
        return sb.toString();
    }

    @Override
    public Natacao clone() {return new Natacao(this);}

    public boolean equals(Object o){
        Natacao c = (Natacao) o;
        return super.equals(o);
    }    
}
