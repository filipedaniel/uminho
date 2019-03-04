/**
 *Actividade Andebol
 *
 */
public class Andebol extends Coletivas{

    private static double indice = 0.135;

    /**
     * Construtor vazio
     */
    public Andebol(){
        super();
    }

    /**
     * Construtor parametrizado
     */
    public Andebol(int id, Data dat, double dur, boolean livre, String cM){
        super(id,dat,dur,livre,cM);
    }

    /**
     * Construtor por cápia
     */
    public Andebol(Andebol o){
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
        sb.append("Actividade: Andebol\n");
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
    public Andebol clone() {return new Andebol(this);}

    public boolean equals(Object o){
        Andebol c = (Andebol) o;
        return super.equals(o);
    }   
}
