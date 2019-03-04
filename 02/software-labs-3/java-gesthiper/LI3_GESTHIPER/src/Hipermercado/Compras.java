package Hipermercado;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author fdr
 * Informação sobre todos os registos de compras no Hipermercado
 */
public class Compras implements Serializable{
    
    HashMap<String,InfoProduto> produtos; //Produto,Informação do produto
    HashMap<String,InfoCliente> clientes; //Cliente,Informação do cliente
    //TreeMap<String,InfoProduto> produtos; //Produto,Informação do produto
    //TreeMap<String,InfoCliente> clientes; //Cliente,Informação do cliente
    
   
    public Compras(){
        this.produtos = new HashMap<>();
        this.clientes = new HashMap<>();
    }
    
    public Compras(Compras c){
        this.produtos = c.getProdutos();
        this.clientes = c.getClientes();
    }
    
    public HashMap<String, InfoProduto> getProdutos(){
        HashMap<String, InfoProduto> r = new HashMap<>();
        for(InfoProduto v: this.produtos.values()){
            r.put(v.getCodigoProduto(), v.clone());
        }
        return r;
    }
    
    public HashMap<String, InfoCliente> getClientes(){
        HashMap<String, InfoCliente> r = new HashMap<>();
        for(InfoCliente v: this.clientes.values()){
            r.put(v.getCodigoCliente(), v.clone());
        }
        return r;
    }
    
    public void setProdutos(HashMap<String, InfoProduto> p){
        this.produtos = new HashMap<>();
        for(InfoProduto v: p.values()){
            this.produtos.put(v.getCodigoProduto(),v.clone());
        }
    }
    
    public void setClientes(HashMap<String, InfoCliente> p){
        this.clientes = new HashMap<>();
        for(InfoCliente v: p.values()){
            this.clientes.put(v.getCodigoCliente(),v.clone());
        }
    }
    

    public void insertCodigoCliente(String cC) {
        this.clientes.put(cC, new InfoCliente(cC));
    }
   
    public void insertCodigoProduto(String cP) {
       this.produtos.put(cP, new InfoProduto(cP));
    }

    
    public void registerSale(RegistoCompra r) {
        if(!this.produtos.containsKey(r.getCodigoProduto()))
        {
            this.produtos.put(r.getCodigoProduto(), new InfoProduto());}
        if(!this.clientes.containsKey(r.getCodigoCliente()))
        {
            this.clientes.put(r.getCodigoCliente(), new InfoCliente());
        }
        this.produtos.get(r.getCodigoProduto()).registarVenda(r.clone());
        this.clientes.get(r.getCodigoCliente()).registarCompra(r.clone());
    }

    
    public Compras clone() {
        return new Compras(this);
    }

    
    
    public int totalDiferentesProdutosQueForamCompras() {
        int total = 0;
        TreeSet<String> t = new TreeSet<>();
        for(InfoProduto i : this.produtos.values()){
            if(i.getNumeroTotalVendas() != 0)
                t.add(i.getCodigoProduto());
        }
        return t.size();
    }
    
    public int totalDiferentesClientesQueEfetuaramCompras() {
        int total = 0;
        TreeSet<String> t = new TreeSet<>();
        for(InfoCliente i : this.clientes.values()){
            if(i.getNumeroTotalCompras() != 0)
                t.add(i.getCodigoCliente());
        }
        return t.size();
    }
    
    public int totalClientesDistintosEfetuaramCompras(int mes) {
        int total = 0;
        for(InfoCliente i : this.clientes.values()){
            if(i.getNumeroTotalVendasMes(mes) != 0)
                total++;
        }
        return total;
    }
    
    
    public List<String> clientesNaoQueNaoEfetuaramCompras() {
        List<String> res=new ArrayList<>(); 
        TreeSet<String> aux = new TreeSet<>();
        for(InfoCliente i : this.clientes.values())
            if(i.getTotalCompras()==0 && i.getNumeroTotalCompras()==0) 
                aux.add(i.getCodigoCliente());
        res.addAll(aux);
        return res;
    }
    
    
    public List<String> produtosQueNaoFaramComprados() {
        List<String> res=new ArrayList<>(); 
        TreeSet<String> aux = new TreeSet<>();
        for(InfoProduto i : this.produtos.values())
            if(i.getTotalVendas()==0 && i.getNumeroTotalVendas()==0) 
                aux.add(i.getCodigoProduto());
        res.addAll(aux);
        return res;
    }
    
    public int totalComprasClientesMes(String c, int mes) throws CodigoInexistenteException{
        if(this.clientes.containsKey(c))
            return this.clientes.get(c).getNumeroTotalVendasMes(mes);
        else
            throw new CodigoInexistenteException(c);
    }
    
    public int totalComprasProdutoMes(String c, int mes) throws CodigoInexistenteException{
        if(this.produtos.containsKey(c))
            return this.produtos.get(c).getNumeroTotalVendasMes(mes);
        else
            throw new CodigoInexistenteException(c);
    }
    
