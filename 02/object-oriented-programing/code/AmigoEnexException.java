
/**
 * Classe excepção que é activida quando de alguma forma se quer aceder a um Amigo que não exista
 * 
 */
public class AmigoEnexException extends Exception
{
    /**
     * Constroi uma exceção com uma mensagem nula 
     */
    public AmigoEnexException() {super();}
    
    /**
    * Constroi uma exceção com uma mensagem s especifica 
    */
    public AmigoEnexException(String s) {super(s);}  
}
