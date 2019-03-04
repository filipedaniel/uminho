/**
 * Classe que declara um Utilizador e suas variaveis para futuramente usar.
 */

import java.io.Serializable;

public class Utilizador implements Serializable 
{
    private String email, nome, password, desportoFav;
    private String genero; 
    private Data dataNasc;
    private double altura, peso;
    private Amigos amigos;
    private Actividades activ;

    /**
     * Construtor vazio
     */
    public Utilizador() {
        this.email = this.nome = this.password = this.desportoFav = "";
        this.genero = "";
        this.dataNasc = new Data();
        this.altura = this.peso = 0;
        this.amigos = new Amigos();
        this.activ = new Actividades();
    }

    /**
     * Construtor parametrizado
     */
    public Utilizador(String mail, String nome, String pass, String genero, Data dat, 
    String desportoFav, double altura, double peso, Amigos a, Actividades l) {
        this.email = mail;
        this.nome = nome;
        this.password = pass;
        this.genero = genero;
        this.dataNasc = new Data(dat.getDia(),dat.getMes(),dat.getAno());
        this.desportoFav = desportoFav;
        this.altura = altura;
        this.peso = peso;
        this.setUAmigos(a);
        this.setUActividades(l);
    }

    /**
     * Construtor por cápia
     */
    public Utilizador(Utilizador u){
        this.email = u.getEmail();
        this.nome = u.getNome();
        this.password = u.getPassword();
        this.genero = u.getGenero();
        this.dataNasc = u.getDataNasc();
        this.desportoFav = u.getDesportoFav();
        this.altura = u.getAltura();
        this.peso = u.getPeso();
        this.amigos = u.getUAmigos();
        this.activ = u.getUActividades();
    }

    public String getEmail() {return this.email;}

    public String getNome() {return this.nome;}

    public String getPassword() {return this.password;}

    public String getGenero() {return this.genero;}

    public Data getDataNasc() {return this.dataNasc;}

    public String getDesportoFav() {return this.desportoFav;}

    public double getAltura() {return this.altura;}

    public double getPeso() {return this.peso;}

    public Amigos getUAmigos(){ return this.amigos.clone();}

    public Actividades getUActividades() {return this.activ.clone();}

    public void setEmail(String e) {this.email = e;}

    public void setNome(String n) {this.nome = n;}

    public void setPassword(String p) {this.password = p;}

    public void setGenero(String g) {this.genero = g;}

    public void setData(int d, int m, int a) {this.dataNasc = new Data(d,m,a);}

    public void setDesportoFav(String df) {this.desportoFav = df;}

    public void setAltura(double a) {this.altura = a;}

    public void setPeso(double p) {this.peso = p;}

    public void setUAmigos(Amigos a){
        this.amigos = new Amigos();
        this.amigos = a.clone();
    }

    public void setUActividades(Actividades l){
        this.activ = new Actividades();
        this.activ = l.clone();
    }

    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append("\n Utilizador:");
        s.append("\n Email: " + this.email);
        //s.append("\n Pass : " + this.password);
        s.append("\n Nome: " + this.nome);
        s.append("\n Género: " + this.genero.toString());
        s.append("\n Data de nascimento: " + getDataNasc().toString());
        s.append("\n Desporto Favorito: " + this.desportoFav);
        s.append("\n Altura: " + this.altura + " m");
        s.append("\n Peso: " + this.peso + " kg");

        return s.toString();
    }

    public Utilizador clone() {return new Utilizador(this);}

    public boolean equals(Object o) 
    {
        if (this == o) return true;

        if ( (o == null) || ( o.getClass() != this.getClass()) )return false;

        Utilizador u = (Utilizador) o;

        return (this.email.equals(u.getEmail())&&this.nome.equals(u.getNome()));

    }

    public double totalColorias() {
        return activ.totalCalorias();
    }
}
