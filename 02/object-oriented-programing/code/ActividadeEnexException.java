
/**
 * Classe excepção que é activida quando de alguma forma se quer aceder a uma actividade que não existe
 * 
 */
public class ActividadeEnexException extends Exception
{
    /**
     * Constroi uma exceção com uma mensagem nula 
     */
    public ActividadeEnexException() {super();}
    
    /**
     * Constroi uma exceção com uma mensagem s especifica 
     */
    public ActividadeEnexException(String s) {super(s);}  
}
