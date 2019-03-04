
/**
 * Classe excepção que é activida quando de alguma forma se quer inserir uma actividade num conjunto de actividades sendo ela já existente.
 * 
 */
public class ActividadeExistException extends Exception
{
   /**
     * Constroi uma exceção com uma mensagem nula 
     */
    public ActividadeExistException() {super();}
   
   /**
    * Constroi uma exceção com uma mensagem s especifica 
    */
   public ActividadeExistException(String s) {super(s);} 
}
