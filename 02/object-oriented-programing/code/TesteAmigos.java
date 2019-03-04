/**
 *
 * @author filipedaniel
 */
import java.util.Iterator;

public class TesteAmigos 
{
    public static Amigos main(){

        Amigos lista_amigos = new Amigos();

        Amigo a1 = new Amigo("f_dkr@gmail.com","Filipe");    
        Amigo a2 = new Amigo("ffiliperibeiro@gmail.com","Daniel");
        Amigo a3 = new Amigo("z_manel@gmail.com","Zé Manuel");
        Amigo a4 = new Amigo("xpto@hotmail.com","Artur");

        try{
            lista_amigos.addAmigo(a1);
            lista_amigos.addAmigo(a2);
            lista_amigos.addAmigo(a3);
            lista_amigos.addAmigo(a4);        
        }
        catch(AmigoExistException e){System.out.println("(4) Erro ao inserir Amigos na lista! - "+ e.getMessage());}

        /*
        // Amigos por ordenados alfabéticamente
        Iterator<Amigo> it = lista_amigos.ordenaPorNome().values().iterator();
        System.out.println(".............Teste Listagem de Amigos por ordem alabética..............");
        while (it.hasNext())
        System.out.println(it.next().toString());
         */

        try{
            lista_amigos.removeAmigo("ola");
        }         
        catch(AmigoEnexException e){System.out.println("(5) Erro ao remover Amigos na lista! - "+ e.getMessage());}

        // Amigos por ordenados alfabéticamente
        Iterator<Amigo> it = lista_amigos.ordenaPorNome().values().iterator();
        System.out.println(".............Teste Listagem de Amigos por ordem alabética..............");
        while (it.hasNext())
            System.out.println(it.next().toString());

        return lista_amigos;    
    }
}
