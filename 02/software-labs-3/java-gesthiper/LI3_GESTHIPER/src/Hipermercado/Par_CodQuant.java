package Hipermercado;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author fdr
 * Regista o par de Valores: Codigo e quantidade comprada/vendida
 */
public class Par_CodQuant {
    
    private String codigo;
    private int quantidade;

    
    public Par_CodQuant() {
        this.codigo = "";
        this.quantidade = 0;
    }

    public Par_CodQuant(String codigo, int quantidade) {
        this.codigo = codigo;
        this.quantidade = quantidade;
    }
    
    public Par_CodQuant(Par_CodQuant p){
        this.codigo=p.getCodigo();
        this.quantidade=p.getQuantidade();
    }
    
    //GET's
    public String getCodigo() {
        return this.codigo;
    }
    public int getQuantidade() {
        return quantidade;
    }

    //SET's
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    
    public Par_CodQuant clone(){
        return new Par_CodQuant(this);
    }

    
    public boolean equals(Par_CodQuant obj) {
        if (this == obj) {return true;}
        if ((obj == null) || (getClass() != obj.getClass())) { return false; }
        
        final Par_CodQuant other = (Par_CodQuant) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (this.quantidade != other.quantidade) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Codigo/Quantidade: ").append(this.codigo).append(" / ").append(this.quantidade);
        return sb.toString();
    }  
    
    
    
    
}
