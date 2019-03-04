/**
 *Classe abstract é a classe para actividades do tipo Fitness
 *
 */
import java.io.Serializable;

public abstract class Fitness extends Actividade implements Serializable
{   
    //private String nome;

    private static double indice = 0.14;

    /**
     * Construtor vazio
     */
    public Fitness() {
        super();
    }

    /**
     * Construtor parametrizado
     */
    public Fitness(int id, Data dat, double durac){
        super(id,dat,durac);
    }

    /**
     * Construtor por cápia
     */
    public Fitness(Fitness f){
        super(f);
    }

    /**
     * Devolve as calorias queimadas de cada actividade do tipo Fitness
     */
    public abstract double calorias();

    public abstract Fitness clone();

    @Override
    public boolean equals(Object o){

        Fitness f = (Fitness) o;

        return (super.equals(o));
    }

    @Override
    public abstract String toString(); 
}
