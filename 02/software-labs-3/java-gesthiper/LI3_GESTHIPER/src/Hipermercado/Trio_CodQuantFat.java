/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hipermercado;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author fdr
 */
public class Trio_CodQuantFat {
    
    private String codigo;
    private int quantidade;
    private float faturacao;

    
    public Trio_CodQuantFat(){
        this.codigo = "";
        this.quantidade = 0;
        this.faturacao = 0.0f;
    }

    public Trio_CodQuantFat(String codigo, int quantidade, float faturacao) {
        this.codigo = codigo;
        this.quantidade = quantidade;
        this.faturacao = faturacao;
    }
    
    public Trio_CodQuantFat(Trio_CodQuantFat t){
        this.codigo = t.getCodigo();
        this.quantidade = t.getQuantidade();
        this.faturacao = t.getFaturacao();
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int faturacao) {
        this.quantidade = faturacao;
    }

    public float getFaturacao() {
        return faturacao;
    }

    public void setFaturacao(float faturacao) {
        this.faturacao = faturacao;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if ((obj == null) || (getClass() != obj.getClass())) { return false; }
        
        final Trio_CodQuantFat other = (Trio_CodQuantFat) obj;
        if (!(this.codigo.equals(other.codigo))) {
            return false;
        }
        if (this.quantidade != other.quantidade) {
            return false;
        }
        if (Float.floatToIntBits(this.faturacao) != Float.floatToIntBits(other.faturacao)) {
            return false;
        }
        return true;
    }

    
    @Override
    public Trio_CodQuantFat clone(){
        return new Trio_CodQuantFat(this);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Código       : ").append(this.codigo).append("\n");
        sb.append(" - Quantidade: ").append(this.quantidade).append("\n");
        sb.append(" - Faturação : ").append(this.faturacao).append("\n");
        return sb.toString();
    }

    

}
