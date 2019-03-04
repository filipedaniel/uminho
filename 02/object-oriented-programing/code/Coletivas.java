import java.io.Serializable;
/**
 *Classe abstract é a classe para actividades do tipo Coletivas
 *
 */
public abstract class Coletivas extends Actividade implements Serializable
{ 
    private boolean arlivre;
    private String cndMet;    

    /**
     * Construtor vazio
     */
    public Coletivas(){
        super();
        this.arlivre = false;
        this.cndMet = "";
    }

    /**
     * Construtor parametrizado
     */
    public Coletivas (int id, Data dat, double dur, boolean livre, String cM){       
        super(id,dat,dur);
        this.arlivre = livre;
        this.cndMet = cM;
    }

    /**
     * Construtor por cápia
     */
    public Coletivas (Coletivas c){
        super(c);
        this.arlivre = c.getArlivre();
        if(c.getArlivre() == true) {this.cndMet = c.getCndMet();}
    }

    public boolean getArlivre() {return this.arlivre;}

    public String getCndMet() {return this.cndMet;} 

    public void setArlivre(boolean liv) {this.arlivre = liv;}

    public void setCndMet(String conMet) {this.cndMet = conMet;}

    /**
     * Devolve as calorias queimadas de cada actividade do tipo Coletivas
     */
    public abstract double calorias();

    @Override
    public abstract Coletivas clone();

    @Override
    public boolean equals(Object o){

        Coletivas c = (Coletivas) o;

        return (super.equals(o) &&
            (this.getArlivre() == c.getArlivre()) &&
            (this.getCndMet().equals(c.getCndMet())));
    }

    @Override
    public abstract String toString();
} 