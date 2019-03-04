package Hipermercado;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.TreeMap;

/**
 *
 * @author fdr
 * Guarda todos os registos de compras invalidos
 */
public class RegistosInvalidos {
    
    //<Linha,Registo>
    private TreeMap<Integer,String> invalidos;
    //private HashMap<Integer,String> invalidos;

    
    public RegistosInvalidos(){
        this.invalidos = new TreeMap<>();
    }
    
    public RegistosInvalidos(TreeMap<Integer, String> invalidos) {
        this.invalidos = invalidos;
    }
    
    public RegistosInvalidos(RegistosInvalidos r){
        this.invalidos = r.getInvalidos();
    }
    
    public TreeMap<Integer, String> getInvalidos() {
        return invalidos;
    }

    /**
     * Insere Registo de COmpra invalido
     */
    public void insereRegistoInvalido(String cod, int linha){
        this.invalidos.put(linha, cod);
    }
    
    /**
     * Total de Registos Invalidos
     */
    public int totalInvalidos(){
        return this.invalidos.size();
    }
    
    /**
     * Guarda todos os registos invalidos num ficheiro: "REGISTOSINVALIDOS.txt"
     */
    public void saveToText() throws IOException{
        FileWriter f = new FileWriter("REGISTOSINVALIDOS.txt");
        f.write("Total registos inválidos: "+this.invalidos.size()+"\n");
        for(Integer i : this.invalidos.keySet())
            f.write(i + "- " + this.invalidos.get(i));
        f.close();
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total de Registos Inválidas: ").append(this.invalidos.size());
        for(Integer i : this.invalidos.keySet())
            sb.append(i.toString()).append(" - ").append(this.invalidos.get(i));
        return sb.toString();
    }
    
    
    public RegistosInvalidos clone(){
        return new RegistosInvalidos(this);
    }


    
    public boolean equals(RegistosInvalidos obj) {
        if (this == obj) {return true;}
        if ((obj == null) || (getClass() != obj.getClass())) { return false; }
       
        final RegistosInvalidos other = (RegistosInvalidos) obj;
        if (!Objects.equals(this.invalidos, other.invalidos)) {
            return false;
        }
        return true;
    }
    
    public void clear(){
        this.invalidos.clear();
    }
    
}
