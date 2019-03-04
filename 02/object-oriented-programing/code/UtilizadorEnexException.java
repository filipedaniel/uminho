
/**
 * Classe excepção que é activida quando de alguma forma se quer aceder a um Utilizador que não exista
 * 
 */
public class UtilizadorEnexException extends Exception 
{
    /**
     * Constroi uma exceção com uma mensagem nula 
     */
    public UtilizadorEnexException() {super();}
    
    /**
    * Constroi uma exceção com uma mensagem s especifica 
    */
    public UtilizadorEnexException(String s) {super(s);} 
}
