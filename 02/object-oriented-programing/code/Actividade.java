/**
 *Classe abstract é a classe genérica para cada uma das actividades.
 *
 */
import java.io.Serializable;

public abstract class Actividade implements Comparable<Actividade>, Serializable
{
    private int id_actividade;
    private Data data;
    private double duracao;
    
    /**
     * Construtor vazio
     */
    public Actividade(){
        this.id_actividade = 0;
        this.data = new Data();
        this.duracao = 0;
    }
    
    /**
     * Construtor parametrizado
     */
    public Actividade(int id, Data dat, double dur) {
            this.id_actividade = id;
            this.data = new Data(dat.getDia(),dat.getMes(),dat.getAno());
            this.duracao = dur;
    }
    
    /**
     * Construtor por cápia
     */
    public Actividade(Actividade a){
        this.id_actividade = a.getIdActividade();
        this.data = a.getData();
        this.duracao = a.getDuracao();
    }
    
    public int getIdActividade() {return this.id_actividade;}
    public Data getData() {return this.data;}
    public double getDuracao() {return this.duracao;}
    
    public void setIdActividade(int id) {this.id_actividade = id;}
    public void setData(int d, int m, int a) {this.data = new Data(d,m,a);}
    public void setDuracao(double dur) {this.duracao = dur;}
    
    public abstract double calorias();
    
    public boolean equals(Object o){
        if(this == o) {return true;}
        
        if((this.getClass() != o.getClass()) || (o == null)) {return false;}
        
        Actividade a = (Actividade) o;
        
        return ((this.duracao == a.getDuracao())&&
                (this.id_actividade == a.getIdActividade())&&
                (this.data.equals(a.getData())));
    }
    
    public abstract Actividade clone();
    
    public abstract String toString();
    
    /**
     * Comparador de Acitividade, usa o comparador de data, organiza por data, da mais recente para a mais antiga
     */
    public int compareTo(Actividade o1) {
        return ((this.data).compareTo(o1.getData()));
    }    
}