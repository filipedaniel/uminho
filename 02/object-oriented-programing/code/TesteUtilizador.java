import java.util.Iterator;

/**
 * 
 * @author (your name) 
 */
public class TesteUtilizador
{
    public static Utilizador main(){

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
        catch(AmigoExistException ue){System.out.println("(3) Erro ao inserir Amigos na lista! - "+ ue.getMessage());}

        Actividades desp = new Actividades();

        try{
            desp.addActividade(new Futebol(11,new Data(11,11,2013),10,false,""));
            desp.addActividade(new Natacao(20,new Data(20,8,2013),60));
            desp.addActividade(new CardioFitness(9,new Data(14,2,2013),30));
        }
        catch(ActividadeExistException ue){System.out.println("(4) Erro ao inserir Actividades! - "+ ue.getMessage());}

        Utilizador user = new Utilizador("alberto@gmail.com","Alberto", "fitness", "M",new Data(1,10,1992),"Futebol",
                1.70,74.2,lista_amigos,desp);
        /*    
        // toString do Utilizador    
        System.out.println(user.toString());
         */
        //-------Listagem de Actividades ordenados pela data-------
        Iterator<Actividade> it = desp.ordenar().iterator();
        System.out.println("\n.............Actividades Realizadas..............");
        while (it.hasNext())
            System.out.println(it.next().toString());
        /*
        //--------Listagem de Amigos por ordem alabética----------
        Iterator<Amigo> i = user.getUAmigos().ordenaPorNome().values().iterator();
        System.out.println("\n .............Amigos..............");
        while (i.hasNext())
        System.out.println(i.next().toString());

         */          
        return user;        
    }

}