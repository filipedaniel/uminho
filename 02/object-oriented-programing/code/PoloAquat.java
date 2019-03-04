
/**
 *Actividade Polo Aquatico
 *
 */
public class PoloAquat extends Aquatica
{
    private static double indice = 0.16;

    /**
     * Construtor vazio
     */
    public PoloAquat(){
        super();
    }

    /**
     * Construtor parametrizado
     */
    public PoloAquat(int id, Data dat, double dur){
        super(id,dat,dur);
    }

    /**
     * Construtor por cápia
     */
    public PoloAquat(PoloAquat o){
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
        sb.append("Actividade: Polo Aquatico\n");
        sb.append("\n Data: ");
        sb.append(super.getData().toString());
        sb.append("\n Duração: ");
        sb.append(super.getDuracao() + " min");
        return sb.toString();
    }

    @Override
    public PoloAquat clone() {return new PoloAquat(this);}

    public boolean equals(Object o){
        PoloAquat c = (PoloAquat) o;
        return super.equals(o);
    }
}