    public int totalProdutosDistintosCompradosMes(String c, int mes) throws CodigoInexistenteException {
        if(this.clientes.containsKey(c))
            return this.clientes.get(c).totalProdutosDistintosCompradosMes(mes);
        else
            throw new CodigoInexistenteException(c);
    }
    
    public int totalClientesDistintosCompraramMes(String c, int mes) throws CodigoInexistenteException{
        if(this.produtos.containsKey(c))
            return this.produtos.get(c).totalClientesDistintosMes(mes);
        else
            throw new CodigoInexistenteException(c);
    }
    
    public float totalGastoMesPorCliente(String c, int mes) throws CodigoInexistenteException{
        if(this.clientes.containsKey(c))
            return this.clientes.get(c).totalGastoMes(mes);
        else
            throw new CodigoInexistenteException(c);
    }
    
    public float totalFaturadoMesProduto(String c, int mes) throws CodigoInexistenteException{
        if(this.produtos.containsKey(c))
            return this.produtos.get(c).totalFaturadoMes(mes);
        else
            throw new CodigoInexistenteException(c);
    }
  
    public List<Par_CodQuant> topComprasCliente(String c) throws CodigoInexistenteException{
        if(this.clientes.containsKey(c))
            return this.clientes.get(c).getTopCompras();
        else
            throw new CodigoInexistenteException(c);
    }
    
    public List<Par_CodQuant> topXProdutosComprados(int top){
        List<Par_CodQuant> l = new ArrayList<>();
        ArrayList<Par_CodQuant> aux2 = new ArrayList<>();
        TreeSet<Par_CodQuant> ordena = new TreeSet<>(new Par_CodQuantCOMPARATOR());
        for(InfoProduto i : this.produtos.values()){
                ordena.add(new Par_CodQuant(i.getCodigoProduto(),i.getTotalVendas()));
        }
        for(Par_CodQuant p : ordena)
              aux2.add(p.clone());
        for (int x = 0; x<aux2.size() && x<top ; x++) {
            l.add(aux2.get(x).clone());
        }
        return l;
    }
    
    
    public List<Par_CodQuant> topXClientesMaisProdutosDistintosComprados(int top){
        List<Par_CodQuant> l = new ArrayList<>();
        ArrayList<Par_CodQuant> aux2 = new ArrayList<>();
        TreeSet<Par_CodQuant> ordena = new TreeSet<>(new Par_CodQuantCOMPARATOR());
        int min = 0;
        
        for(InfoCliente i : this.clientes.values())
            ordena.add(new Par_CodQuant(i.getCodigoCliente(),i.totalProdutosDistintosComprados()));
    
        for(Par_CodQuant p : ordena)
            aux2.add(p.clone());
        
        for (int x = 0; x<aux2.size() && x<top ; x++) {
            l.add(aux2.get(x).clone());
        }
        return l;
    }
    
    public List<Trio_CodQuantFat> topXCompradoresDeProduto(String cod, int top) throws CodigoInexistenteException{
        if(this.produtos.containsKey(cod))
            return this.produtos.get(cod).topXCompradores(top);
        else
            throw new CodigoInexistenteException(cod);
    }
    
      
    public VendaAnual registoAnualProduto(String cod) throws CodigoInexistenteException{
        if(this.produtos.containsKey(cod)){
            VendaAnual teste =  this.produtos.get(cod).getRegistoVendas();
            return teste;
        }
        else
            throw new CodigoInexistenteException(cod);
        
    }
    
    
    public EstatisticaAnual getEstatisticaAnualCliente(String cod) throws CodigoInexistenteException {
        if (!(this.clientes.containsKey(cod))) {
            throw new CodigoInexistenteException(cod);
        } else {
            EstatisticaAnual est = new EstatisticaAnual(cod);
            InfoCliente inf = this.clientes.get(cod);
            est.setRegistoCompras(inf.getRegistoCompras().clone());
            for (int i = 1; i <= 12; i++) {
                est.adicionaDistintos(i, inf.totalProdutosDistintosCompradosMes(i));
            }
            return est.clone();
        }
    }
    
    public EstatisticaAnual getEstatisticaAnualProduto(String cod) throws CodigoInexistenteException {
        if (!(this.produtos.containsKey(cod))) {
            throw new CodigoInexistenteException(cod);
        } else {
            EstatisticaAnual est = new EstatisticaAnual(cod);
            InfoProduto inf = this.produtos.get(cod);
            est.setRegistoCompras(inf.getRegistoVendas());
            for (int i = 1; i <= 12; i++) {
                est.adicionaDistintos(i, inf.totalClientesDistintosMes(i));
            }
            return est.clone();
        }
    }

    public void clearCompras(){
        this.clientes.clear();
        this.produtos.clear();
    }

    
    

}



