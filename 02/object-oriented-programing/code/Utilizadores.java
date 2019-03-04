import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * Classe criada para organizador o código, contem um conjunto de elmentos da classe Utilizador e seus métudos.
 * Organizado num HashMap sendo como chave o e-mail.
 */
public class Utilizadores implements Serializable 
{   
    private HashMap<String,Utilizador> users;

    /**
     * Construtor vazio
     */
    public Utilizadores(){
        this.users = new HashMap<String,Utilizador>();
    }

    /**
     * Construtor parametrizado
     */
    public Utilizadores(Utilizador u) {
        this.users = new HashMap<String,Utilizador>();
        users.put(u.getEmail(),u.clone());
    }

    /**
     * Construtor por cópia
     */
    public Utilizadores(Utilizadores us){
        this.users = us.getUtilizadores();
    }

    /**
     * Devolve um HashMap com todos os Utilizadores 
     */
    public HashMap<String,Utilizador> getUtilizadores(){
        HashMap<String,Utilizador> aux = new HashMap<String,Utilizador>();      
        for(Utilizador u: this.users.values()){
            aux.put(u.getEmail(),u.clone());
        }
        return aux;
    }

    /**
     * Devolve o Utilizador corespondente a um e-mail e password
     */
    public Utilizador getUtilizador(String email,String pass) throws UtilizadorEnexException{
        Utilizador aux = new Utilizador();      
        if(!existe(email))
            throw new UtilizadorEnexException("E-mail não existe!");

        for(Utilizador u: this.users.values()){
            if(email.equals(u.getEmail()) && pass.equals(u.getPassword())) aux = u.clone();
        }
        return aux;
    }

    /**
     * Devolve um utilizador dado o seu e-mail.
     */
    public Utilizador getUtilizadorM(String email) throws UtilizadorEnexException{
        Utilizador aux = new Utilizador();      
        if(!existe(email))
            throw new UtilizadorEnexException("E-mail não existe!");

        for(Utilizador u: this.users.values()){
            if(email.equals(u.getEmail())) aux = u.clone();
        }
        return aux;
    }

    public void setUtilizadores(Map<String,Utilizador> us){
        this.users = new HashMap<String,Utilizador>();
        for(Utilizador u: us.values()){
            this.users.put(u.getEmail(),u.clone());
        }
    } 

    /**
     * Dado o e-mail correspondente a um Utilizador actualiza a lista de Actividades
     */
    public void actualizaActividades(String email, Actividades l){

        for(Utilizador u:this.users.values()){
            if(email.equals(u.getEmail())){
                u.setUActividades(l);
            }
        }
    }

    /**
     * Dado o e-mail correspondente a um Utilizador actualiza a lista de Amigos
     */
    public void actualizaAmigos(String email, Amigos l){

        for(Utilizador u:this.users.values()){
            if(u.getEmail().equals(email)){
                u.setUAmigos(l);
            }
        }
    }

    /**
     * Guarda os novos dados de um utilizador
     */
    public void alteraRegisto(Utilizador us){

        for(Utilizador u:this.users.values()){
            if(u.getEmail().equals(us.getEmail())){
                u.setPassword(us.getPassword());
                u.setDesportoFav(us.getDesportoFav());
                u.setAltura(us.getAltura());
                u.setPeso(us.getPeso());
            }
        }
    }

    /**
     * Adiciona um Utilizador a um conjunto de elementos da classe Utilizador.
     */
    public void addUtilizador(Utilizador u) throws UtilizadorExistException{
        if(this.existe(u.getEmail()))
            throw new UtilizadorExistException("E-mail já existente!");
        this.users.put(u.getEmail(), u.clone());
    }

    /**
     * Remove um Utilizador de um conjunto de elementos da classe Utilizador.
     */
    public void removeUtilizador(String mail) throws UtilizadorEnexException{
        if(!existe(mail))
            throw new UtilizadorEnexException("E-mail não existe!");
        this.users.remove(mail);
    }

    /**
     * Verifica de um Utilizador existe num conjunto de elementos da classe Utilizador.
     */
    public boolean existe(String email){

        for(Utilizador u: this.users.values()){
            if(email.equals(u.getEmail())) 
            {return true;}
        }
        return false;
    }

    /**
     * Devolve o númeto total de elementos de um conjunto de elemnetos da classe Amigo.
     */
    public int totalUtilizadores(){
        return this.users.size();
    } 

    public Utilizadores clone(){return new Utilizadores(this);}

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Utilizador u : this.users.values()){
            sb.append(u.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean equals(Object o){

        if(this == o) {return true;}

        if((this.getClass() != o.getClass()) || (o == null)) {return false;}

        Utilizadores l = (Utilizadores) o;

        return this.users.equals(l.getUtilizadores());
    }
}
