package Hipermercado;

import java.io.Serializable;

/**
 * @author fdr
 * Regista as total de compras compras, bem como distinguindo tipo normal e promoção 
 */
public class Trio_InfoNumCompras {
    
    private int nCompras;
    private int tipoN;
    private int tipoP;

    
    public Trio_InfoNumCompras(){
        this.nCompras = 0;
        this.tipoN = 0;
        this.tipoP = 0;
    }
    
    public Trio_InfoNumCompras(int nCompras, int tipoN, int tipoP) {
        this.nCompras = nCompras;
        this.tipoN = tipoN;
        this.tipoP = tipoP;
    }

    public Trio_InfoNumCompras(Trio_InfoNumCompras t){
        this.nCompras = t.getnCompras();
        this.tipoN = t.getTipoN();
        this.tipoP = t.getTipoP();
    }
    
    
    public int getnCompras() {
        return this.nCompras;
    }
    public int getTipoN() {
        return this.tipoN;
    }
    public int getTipoP() {
        return this.tipoP;
    }
    
    public void setnCompras(int nCompras) {
        this.nCompras = nCompras;
    }
    public void setTipoN(int tipoN) {
        this.tipoN = tipoN;
    }
    public void setTipoP(int tipoP) {
        this.tipoP = tipoP;
    }

    
    public boolean equals(Trio_InfoNumCompras obj) {
        if (this == obj) {return true;}
        if ((obj == null) || (getClass() != obj.getClass())) { return false; }
        
        final Trio_InfoNumCompras other = (Trio_InfoNumCompras) obj;
        if (this.nCompras != other.nCompras) {
            return false;
        }
        if (this.tipoN != other.tipoN) {
            return false;
        }
        if (this.tipoP != other.tipoP) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("     Numero Compras: ").append(this.nCompras).append("\n");
        sb.append("         Normal    : ").append(this.tipoN).append("\n");
        sb.append("         Promoção  : ").append(this.tipoP).append("\n");
        return sb.toString();

    }
    
    public Trio_InfoNumCompras clone(){
        return new Trio_InfoNumCompras(this);
    }

}
