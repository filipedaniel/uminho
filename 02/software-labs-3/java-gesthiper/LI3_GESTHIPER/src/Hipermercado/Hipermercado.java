package Hipermercado;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author fdr
 * Class que gere toda a informação do Hipermercado
 */
public class Hipermercado implements Serializable, HipermercadoINTERFACE{
 
    private Catalogo catalogoClientes;
    private Catalogo catalogoProdutos;
    private Contabilidade contabilidade;
    private Compras compras;

    
    public Hipermercado() {
        this.catalogoClientes = new Catalogo();
        this.catalogoProdutos = new Catalogo();
        this.contabilidade = new Contabilidade();
        this.compras = new Compras();
    }

    public Catalogo getCatalogoClientes() {
        return this.catalogoClientes.clone();
    }

    public Catalogo getCatalogoProdutos() {
        return this.catalogoProdutos.clone();
    }

    public Contabilidade getContabilidade() {
        return this.contabilidade.clone();
    }

    public Compras getCompras() {
        return this.compras.clone();
    }

    public void setClientes(Catalogo c) {
        this.catalogoClientes = c.clone();
    }

    public void setProdutos(Catalogo c) {
        this.catalogoProdutos = c.clone();
    }

    public void setContabilidade(Contabilidade c) {
        this.contabilidade = c.clone();
    }

    public void setCompras(Compras c) {
        this.compras = c.clone();
    }

    /**
     * Insere informação de Cliente no Hipermecado
     */
    public void insertCodigoCliente(String c) {
        this.catalogoClientes.insertCodigo(c);
        this.compras.insertCodigoCliente(c);
    }

    /**
     * Insere informação de Produto no Hipermecado
     */
    public void insertCodigoProduto(String c) {
        this.catalogoProdutos.insertCodigo(c);
        this.compras.insertCodigoProduto(c);
        this.contabilidade.inserirCodigoProduto(c);
    }

    /**
     * Regista informação de Compra no Hipermecado
     */
    public void registerSale(RegistoCompra c) {
        this.contabilidade.registarVenda(c.clone());
        this.compras.registerSale(c.clone());
    }

    //**********************************************************************
    
    public int getTotalClientes(){
        return this.catalogoClientes.getTotalCodigos();
    }
    
    public int totalDiferentesProdutosComprados(){
        return this.compras.totalDiferentesProdutosQueForamCompras();
    }
            
    public int totalDiferentesClientesQueEfetuaramCompras(){
        return this.compras.totalDiferentesClientesQueEfetuaramCompras();
    }        
    
    public float getFaturacaoTotal(){
        return this.contabilidade.faturacaoTotal();
    }
    
    public int totalComprasMes(int mes){
        return this.contabilidade.totalComprasPorMes(mes);
    }
    
    public float totalFaturadoMes(int mes){
        return this.contabilidade.totalFaturadoPorMes(mes);
    }
    
    public int totalClientesDistintosEfetuaramCompras(int mes){
        return this.compras.totalClientesDistintosEfetuaramCompras(mes);
    }
    
    public List<String> produtosQueNaoFaramComprados(){
        return this.compras.produtosQueNaoFaramComprados();
    }
    
    public List<String> clientesNaoQueNaoEfetuaramCompras(){
        return this.compras.clientesNaoQueNaoEfetuaramCompras();
    }
    
    public int totalNumeroComprasPorMes(int mes){
        return this.contabilidade.totalNumeroComprasPorMes(mes);
    }
    
    public int totalNumeroComprasClientePorMes(String c, int mes) throws CodigoInexistenteException{
        return this.compras.totalComprasClientesMes(c, mes);
    }
    
    public int totalNumeroComprasProdutoPorMes(String c, int mes) throws CodigoInexistenteException{
        return this.compras.totalComprasProdutoMes(c, mes);
    }
    
    public int totalProdutosDistintosCompradosMes(String c, int mes) throws CodigoInexistenteException{
        return this.compras.totalProdutosDistintosCompradosMes(c, mes);
    }
    
    public int totalClientesDistintosCompradosMes(String c, int mes) throws CodigoInexistenteException{
        return this.compras.totalClientesDistintosCompraramMes(c, mes);
    }
    
    public float totalGastoMesPorCliente(String c, int mes) throws CodigoInexistenteException{
        return this.compras.totalGastoMesPorCliente(c,mes);
    }
    
    public float totalFaturadoMesPorProduto(String c, int mes) throws CodigoInexistenteException{
        return this.compras.totalFaturadoMesProduto(c, mes);
    }
    
    public boolean existeCodigoProduto(String c){
        return this.catalogoProdutos.codigoExiste(c);
    }
    
    public boolean existeCodigoCliente(String c){
        return this.catalogoClientes.codigoExiste(c);
    }
    
    // Q4
    public EstatisticaAnual getEstatisticaAnualCliente(String c) throws CodigoInexistenteException{
        return this.compras.getEstatisticaAnualCliente(c);
    }
    
    //Q5
    public EstatisticaAnual getEstatisticaAnualProduto(String c) throws CodigoInexistenteException{
        return this.compras.getEstatisticaAnualProduto(c);
    }
    
    // Q6
    public Trio_InfoNumCompras infoNumeroComprasProdutoMes(String s, int mes) throws CodigoInexistenteException{
        return this.contabilidade.infoNumeroComprasProdutoMes(s, mes);
    }
    
    // Q7
    public List<Par_CodQuant> topComprasCliente(String cod) throws CodigoInexistenteException{
        return this.compras.topComprasCliente(cod);
    }
    
    public List<Par_CodQuant> topXProdutosComprados(int top){
        return this.compras.topXProdutosComprados(top);
    }
    
    public List<Par_CodQuant> topXClientesMaisProdutosDistintosComprados(int top){
        return this.compras.topXClientesMaisProdutosDistintosComprados(top);
    }
    
    public List<Trio_CodQuantFat> topXCompradoresDeProduto(String cod, int top) throws CodigoInexistenteException{
        return this.compras.topXCompradoresDeProduto(cod, top);
    }
    
    public VendaAnual registoAnualProduto(String cod) throws CodigoInexistenteException{
        return this.compras.registoAnualProduto(cod);
    }
    
    /**
     * Limpa todos os registos do Hipermecado
     */
    public Hipermercado clearMercado(){
        this.compras.clearCompras(); this.compras = null;
        this.contabilidade.clearContabilidade(); this.contabilidade = null;
        this.catalogoClientes.clearCatalogo(); this.catalogoClientes = null;
        this.catalogoProdutos.clearCatalogo(); this.catalogoProdutos = null;
        return new Hipermercado();
    }
    
    
    
}
