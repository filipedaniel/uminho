import java.io.Serializable;

/**
 * Classe que declara um Amigo para futuramente usar.
 */

public class Amigo implements Serializable
{
    private String email;
    private String nome;

    /**
     * Construtor vazio
     */
    public Amigo () {
        this.email = "";
        this.nome = "";
    }

    /**
     * Construtor parametrizado
     */
    public Amigo (String mail, String n) {
        this.email = mail; 
        this.nome = n;
    }

    /**
     * Construtor por cópia
     */
    public Amigo (Amigo a) {
        this.email = a.getEmail(); 
        this.nome = a.getNome();
    }

    public String getEmail() {return this.email;}
    public String getNome() {return this.nome;}

    public void setEmail (String mail) {this.email=mail;}
    public void setNome (String n) {this.nome=n;}

    public Amigo clone() {return new Amigo(this);}

    public boolean equals(Object o){

        if(this==o) return true;

        if((o==null)||(this.getClass()!=o.getClass())) return false;

        Amigo a = (Amigo) o;

        return ((email.equals(a.getEmail()))&&
            (nome.equals(a.getNome())));
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n Nome: ");sb.append(nome);
        sb.append("\n     - ");sb.append(email);
        return sb.toString();
    }
}
