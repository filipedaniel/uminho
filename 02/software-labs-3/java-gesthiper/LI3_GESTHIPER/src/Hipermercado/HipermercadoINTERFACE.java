package Hipermercado;

import java.util.List;

/**
 * Interface Class Hipermercado
 * @author fdr
 */
public interface HipermercadoINTERFACE {
    
    public void insertCodigoCliente(String c);
    public void insertCodigoProduto(String c);
    public void registerSale(RegistoCompra c);

    
    public int getTotalClientes();
    public int totalDiferentesProdutosComprados();
    public int totalDiferentesClientesQueEfetuaramCompras();
    public float getFaturacaoTotal();
    public int totalComprasMes(int mes);
    public float totalFaturadoMes(int mes);
    public int totalClientesDistintosEfetuaramCompras(int mes);
    public List<String> produtosQueNaoFaramComprados();
    public List<String> clientesNaoQueNaoEfetuaramCompras();
    public int totalNumeroComprasPorMes(int mes);
    public int totalNumeroComprasClientePorMes(String c, int mes) throws CodigoInexistenteException;
    public int totalNumeroComprasProdutoPorMes(String c, int mes) throws CodigoInexistenteException;
    public int totalProdutosDistintosCompradosMes(String c, int mes) throws CodigoInexistenteException;
    public int totalClientesDistintosCompradosMes(String c, int mes) throws CodigoInexistenteException;
    public float totalGastoMesPorCliente(String c, int mes) throws CodigoInexistenteException;
    public float totalFaturadoMesPorProduto(String c, int mes) throws CodigoInexistenteException;
    public boolean existeCodigoProduto(String c);
    
    public boolean existeCodigoCliente(String c);
    
    public EstatisticaAnual getEstatisticaAnualCliente(String c) throws CodigoInexistenteException;
    public EstatisticaAnual getEstatisticaAnualProduto(String c) throws CodigoInexistenteException;
    public Trio_InfoNumCompras infoNumeroComprasProdutoMes(String s, int mes) throws CodigoInexistenteException;
    public List<Par_CodQuant> topComprasCliente(String cod) throws CodigoInexistenteException;
    public List<Par_CodQuant> topXProdutosComprados(int top);
    public List<Par_CodQuant> topXClientesMaisProdutosDistintosComprados(int top);
    public List<Trio_CodQuantFat> topXCompradoresDeProduto(String cod, int top) throws CodigoInexistenteException;
    public VendaAnual registoAnualProduto(String cod) throws CodigoInexistenteException;
    
    public Hipermercado clearMercado();
    
}
