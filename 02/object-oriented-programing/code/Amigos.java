/**
 * Classe criada para organizador o código, contem um conjunto de elmentos da classe Amigo e seus métudos.
 */
import java.util.ArrayList;
import java.util.TreeMap;
import java.io.Serializable;

public class Amigos implements Serializable
{
    private ArrayList<Amigo> lista;

    /**
     * Construtor vazio
     */
    public Amigos() {
        this.lista = new ArrayList<Amigo>();
    }

    /**
     * Construtor parametrizado
     */
    public Amigos(Amigo amg) {
        this.lista = new ArrayList<Amigo>();
        lista.add(amg.clone());
    }

    /**
     * Construtor por cópia
     */
    public Amigos(Amigos amgs) {
        this.lista = amgs.getAmigos();
    }

    public ArrayList<Amigo> getAmigos() {
        ArrayList<Amigo> aux = new ArrayList<Amigo>();
        for(Amigo a: this.lista)
            aux.add(a.clone());    
        return aux;
    }

    public void setAmigos(ArrayList<Amigo> l){
        this.lista = new ArrayList<Amigo>();
        for(Amigo a: l){
            this.lista.add(a.clone());
        }
    }

    /**
     * Dado um e-mail correspondente a um amigo verifica se ele existe num conjunto.
     */
    public boolean existeAmigo(String mail) {
        for(Amigo a: this.lista){
            if(mail.equals(a.getEmail())) return true;
        }
        return false;          
    }        

    /**
     * Dado um e-mail devolve o Amigo correspondente
     */
    public Amigo getAmigo(String mail) throws AmigoEnexException{
        Amigo aux = new Amigo();

        if(existeAmigo(mail)){
            for(Amigo a: this.lista){
                if(mail.equals(a.getEmail())) aux = a;
            }
        }
        else { throw new AmigoEnexException("Amigo não existe! \n");}
        return aux;
    }

    /**
     * Addiciona um Amigo a um conjunto.
     */
    public void addAmigo(Amigo a) throws AmigoExistException{

        if(existeAmigo(a.getEmail()))
            throw new AmigoExistException("Amigo já existe na lista! \n");

        this.lista.add(a.clone());
    }

    /**
     * Remove um Amigo de um conjunto.
     */
    public void removeAmigo(String mail) throws AmigoEnexException {

        if(existeAmigo(mail)){
            Amigo a = getAmigo(mail);
            this.lista.remove(a);
        }
        else {throw new AmigoEnexException("Amigo não existe! \n"); }
    }   

    /**
     * Ordena um conjunto de elementos da classe Amigo por ordem alfabética.
     */
    public TreeMap<String,Amigo> ordenaPorNome(){
        TreeMap<String,Amigo> novo = new TreeMap<String,Amigo>();

        for(Amigo a: this.lista){
            novo.put(a.getNome(),a.clone());    
        }
        return novo;
    }

    /**
     * Devolve o númeto total de elementos de um conjunto de elemnetos da classe Amigo.
     */
    public int totalAmigos(){
        return lista.size();
    }

    public Amigos clone() {return new Amigos(this);}

    public boolean equals(Object obj) {

        if(this == obj) return true; 

        if((obj == null) || (this.getClass() != obj.getClass())) {return false;}

        Amigos amgs = (Amigos) obj;

        return (this.lista.equals(amgs.getAmigos()));
    }  

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Amigos ---\n");
        for(Amigo a : this.lista)
            sb.append(a.toString() + "\n");
        return sb.toString();
    } 
}