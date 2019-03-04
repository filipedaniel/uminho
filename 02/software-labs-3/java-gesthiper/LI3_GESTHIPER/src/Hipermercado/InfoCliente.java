/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hipermercado;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author fdr
 * Guarda toda a informação de compras de um produto
 */
public class InfoCliente implements Serializable{
    
    private String codigoCliente;
    private VendaAnual registoCompras; // registo de compras anual do cliente
    private HashMap<String,Stats> statsGeral;
    //private TreeMap<String,Stats> statsGeral;

    
    public InfoCliente() {
        this.codigoCliente = "";
        this.registoCompras = new VendaAnual();
        this.statsGeral = new HashMap<>();
    }
    
    public InfoCliente(String codigoClient) {
        this.codigoCliente = codigoClient;
        this.registoCompras = new VendaAnual();
        this.statsGeral = new HashMap<>();
        this.statsGeral.put(codigoClient, new Stats(codigoClient));
    }

    public InfoCliente(InfoCliente i) {
        this.codigoCliente = i.getCodigoCliente();
        this.registoCompras = i.getRegistoCompras();
        this.statsGeral = i.getStatsGeral();
    }
    
    // GET's
    public String getCodigoCliente() {
        return this.codigoCliente;
    }
    public VendaAnual getRegistoCompras() {
        return this.registoCompras.clone();
    }
    public HashMap<String, Stats> getStatsGeral() {
        HashMap<String, Stats> res = new HashMap<>();
        for(Stats n: this.statsGeral.values())
            res.put(n.getCodigo(), n.clone());
        return res;
    }

    // SET's
    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }
    public void setRegistoCompras(VendaAnual registoCompras) {
        this.registoCompras = registoCompras.clone();
    }
    public void setStatsGeral(HashMap<String, Stats> stats) {
        this.statsGeral = new HashMap<>();
        for(Stats n: stats.values())
            this.statsGeral.put(n.getCodigo(), n.clone());
    }
    

    
    @Override
    public InfoCliente clone(){
        return new InfoCliente(this);
    }
    
    
    // ******************************************************
    public int getNumeroTotalCompras() {
        return this.registoCompras.getNumeroAnualVendas();
    }
    
    public int getNumeroTotalVendasMes(int mes) {
        return this.registoCompras.getNumeroTotalVendasMes(mes);
    }
    
    public int getTotalCompras() {
        return this.registoCompras.getTotalAnualVendas();
    }
    
    public float getTotalFaturado(){
        return this.registoCompras.getTotalAnualFaturado();
    }

    // ******************************************************
    
    /**
     * Registar compra
     */
    public void registarCompra(RegistoCompra r){
        this.registoCompras.registar(r);
        if(!this.statsGeral.containsKey(r.getCodigoProduto())){
           this.statsGeral.put(r.getCodigoProduto(), new Stats(r.getCodigoProduto()));
        }
        this.statsGeral.get(r.getCodigoProduto()).updateStats(r);
    }
    
    /**
     * Total de Produtos distintos que foram comprados por cliente num determinado mes
     */
    public int totalProdutosDistintosCompradosMes(int mes) {
        TreeSet<String> aux = new TreeSet<>();
        int total = 0;
        for(Stats s : this.statsGeral.values()){
            if(s.existeComprasMes(mes))
                aux.add(s.getCodigo());
        }
        return aux.size();
    }
    
    /**
     * Total de Produtos distintos que foram comprados por cliente
     */
    public int totalProdutosDistintosComprados() {
        TreeSet<String> aux = new TreeSet<>();
        int total = 0;
        for(int i = 1; i <= 12; i++)
            for(Stats s : this.statsGeral.values()){
                if(s.existeComprasMes(i))
                    aux.add(s.getCodigo());
            }
        return aux.size();
    }
    
    /**
     * Total de Gasto num determinado mês
     */
    public float totalGastoMes(int mes){
        float total = 0.0f;
        for(Stats s : this.statsGeral.values()){
            if(s.existeComprasMes(mes))
                total+=s.getTotalFaturadoMes(mes);
        }
        return total;
    }

    
    /**
     * Top Produtos Conprados de Cliente
     */
    public List<Par_CodQuant> getTopCompras(){
        List<Par_CodQuant> res = new ArrayList<>();
        TreeSet<Par_CodQuant> aux = new TreeSet<>(new Par_CodQuantCOMPARATOR());
        for (Stats s : this.statsGeral.values()) {
            aux.add(new Par_CodQuant(s.getCodigo(),s.getTotalAnualCompras()));
        }
        for (Par_CodQuant p : aux) {
            res.add(p.clone());
        }
        return res;
    }
    
    public void clear(){
        this.codigoCliente = "";
        this.registoCompras = new VendaAnual();
        this.statsGeral.clear();
    }
}
    
    
    
  
