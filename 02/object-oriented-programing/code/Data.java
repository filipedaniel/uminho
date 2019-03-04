/**
 * Classe usada para de alguma forma substituir o classe já definida no Java Gregorian Calendar no uso de datas.
 */
import java.io.Serializable;

public class Data implements Comparable<Data>,Serializable
{
    private int dia;
    private int mes;
    private int ano;

    /**
     * Construtor vazio.
     */
    public Data(){
        this.dia = 0;
        this.mes = 0;
        this.ano = 0;
    }
    
    /**
     * Construtor parametrizado.
     */
    public Data(int d, int m, int a){
        this.dia = d;
        this.mes = m;
        this.ano = a;
    }

    /**
     * Construtor por cópia.
     */
    public Data(Data d){
        this.dia = d.getDia();
        this.mes = d.getMes();
        this.ano = d.getAno();
    }
    
    
    public int getDia() {return this.dia;}
    public int getMes() {return this.mes;}
    public int getAno() {return this.ano;}

    public void setDia(int d){this.dia = d;}
    public void setMes(int m) {this.mes = m;}
    public void setAno(int a) {this.ano = a;}

    public Data clone() {return new Data(this);}

    public boolean equals(Object o){
        if(this == o) {return true;}

        if((this.getClass() != o.getClass()) || (o == null)) {return false;}

        Data d = (Data) o;

        return ((this.dia == d.getDia()) &&
            (this.mes == d.getMes()) &&
            (this.ano == d.getAno()));  
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.dia + "-" + this.mes + "-" + this.ano);
        return sb.toString();
    }

    /**
     * Comparador de datas. 
     */
    public int compareTo(Data d1) {
        if(this.ano > d1.getAno()) {return -1;}
        if(this.ano == d1.getAno() && this.mes > d1.getMes()) {return -1;}
        if(this.ano == d1.getAno() && this.mes == d1.getMes() && this.dia > d1.getDia()) {return -1;}
        if(this.dia == d1.getDia() && this.mes == d1.getMes() && this.ano == d1.getAno()) {return 0;}
        else {return 1;}
    }
}
