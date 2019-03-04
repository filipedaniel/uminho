package Hipermercado;

/**
 * @author fdr
 * Exceção caso o código não exista
 */
public class CodigoInexistenteException extends Exception {
    public CodigoInexistenteException(){
        super();
    }
    public CodigoInexistenteException(String ex){
        super(ex);
    }
}
