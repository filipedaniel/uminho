/**
 *Actividade Futebol
 *
 */
public class Futebol extends Coletivas{

    private static double indice = 0.126;

    /**
     * Construtor vazio
     */
    public Futebol(){
        super();
    }

    /**
     * Construtor parametrizado
     */
    public Futebol(int id, Data dat, double dur, boolean livre, String cM){
        super(id,dat,dur,livre,cM);
    }

    /**
     * Construtor por cápia
     */
    public Futebol(Futebol o){
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
        sb.append("Actividade: Futebol\n");
        sb.append("\n Data: ");
        sb.append(super.getData().toString());
        sb.append("\n Activadade: ");
        sb.append("\n Duração: ");
        sb.append(this.getDuracao() + " min");
        sb.append("\n Ar Livre: ");
        if(super.getArlivre() == true){
            sb.append("Sim");
            sb.append("\n   Condição Atmosférica: ");
            sb.append(super.getCndMet());}

        else {sb.append("Não");}
        return sb.toString();
    }

    @Override
    public Futebol clone() {return new Futebol(this);}

    public boolean equals(Object o){
        Futebol c = (Futebol) o;
        return super.equals(o);
    }   
}
