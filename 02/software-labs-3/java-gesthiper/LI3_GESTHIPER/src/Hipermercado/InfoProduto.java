package Hipermercado;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

/**
 *
 * @author fdr
 * Guarda toda a informação de vendas de um produto
 */
public class InfoProduto implements Serializable{
    
    private String codigoProduto;
    private VendaAnual registoVendas;           // registo de compras anual do produto
    private HashMap<String,Stats> statsGeral;   // para codigo de cliente o seu stats
    //private TreeMap<String,Stats> statsGeral;
    
    public InfoProduto() {
        this.codigoProduto = "";
        this.registoVendas = new VendaAnual();
        this.statsGeral = new HashMap<>();
    }
    
    public InfoProduto(String codigoProdut) {
        this.codigoProduto = codigoProdut;
        this.registoVendas = new VendaAnual();
        this.statsGeral = new HashMap<>();
        this.statsGeral.put(codigoProdut, new Stats(codigoProdut));
    }

    public InfoProduto(InfoProduto i) {
        this.codigoProduto = i.getCodigoProduto();
        this.registoVendas = i.getRegistoVendas();
        this.statsGeral = i.getStatsGeral();
    }
    
    //GET's
    public String getCodigoProduto() {
        return this.codigoProduto;
    }
    public VendaAnual getRegistoVendas() {
        return this.registoVendas.clone();
    }
    public HashMap<String, Stats> getStatsGeral() {
        HashMap<String, Stats> res = new HashMap<>();
        for(Stats n: this.statsGeral.values())
            res.put(n.getCodigo(), n.clone());
        return res;
    }

    //SET's
    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }
    public void setRegistoVendas(VendaAnual registoVendas) {
        this.registoVendas = registoVendas.clone();
    }
    public void setStatsGeral(HashMap<String, Stats> stats) {
        this.statsGeral = new HashMap<>();
        for(Stats n: stats.values())
            this.statsGeral.put(n.getCodigo(), n.clone());
    }

   
    @Override
    public InfoProduto clone(){
        return new InfoProduto(this);
    }
    
    // ******************************************************
    public int getNumeroTotalVendas() {
        return this.registoVendas.getNumeroAnualVendas();
    }

    public int getNumeroTotalVendasMes(int mes) {
        return this.registoVendas.getNumeroTotalVendasMes(mes);
    }
    
    public int getTotalVendas() {
        return this.registoVendas.getTotalAnualVendas();
    }
    
    public float getTotalFaturado(){
        return this.registoVendas.getTotalAnualFaturado();
    }
    
    
    // ******************************************************
    
    /**
     * Regista venda de produto
     */
    public void registarVenda(RegistoCompra r){
        this.registoVendas.registar(r);
        if(!this.statsGeral.containsKey(r.getCodigoCliente())){
           this.statsGeral.put(r.getCodigoCliente(), new Stats(r.getCodigoCliente()));
        }
        this.statsGeral.get(r.getCodigoCliente()).updateStats(r); 
    }

    
    /**
     * Total de Clientes distintos que compraram o prduto num determinado mes
     */
    public int totalClientesDistintosMes(int mes) {
        TreeSet<String> aux = new TreeSet<>();
        int total = 0;
        for(Stats s : this.statsGeral.values()){
            if(s.existeComprasMes(mes))
                aux.add(s.getCodigo());
        }
        return aux.size();
    }
    
    
    /**
     * Total de Faturado num determinado mês
     */
    public float totalFaturadoMes(int mes){
        float total = 0.0f;
        for(Stats s : this.statsGeral.values()){
            if(s.existeComprasMes(mes))
                total+=s.getTotalFaturadoMes(mes);
        }
        return total;
    }
    
    /**
     * Top Compraddores de produto
    */
    public List<Trio_CodQuantFat> topXCompradores(int top){
        List<Trio_CodQuantFat> l = new ArrayList<>();
        ArrayList<Trio_CodQuantFat> aux = new ArrayList<>();
        TreeSet<Trio_CodQuantFat> ordena = new TreeSet<>(new Trio_CodQuantFatCOMPARATOR());
        
        for(Stats s: this.statsGeral.values()){
            ordena.add(new Trio_CodQuantFat(s.getCodigo(),s.getTotalAnualCompras(),s.getTotalAnualFaturado()));
        }
        
        for(Trio_CodQuantFat t : ordena){
            aux.add(t.clone());
        }
        
        for(int x = 0; x<top && x<ordena.size(); x++)
            l.add(aux.get(x).clone());
        
        return l;
    }
   
    public void clear(){
        this.codigoProduto = "";
        this.registoVendas = new VendaAnual();
        this.statsGeral.clear();
    }
    
}
