package Hipermercado;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author fdr
 * Registo anual de Compras/Vendas de produtos
 */
public class VendaAnual implements Serializable{
    
    private String codigo;
    private VendaMensal[] vendas;

    public VendaAnual() {
        this.codigo = "";
        int i = 0;
        this.vendas = new VendaMensal[12];
        for(i = 0; i<12 ; i++)
            vendas[i] = new VendaMensal();
    }
    
    public VendaAnual(String codigo) {
        this.codigo = codigo;
        this.vendas = new VendaMensal[12];
        int i = 0;
        for(i=0;i<12;i++)
            vendas[i] = new VendaMensal();
    }

    public VendaAnual(VendaAnual va){
        this.codigo = va.getCodigo();
        this.vendas = va.getVendas();
    }
    
    // GET's
    public String getCodigo() {
        return this.codigo;
    }
    
    public VendaMensal[] getVendas(){
        VendaMensal[] res = new VendaMensal[12];
        int i = 0;
        while(i<12){
            res[i] = this.vendas[i].clone();
            i++;
        }
        return res;
    }
    
    //SET's
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public void setVendas(VendaMensal[] v) {
        this.vendas = new VendaMensal[12];
        int i = 0;
        while(i<12){
            this.vendas[i] = v[i].clone();
            i++;
        }
    }
    
   
    
    public boolean equals(VendaAnual obj) {
        if (this == obj) {return true;}
        if ((obj == null) || (getClass() != obj.getClass())) { return false; }
        
        final VendaAnual other = (VendaAnual) obj;
        if (!(this.codigo.equals(other.codigo))) {
            return false;
        }
        if (!Arrays.deepEquals(this.vendas, other.vendas)) {
            return false;
        }
        return true;
    }

    @Override
    public VendaAnual clone(){
        return new VendaAnual(this);
    }
    
   @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i;
        sb.append("Vendas Anuais:\n\n");
        for(i=0;i<12;i++){
            sb.append("\n\nMês: ").append(i);
            sb.append(this.vendas[i].toString());
        }
        return sb.toString();
    }

    
    //Total de vendas
    
    public boolean existeVendas(int mes){
        return (this.vendas[mes-1].getTotalVendas() != 0);
    }
    
    public int getTotalVendasMes(int mes) {
        return this.vendas[mes-1].getTotalVendas();
    }
    
    public int getTotalVendasMesNormal(int mes) {
        return this.vendas[mes-1].getVendasNormal();
    }
    
    public int getTotalVendasMesPromocao(int mes){
        return this.vendas[mes-1].getVendasPromocao();
    }

    public int getTotalAnualVendas(){
        int i;
        int total = 0;
        for(i=0;i<12;i++)
            total += this.vendas[i].getTotalVendas();
        return total;
    }
    
    
    //Total Faturado
    
    public float getTotalFaturacaoMes(int mes) {
        return this.vendas[mes-1].getTotalFaturado();
    }
    
    public float getTotalFaturacaoNormalMes(int mes) {
        return this.vendas[mes-1].getFaturadoNormal();
    }
    
    public float getTotalFaturacaoPromocaoMes(int mes) {
        return this.vendas[mes-1].getFaturadoPromocao();
    }

    public float getTotalAnualFaturado(){
        int i;
        float total = 0.0f;
        for(i=0;i<12;i++)
            total += this.vendas[i].getTotalFaturado();
        return total;
    }
    
    //Numero de Vendas
    
    public int getNumeroTotalVendasMes(int mes) {
        return this.vendas[mes-1].getNumeroTotalVendas();
    }
    
    public int getNumeroVendasNormalMes(int mes) {
        return this.vendas[mes-1].getNumeroVendasNormal();
    }
    
    public int getNumeroVendasPromocaoMes(int mes) {
        return this.vendas[mes-1].getNumeroVendasPromocao();
    }

    public int getNumeroAnualVendas(){
        int i;
        int total = 0;
        for(i=0;i<12;i++)
            total += this.vendas[i].getNumeroTotalVendas();
        return total;
    }
    
    
    //Registar uma Compra
    /**
     * Regista uma Compra
     */
    public void registar(RegistoCompra r){
        if(r.getTipo() == 'N'){
            this.vendas[r.getMes()-1].updateVendasNormal(r.getQuantidade());
            this.vendas[r.getMes()-1].updateFaturadoNormal(r.getQuantidade(),r.getValorUnitario());
            this.vendas[r.getMes()-1].incrementaNumeroVendasNormal();
        }
        else{
            this.vendas[r.getMes()-1].updateVendasPromocao(r.getQuantidade());
            this.vendas[r.getMes()-1].updateFaturadoPromocao(r.getQuantidade(),r.getValorUnitario());
            this.vendas[r.getMes()-1].incrementaNumeroVendasPromocao();
        }
    }
    
}
