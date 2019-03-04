package Hipermercado;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author fdr
 * Toda a Contabilidade do Hipermecado
 */
public class Contabilidade implements Serializable{
    
    private HashMap<String, VendaAnual> contabilidade;
    //private TreeMap<String, VendaAnual> contabilidade;

    public Contabilidade() {
        this.contabilidade = new HashMap<>();
    }
    
    public Contabilidade(HashMap<String, VendaAnual> contabilidade){
        this.contabilidade = new HashMap<>();
        for(VendaAnual v: contabilidade.values()){
            this.contabilidade.put(v.getCodigo(), v.clone());
        }            
    }

    public Contabilidade(Contabilidade c){
        this.contabilidade = c.getContabilidade();
    }
    
    public HashMap<String, VendaAnual> getContabilidade(){
        HashMap<String, VendaAnual> r = new HashMap<>();
        for(VendaAnual v: this.contabilidade.values()){
            r.put(v.getCodigo(), v.clone());
        }
        return r;
    }

    public void setContabilidade(HashMap<String, VendaAnual> contabilidade){
        this.contabilidade = new HashMap<>();
        for(VendaAnual v: contabilidade.values()){
            this.contabilidade.put(v.getCodigo(),v.clone());
        }
    }
    
    public void inserirCodigoProduto(String codigo) {
        this.contabilidade.put(codigo, new VendaAnual(codigo));
    }

    public void registarVenda(RegistoCompra r) {
        //if(!this.contabilidade.containsKey(r.getCodigoProduto()))
        //   this.contabilidade.put(r.getCodigoProduto(), new VendaAnual(r.getCodigoProduto()));
        this.contabilidade.get(r.getCodigoProduto()).registar(r);
        
    }
    
    @Override
    public Contabilidade clone(){
        return new Contabilidade(this);
    }
    
    
    public float faturacaoTotal(){
        float total = 0.0f;
        for(VendaAnual v : contabilidade.values())
            total += v.getTotalAnualFaturado();
        return total;
    }
    
    
    public int totalComprasPorMes(int mes){
        int total = 0;
        for(VendaAnual v : this.contabilidade.values())
            total += v.getNumeroTotalVendasMes(mes);
        return total;
    }
    
    
    public float totalFaturadoPorMes(int mes){
        float total = 0.0f;
        for(VendaAnual v : this.contabilidade.values())
            total += v.getTotalFaturacaoMes(mes);
        return total;
    }
    
    public int totalNumeroComprasPorMes(int mes){
        int total = 0;
        for(VendaAnual v : this.contabilidade.values())
            total += v.getNumeroTotalVendasMes(mes);
        return total;
    }
  
    // Para um mes: numero de Vendas total, Normal e Promoção 
    public Trio_InfoNumCompras infoNumeroComprasProdutoMes(String s, int mes) throws CodigoInexistenteException{
        Trio_InfoNumCompras inf = new Trio_InfoNumCompras();
        if(this.contabilidade.containsKey(s)){
            inf.setnCompras(this.contabilidade.get(s).getNumeroTotalVendasMes(mes));
            inf.setTipoN(this.contabilidade.get(s).getNumeroVendasNormalMes(mes));
            inf.setTipoP(this.contabilidade.get(s).getNumeroVendasPromocaoMes(mes));
        }
        else
            throw new CodigoInexistenteException(s);
        
        return inf;
    }

    public void clearContabilidade(){
        this.contabilidade.clear();
    }
    
    
    
}
