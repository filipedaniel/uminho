package Hipermercado;

import java.io.Serializable;

/**
 * @author fdr
 * Registo mensal de Compras/Vendas de produtos
 */
public class VendaMensal implements Serializable{
    
    private int vendasNormal;
    private int vendasPromocao;
    private float faturadoNormal;
    private float faturadoPromocao;
    private int numeroVendasNormal;
    private int numeroVendasPromocao;
    
    public VendaMensal() {
        this.vendasNormal = 0;
        this.vendasPromocao = 0;
        this.faturadoNormal = 0.0f;
        this.faturadoPromocao = 0.0f;
        this.numeroVendasNormal = 0;
        this.numeroVendasPromocao = 0;
    }

    public VendaMensal(int vendasNormal, int vendasPromocao, float faturadoNormal, float faturadoPromocao, int numeroVendasNormal, int numeroVendasPromocao) {
        this.vendasNormal = vendasNormal;
        this.vendasPromocao = vendasPromocao;
        this.faturadoNormal = faturadoNormal;
        this.faturadoPromocao = faturadoPromocao;
        this.numeroVendasNormal = numeroVendasNormal;
        this.numeroVendasPromocao = numeroVendasPromocao;
    }
    
    public VendaMensal(VendaMensal c){
        this.vendasNormal = c.getVendasNormal();
        this.vendasPromocao = c.getVendasPromocao();
        this.faturadoNormal = c.getFaturadoNormal();
        this.faturadoPromocao = c.getFaturadoPromocao();
        this.numeroVendasNormal = c.getNumeroVendasNormal();
        this.numeroVendasPromocao = c.getNumeroVendasPromocao();
    }

    //GET's
    public int getVendasNormal() { 
        return this.vendasNormal; 
    }
    public int getVendasPromocao() { 
        return this.vendasPromocao; 
    }
    public float getFaturadoNormal() {
        return this.faturadoNormal;
    }
    public float getFaturadoPromocao() {
        return this.faturadoPromocao;
    }
    public int getNumeroVendasNormal() {
        return this.numeroVendasNormal;
    }
    public int getNumeroVendasPromocao() {
        return this.numeroVendasPromocao;
    }
    
    // SET+s
    public void setVendasNormal(int vendasNormal) { 
        this.vendasNormal = vendasNormal; 
    }
    public void setVendasPromocao(int vendasPromocao) {
        this.vendasPromocao = vendasPromocao;
    }
    public void setFaturadoNormal(float faturadoNormal) {
        this.faturadoNormal = faturadoNormal;
    }
    public void setFaturadoPromocao(float faturadoPromocao) {
        this.faturadoPromocao = faturadoPromocao;
    }
    public void setNumeroVendasNormal(int numeroVendasNormal) {
        this.numeroVendasNormal = numeroVendasNormal;
    }
    public void setNumeroVendasPromocao(int numeroVendasPromocao) {
        this.numeroVendasPromocao = numeroVendasPromocao;
    }


    @Override
    public VendaMensal clone(){
        return new VendaMensal(this);
    }

 
    public boolean equals(VendaMensal obj) {
        
        if(this == obj) return true;
        if ((obj == null) || (getClass() != obj.getClass())) { return false; }
            else{
                final VendaMensal other = (VendaMensal) obj;
                if (this.vendasNormal != other.vendasNormal) {
                    return false;
                }
                if (this.vendasPromocao != other.vendasPromocao) {
                    return false;
                }
                if (Float.floatToIntBits(this.faturadoNormal) != Float.floatToIntBits(other.faturadoNormal)) {
                    return false;
                }
                if(this.numeroVendasNormal != other.numeroVendasNormal){
                    return false;
                }
                if(this.numeroVendasPromocao != other.numeroVendasPromocao){
                    return false;
                }
                return Float.floatToIntBits(this.faturadoPromocao) == Float.floatToIntBits(other.faturadoPromocao);
            }
        
    }
    
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nTotal Vendas Normal: ");
        sb.append(this.vendasNormal);
        sb.append("\nTotal Vendas Promoção: ");
        sb.append(this.vendasPromocao);
        sb.append("\nTotal Faturado Normal: ");
        sb.append(this.faturadoNormal);
        sb.append("\nTotal Faturado Promoção: ");
        sb.append(this.faturadoPromocao);
        sb.append("\nNumero de Vendas tipo Normal: ");
        sb.append(this.numeroVendasNormal);
        sb.append("\nNumero de Vendas tipo Promoção: ");
        sb.append(this.numeroVendasPromocao);
        return sb.toString();
    }
    
    //*************************************************************************
    
    public int getTotalVendas(){
        return this.vendasNormal + this.vendasPromocao;
    }
    
    public float getTotalFaturado(){
        return this.faturadoNormal + this.faturadoPromocao;
    }
    
    public int getNumeroTotalVendas(){
        return this.numeroVendasNormal + this.numeroVendasPromocao;
    }
    
    // Updates
    public void updateVendasNormal(int vendas){
        this.vendasNormal += vendas;
    }
    
    public void updateVendasPromocao(int vendas){
        this.vendasPromocao += vendas;
    }
    
    public void updateFaturadoNormal(int vendas, float valor){
        this.faturadoNormal += (vendas * valor);
    }
    
    public void updateFaturadoPromocao(int vendas, float valor){
        this.faturadoPromocao += (vendas * valor);
    }
    
    public void incrementaNumeroVendasNormal(){
        this.numeroVendasNormal++;
    }
    
    public void incrementaNumeroVendasPromocao(){
        this.numeroVendasPromocao++;
    }

}
