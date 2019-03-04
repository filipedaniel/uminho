package Hipermercado;


/**
 *
 * @author fdr
*  Informação sobre registo de Compra
*/
public class RegistoCompra {
    
    private String codigoProduto;
    private float valorUnitario;
    private int quantidade;
    private char tipo;
    private String codigoCliente;
    private int mes;

   public RegistoCompra() {
        this.codigoProduto = "";
        this.valorUnitario = 0.0f;
        this.quantidade = 0;
        this.tipo = 'N';
        this.codigoCliente = "";
        this.mes = 0;
    }
    
    public RegistoCompra(String codigoProduto, float valorUnitario, int quantidade, char tipo, String codigoCliente, int mes) {
        this.codigoProduto = codigoProduto;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.codigoCliente = codigoCliente;
        this.mes = mes;
    }

    public RegistoCompra(RegistoCompra r) {
        this.codigoProduto = r.getCodigoProduto();
        this.valorUnitario = r.getValorUnitario();
        this.quantidade = r.getQuantidade();
        this.tipo = r.getTipo();
        this.codigoCliente = r.getCodigoCliente();
        this.mes = r.getMes();
    }
    
    // GET's
    public String getCodigoProduto() {
        return this.codigoProduto;
    }
    public float getValorUnitario() {
        return this.valorUnitario;
    }
    public int getQuantidade() {
        return this.quantidade;
    }
    public char getTipo() {
        return this.tipo;
    }
    public String getCodigoCliente() {
        return this.codigoCliente;
    }
    public int getMes() {
        return this.mes;
    }
    
    //SET's
    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }
    public void setValorUnitario(float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public void setTipo(char tipo) {
        this.tipo = tipo;
    }
    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }
    public void setMes(int mes) {
        this.mes = mes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Código Produto: ").append(this.codigoProduto);
        sb.append("Valor Unitário: ").append(this.valorUnitario);
        sb.append("Quantidade    : ").append(this.quantidade);
        sb.append("Tipo          : ").append(this.tipo);
        sb.append("Código Cliente: ").append(this.codigoCliente);
        sb.append("Mês           : ").append(this.mes);
        return sb.toString();
    }


    public boolean equals(RegistoCompra obj) {
        if (this == obj) {return true;}
        if ((obj == null) || (getClass() != obj.getClass())) { return false; }
        else {
            RegistoCompra c = (RegistoCompra) obj;
            return (this.codigoProduto.equals(c.getCodigoProduto()) && 
                    this.valorUnitario == c.getValorUnitario() &&
                    this.quantidade == c.getQuantidade() && 
                    this.tipo == c.getTipo() && 
                    this.codigoCliente.equals(c.getCodigoCliente()) &&
                    this.mes == c.getMes());
        }
    }
    
    public RegistoCompra clone(){
        return new RegistoCompra(this);
    }
 
    
    
    
    
}
