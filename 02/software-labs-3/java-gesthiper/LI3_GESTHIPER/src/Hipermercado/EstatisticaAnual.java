package Hipermercado;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author fdr
 * Estatistica anual sobre Compras
 */
public class EstatisticaAnual{
    
    private String codigo;
    private VendaAnual registoCompras;
    private int[] distintos;        //Numero distinto de Clientes/Produtos

    
    public EstatisticaAnual() {
        this.codigo = "";
        this.registoCompras = new VendaAnual();
        this.distintos = new int[12];
    }
    
    public EstatisticaAnual(String c){
        this.codigo = c;
        this.registoCompras = new VendaAnual();
        this.distintos = new int[12];
    }
    
    public EstatisticaAnual(EstatisticaAnual e){
        this.codigo = e.getCodigo();
        this.registoCompras = e.getRegistoCompras();
        this.distintos = e.getDistintos();
    }

    
    public String getCodigo() {
        return this.codigo;
    }
    public VendaAnual getRegistoCompras() {
        return this.registoCompras.clone();
    }
    public int[] getDistintos() {
        return this.distintos;
    }


    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public void setRegistoCompras(VendaAnual registoCompras) {
        this.registoCompras = registoCompras.clone();
    }
    public void setDistindos(int[] distintos) {
        this.distintos = distintos;
    }

    
    /**
     * Numero distintos de Clientes/Produtos num determinado mês
     */
    public void adicionaDistintos(int mes, int n) {
        this.distintos[mes-1] = n;
    }
    
    public EstatisticaAnual clone(){
        return new EstatisticaAnual(this);
        }
    
    
    @Override
    public String toString() {
        String[] meses = {"Janeiro","Fevereiro","Março","Abril","Maio",
                            "Junho", "Julho","Agosto","Setembro",
                            "Outubro","Novembro","Dezembro"};
        StringBuilder sb = new StringBuilder();
        sb.append("Código: " ).append(this.codigo).append("\n");
        for(int i = 1;i<=12;i++){
            sb.append(meses[i-1]).append("\n");
            sb.append(" - Numero distinto : ").append(this.distintos[i-1]).append("\n");
            sb.append(" - Total de Compras: ").append(this.registoCompras.getTotalVendasMes(i)).append("\n");
            sb.append(" - Total Faturado  : ").append(this.registoCompras.getTotalFaturacaoMes(i)).append(" €").append("\n");
        }
        sb.append("Total anual Faturado : ").append(this.registoCompras.getTotalAnualFaturado()).append(" €").append("\n");
        return sb.toString();
    }

    

    
    public boolean equals(EstatisticaAnual obj) {
        if (this == obj) {return true;}
        if ((obj == null) || (getClass() != obj.getClass())) { return false; }
        final EstatisticaAnual other = (EstatisticaAnual) obj;
        if (!(this.codigo.equals(other.codigo))) {
            return false;
        }
        if (!(this.registoCompras.equals(other.registoCompras))) {
            return false;
        }
        return Arrays.equals(this.distintos, other.distintos);
    }
    
}
