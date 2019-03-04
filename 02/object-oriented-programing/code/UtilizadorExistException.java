
/**
 * Classe excepção que é activida quando de alguma forma se quer inserir um Utilizador num conjunto de utilizadores sendo ele já existente.
 * 
 */
public class UtilizadorExistException extends Exception
{
    /**
     * Constroi uma exceção com uma mensagem nula 
     */
    public UtilizadorExistException() {super();}
    
    /**
    * Constroi uma exceção com uma mensagem s especifica 
    */
    public UtilizadorExistException(String s) {super(s);} 
}
