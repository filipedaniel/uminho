import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Classe criada com o objectivo de organizador o código, contem um conjunto de elmentos da classe Actividade e seus métudos.
 * Organizado num HashMap sendo como chave o id de cada Actividade, sendo isso um identificador unico..
 * 
 */

public class Actividades implements Serializable
{
    /**
     * HashMap da lista de todos as actividades no sistema.
     */
    private HashMap<Integer,Actividade> l_actividades;   

    public Actividades(){
        this.l_actividades = new HashMap<Integer, Actividade>();
    }

    public Actividades(Map<Integer,Actividade> act){
        this.setActividades(act);
    }

    public Actividades (Actividades act){
        this.l_actividades = act.getActividades();
    }

    public HashMap<Integer,Actividade> getActividades(){
        HashMap<Integer,Actividade> aux = new HashMap<Integer,Actividade>(); 
        for(Actividade a:this.l_actividades.values()){
            aux.put(a.getIdActividade(),a.clone());
        }
        return aux;
    }

    public void setActividades(Map<Integer,Actividade> a){
        this.l_actividades = new HashMap<Integer,Actividade>();
        for(Actividade ac: a.values()){
            this.l_actividades.put(ac.getIdActividade(),ac.clone());
        }
    }

    /**
     * Devolve todas as actividades de um dado tipo, passado por parametro.
     */
    public TreeSet<Actividade> getActividadePorTipo(String tipo){
        TreeSet<Actividade> aux = new TreeSet<Actividade>();      
        for(Actividade a : this.l_actividades.values()){
            if(a.getClass().getSimpleName().equals(tipo)) aux.add(a.clone());
        }
        return aux;
    } 

    /**
     * Devolve as ultimas 10 Actividades paraticadas, organizadas por data.
     */
    public TreeSet<Actividade> getUltimas10Actividades(){
        TreeSet<Actividade> aux = new TreeSet<Actividade>();
        TreeSet<Actividade> novo = new TreeSet<Actividade>();
        int i = 10;
        for(Actividade a: this.l_actividades.values()){
            aux.add(a.clone());    
        }
        if(aux.size() < 10){i = aux.size();}
        for(Actividade a: aux){
            if(i>0) {novo.add(a.clone());i--;}
        }
        return novo;
    }

    /**
     * Devolve as Actividades paraticadas do ultimo mês (mês presente).
     */
    public Actividades getUltimoMes() throws ActividadeExistException{
        TreeSet<Actividade> aux = new TreeSet<Actividade>(); 
        Actividades novo = new Actividades();
        for(Actividade a: this.l_actividades.values()){
            aux.add(a.clone());    
        }
        Actividade ac = aux.first();
        int i = ac.getData().getMes();
        int an = ac.getData().getAno();
        for(Actividade a: this.l_actividades.values()){
            if((a.getData().getAno() == an) && (a.getData().getMes() == i))
                novo.addActividade(a.clone());
        }
        return novo;
    }

    /**
     * Devolve as Actividades paraticadas num ano passado por parametro
     */
    public Actividades getAnual(int ano) throws ActividadeExistException{
        Actividades novo = new Actividades();
        for(Actividade a: this.l_actividades.values()){
            if(a.getData().getAno() == ano)
                novo.addActividade(a.clone());
        }
        return novo;
    }

    /**
     * Ordena as actividades atravez do conmparador definido para a classe Actividade
     */
    public TreeSet<Actividade> ordenar(){
        TreeSet<Actividade> novo = new TreeSet<Actividade>();  
        for(Actividade a: this.l_actividades.values()){
            novo.add(a.clone());    
        }
        return novo;
    }

    /**
     * Adiciona uma actividade a um conjunto de elementos da classe Actividade.
     */
    public void addActividade(Actividade a) throws ActividadeExistException{
        this.l_actividades.put(a.getIdActividade(), a.clone());
    }

