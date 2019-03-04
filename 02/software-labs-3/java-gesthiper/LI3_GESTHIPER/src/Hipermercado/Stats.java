package Hipermercado;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author fdr
 * Informação de um Cliente/Produto
 */
public class Stats implements Serializable{
    
    private String codigo;
    private VendaAnual stats;

    
    public Stats(){
        this.codigo = "";
        this.stats = new VendaAnual();
    }
    
    public Stats(String codigo) {
        this.codigo = codigo;
        this.stats = new VendaAnual(codigo);
    }
    
    public Stats(Stats cp){
        this.codigo = cp.getCodigo();
        this.stats = cp.getStats();
    }
    
    public String getCodigo() {
        return this.codigo;
    }
    public VendaAnual getStats() {
        return stats.clone();
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public void setStats(VendaAnual stats) {
        this.stats = stats;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Stats other = (Stats) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.stats, other.stats)) {
            return false;
        }
        return true;
    }

   
    public Stats clone(){
        return new Stats(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Codigo: ").append(this.codigo);
        sb.append(this.stats.toString());
        return sb.toString();
    }
    
    //**************************************************************************
    
    // Existe compras de total de vendas e total de compras e numero total de
    // compras for 0;
    public boolean existeComprasMes(int mes){
        return ((this.stats.getTotalVendasMes(mes) != 0) && 
                (this.stats.getNumeroTotalVendasMes(mes) ) != 0);
    }
    
    
    /*TOTAL DE COMPRAS*/
    public int getTotalComprasMes(int mes){
        return this.stats.getNumeroTotalVendasMes(mes);
    }
    
    public int getTotalComprasMesNormal(int mes){
        return this.stats.getTotalVendasMesNormal(mes);
    }
    
    public int getTotalComprasMesPromocao(int mes){
        return this.stats.getTotalVendasMesPromocao(mes);
    }
    
    public int getTotalAnualCompras(){
        return this.stats.getTotalAnualVendas();
    }
    
   
    /*TOTAL DE FATURADO*/
    public float getTotalFaturadoMes(int mes){
        return this.stats.getTotalFaturacaoMes(mes);
    }
    
    public float getTotalFaturadoNormalMes(int mes){
        return this.stats.getTotalFaturacaoNormalMes(mes);
    }
    
    public float getTotalFaturadoPromocaoMes(int mes){
        return this.stats.getTotalFaturacaoPromocaoMes(mes);
    }
    
    public float getTotalAnualFaturado(){
        return this.stats.getTotalAnualFaturado();
    }
    
    
    /*NUMERO DE VENDAS*/
    public int getNumeroTotalVendasMes(int mes) {
        return this.stats.getNumeroTotalVendasMes(mes);
    }
    
    public int getNumeroTotalVendasNormalMes(int mes) {
        return this.stats.getNumeroVendasNormalMes(mes);
    }
    
    public int getNumeroTotalVendasPromocaoMes(int mes) {
        return this.stats.getNumeroVendasPromocaoMes(mes);
    }
    
    public int getNumeroAnualVendas(){
        return this.stats.getNumeroAnualVendas();
    }
    
    
    /*UPDATE VALORES*/
    /**
     * Regista Compra
     */
    public void updateStats(RegistoCompra r){
        this.stats.registar(r);
    }
    
}
