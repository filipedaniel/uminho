
/**
 *Actividade Dança
 *
 */
public class Danca extends Fitness
{
    private static double indice = 0.075;

    private String estilo;

    /**
     * Construtor vazio
     */
    public Danca(){
        super();
    }

    /**
     * Construtor parametrizado
     */
    public Danca(int id, Data dat, double dur, String e){
        super(id,dat,dur);
        this.estilo = e;
    }

    /**
     * Construtor por cápia
     */
    public Danca(Danca o){
        super(o);
        this.estilo = o.getEstilo();
    }

    public String getEstilo() {return this.estilo;}

    public void setEstilo(String e) {this.estilo = e;}

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
        sb.append("Actividade: Dança\n");
        sb.append("   ->Estilo: " + this.getEstilo());
        sb.append("\n Data:");
        sb.append(super.getData().toString());
        sb.append("\n Duração: ");
        sb.append(super.getDuracao() + " min");
        return sb.toString();
    }

    @Override
    public Danca clone() {return new Danca(this);}

    public boolean equals(Object o){
        Danca c = (Danca) o;
        return super.equals(o);
    }  
}