    /**
     * Remove uma actividade de um conjunto de elementos da classe Actividade.
     */
    public void removeActividade(int id) throws ActividadeEnexException{
        this.l_actividades.remove(id);
    }

    /**
     * Devolve o númeto total de actividades.
     */
    public int totolAvtividades(HashMap<Integer,Actividade> acts){
        return acts.size();
    }    

    public Actividades clone() {return new Actividades(this);}

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Actividade a : this.l_actividades.values()){
            sb.append(a.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean equals(Object o){

        if(this == o) {return true;}

        if((this.getClass() != o.getClass()) || (o == null)) {return false;}

        Actividades l = (Actividades) o;

        return this.l_actividades.equals(l.getActividades());
    }

    /**
     * Total de calorias de um conjunto de actividades.
     */
    public double totalCalorias(){
        double total = 0.0;
        for(Actividade a: this.l_actividades.values())
            total += a.calorias();
        return total;    
    }

    /**
     * Total de calorias de um conjunto de actividades, de um dado tipo.
     */
    public double totalCaloriaspPorTipo(String tipo){
        double total = 0.0;
        for(Actividade a: this.l_actividades.values())
            if ((a.getClass().getSimpleName()).equals(tipo))
                total+=a.calorias();
        return total;    
    }

    /**
     * Devolve o númeto total de actividades.
     */
    public int totalActividades(){
        int total = 0;
        for(Actividade a: this.l_actividades.values())
            total++;
        return total;    
    }

    /**
     * Devolve o númeto total de actividades de um dado tipo.
     */
    public int totalActividadesPorTipo(String tipo){
        int total = 0;
        for(Actividade a: this.l_actividades.values())
            if ((a.getClass().getSimpleName()).equals(tipo))
                total++;
        return total;
    }

    /**
     * Total de quilometros de um conjunto de actividades.
     */
    public double totalkilometros(){
        double km = 0.0;
        for(Actividade ac: this.l_actividades.values())
            if(ac instanceof Kilometragem){
                Kilometragem o = (Kilometragem) ac;
                km += o.kilometros(); 
        }
        return km;
    }

    /**
     * Total tempo gasto em actividade num conjunto de actividades.
     */
    public double totalDuracao(){
        double total = 0.0;
        for(Actividade a: this.l_actividades.values())
            total += a.getDuracao();
        return total;
    }

    /**
     * Maximo tempo gasto numa só actividade.
     */
    public double maximaDuracao(){
        double max = 0.0;
        for(Actividade a: this.l_actividades.values())
            if(max < a.getDuracao()) max = a.getDuracao();
        return max;
    }

    /**
     * Maximo de calorias queimadas numa só actividade.
     */
    public double maximoCalorias(){
        double max = 0.0;
        for(Actividade a: this.l_actividades.values())
            if(max < a.calorias()) max = a.calorias();
        return max;    
    }

    /**
     * Maximo de calorias queimadas numa só actividade, dde um dato tipo de actividade.
     */
    public double totalDuracaoPorTipo(String tipo){
        double total = 0.0;
        for(Actividade a: this.l_actividades.values())
            if ((a.getClass().getSimpleName()).equals(tipo))
                total += a.getDuracao();
        return total;
    }

    /**
     * Maximo altitude numa só actividade.
     */
    public double maxAltitude(){
        double max = 0.0;
        for(Actividade a: this.l_actividades.values())
            if(a instanceof Outdoor){
                Outdoor o = (Outdoor) a;
                if(max<o.getAltMax()){max = o.getAltMax();}
        }
        return max;
    }

    /**
     * Minama altitude numa só actividade.
     */
    public double minAltitude(){
        double min = 1000.0;
        for(Actividade a: this.l_actividades.values())
            if(a instanceof Outdoor){
                Outdoor o = (Outdoor) a;
                if(min>o.getAltMin()){min = o.getAltMin();}
        }
        return min;
    }    
}
