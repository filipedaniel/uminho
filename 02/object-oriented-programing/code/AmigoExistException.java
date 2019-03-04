
/**
 * Classe excepção que é activida quando de alguma forma se quer inserir um amigo num conjunto de Amigos sendo ele já existente.
 * 
 */
public class AmigoExistException extends Exception
{
    /**
     * Constroi uma exceção com uma mensagem nula 
     */
    public AmigoExistException() {super();}
    
    /**
    * Constroi uma exceção com uma mensagem s especifica 
    */
    public AmigoExistException(String s) {super(s);} 
}
