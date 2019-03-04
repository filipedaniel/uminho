/**
 *Classe abstract é a classe para actividades do tipo Outdoor
 *
 */
import java.io.Serializable;

public abstract class Aquatica extends Actividade implements Serializable
{
    //private String nome;

    private static double indice = 0.16;

    /**
     * Construtor vazio
     */
    public Aquatica(){
        super();
    }

    /**
     * Construtor parametrizado
     */
    public Aquatica(int id, Data dat,double dur){
        super(id,dat,dur);
    }

    /**
     * Construtor por cápia
     */
    public Aquatica(Aquatica p){
        super(p);
    }   

    /**
     * Devolve as calorias queimadas de cada actividade do tipo Aquatica
     */
    public abstract double calorias();
    @Override
    public boolean equals(Object o) {
        Aquatica p = (Aquatica) o;
        return (super.equals(o));
    }

    @Override
    public abstract Aquatica clone();

    @Override
    public abstract String toString();
}
