package Hipermercado;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author fdr
 * Catálogo (código) dos clientes e produtos existentes;

 */
public class Catalogo implements Serializable{

    private HashMap<Character,TreeSet<String>> catalogo;
    //private TreeMap<Character,TreeSet<String>> catalogo;
    private int totalCodigos;

    // Construtores
    public Catalogo() {
        this.catalogo = new HashMap<>();
        this.totalCodigos = 0;
    }
    
    public Catalogo(Catalogo cat) {
        this.catalogo = new HashMap<>(cat.catalogo);
        this.totalCodigos = cat.getTotalCodigos();
    }

    // GET's
    public int getTotalCodigos() {
        return this.totalCodigos;
    }

    public HashMap<Character, TreeSet<String>> getCatalogo() {
        HashMap<Character, TreeSet<String>> res = new HashMap<>();
        TreeSet<String> aux;
        for(Character c : catalogo.keySet()){
            for (TreeSet<String> ts : this.catalogo.values()) {
                aux = new TreeSet<>();
                for (String st : ts) {
                    aux.add(st);
                }
                res.put(c, aux);
          }
        }
        return res;
    }
    
    // SET'S
    public void setTotalCodigos(int totalCodigos) {
        this.totalCodigos = totalCodigos;
    }

    public void setCatalogo(HashMap<Character, TreeSet<String>> c) {
        this.catalogo = new HashMap<>();
        TreeSet<String> aux;
        for(Character ch: catalogo.keySet()){
            for (TreeSet<String> ts : c.values()) {
                aux = new TreeSet<>();
                for (String st : ts) {
                    aux.add(st);
                }
                this.catalogo.put(ch, aux);
            }
        }
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb  =new StringBuilder();
        sb.append("Existem um total de ").append(this.totalCodigos).append("registos.");
        return sb.toString();
    }

    @Override
    protected Catalogo clone(){
         return new Catalogo(this);
    }
  
    /**
     * Insere um novo código
     * @param s
     */
    public void insertCodigo(String s){
        String ss = s.toUpperCase();
        TreeSet<String> set = this.catalogo.get(ss.charAt(0));
        this.totalCodigos++;
        if(set != null){ set.add(ss);}
        else{
            set = new TreeSet<>();
            set.add(ss);
            this.catalogo.put(ss.charAt(0), set);
        }
    }
    
    /**
     * Devolve um "Set" de códigos dado primeira letra
     */
    public Set<String> getCodigosPorLetra(char ch) {
        TreeSet<String> set = new TreeSet<>();
        TreeSet<String> codigos = codigos = catalogo.get(Character.toUpperCase(ch));
        if(!codigos.isEmpty()){
            Iterator<String> it = codigos.iterator();
            while(it.hasNext()){
                String s = it.next();
                set.add(s);
            }
        }
        return set;
    }
    
    /**
     * Verifica se código existe
     */
    public boolean codigoExiste(String codigo){
        char c = codigo.toUpperCase().charAt(0);
        if(this.catalogo.get(c).isEmpty())
            return false;
        else return this.catalogo.get(c).contains(codigo);
    }
    
    /**
     * Devolve o número total de codigos 
     */
    public int totalCodigos(){
        return this.totalCodigos;
    }
    
    /**
     * Devolve o numero total de códigos dado primeira letra 
     */
    public int totalCodigosLetra(char c){
        return this.catalogo.get(Character.toUpperCase(c)).size();
    }
        
    /**
     * Limpa todos as registos do Catálogo
     */
    public void clearCatalogo(){
        this.catalogo.clear();
    }
    
}
