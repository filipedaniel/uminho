import java.util.Iterator;

/**
 * 
 * @author (your name) 
 */
public class TesteUtilizadores
{
    public static Utilizadores main(){

        Amigos amigos1 = new Amigos();

        Amigo a1 = new Amigo("f_dkr@gmail.com","Filipe");     
        Amigo a2 = new Amigo("ffiliperibeiro@gmail.com","Daniel");
        Amigo a3 = new Amigo("z_manel@gmail.com","Zé Manuel");
        Amigo a4 = new Amigo("xpto@hotmail.com","Artur");

        try{
            amigos1.addAmigo(a1);
            amigos1.addAmigo(a2);
            amigos1.addAmigo(a3);
            amigos1.addAmigo(a4);        
        }
        catch(AmigoExistException ae){System.out.println("(1) Erro ao inserir Amigos! - "+ ae.getMessage());}

        Actividades desp1 = new Actividades();

        try{
            desp1.addActividade(new Andebol(1,new Data(11,11,2013),10,false,""));
            desp1.addActividade(new Hidroginastica(2,new Data(20,8,2013),60));
        }
        catch(ActividadeExistException ae){System.out.println("(1) Erro ao inserir Actividades! - "+ ae.getMessage());}

        Utilizador user1 = new Utilizador("alberto@gmail.com","Alberto", "fitness", "M",new Data(1,10,1992),"Futebol",
                1.70,74.2,amigos1,desp1);

        //-----------------------------------------------------------------

        Amigos amigos2 = new Amigos();

        Amigo aa1 = new Amigo("pedro@gmail.com","Pedro");     
        Amigo aa2 = new Amigo("joao@gmail.com","João");
        Amigo aa3 = new Amigo("antoniol@gmail.com","Antonio");
        Amigo aa4 = new Amigo("xavier@hotmail.com","Xavier");

        try{
            amigos2.addAmigo(aa1);
            amigos2.addAmigo(aa2);
            amigos2.addAmigo(aa3);
            amigos2.addAmigo(aa4);        
        }
        catch(AmigoExistException ae){System.out.println("(2) Erro ao inserir Amigos! - "+ ae.getMessage());}

        Actividades desp2 = new Actividades();

        try{
            desp2.addActividade(new Futebol(1,new Data(11,11,2013),10,false,""));
            desp2.addActividade(new Rugby(2,new Data(11,11,2013),10,false,""));
            desp2.addActividade(new PoloAquat(3,new Data(20,8,2013),60));
        }
        catch(ActividadeExistException ae){System.out.println("(2) Erro ao inserir Actividades! - "+ ae.getMessage());}

        Utilizador user2 = new Utilizador("nelson@gmail.com","Nelson", "123456", "M",new Data(12,10,1989),"Futebol",
                1.72,75,amigos2,desp2);   

        //-----------------------------------------------------------------                              

        Utilizadores l1 = new Utilizadores();

        try{
            l1.addUtilizador(user1);
            l1.addUtilizador(user2); 
        }
        catch(UtilizadorExistException ue){System.out.println("(1) Erro ao inserir Utilizadores! - "+ ue.getMessage());}

        //System.out.println(".............Listagem de Utilizadores..............");
        //System.out.println(l1.toString());

        //-----------------------------------------------------------------                              
        //Iterator<Utilizador> it = l1.getUtilizadores().values().iterator();
        //System.out.println(".............Listagem de Utilizadores..............");
        // while (it.hasNext())
        //   System.out.println(it.next().toString());

        //----------------------------------------------------------------- 
        //System.out.println(".............Existe Utilizador..............");
        //System.out.println(l1.existe("nelson@gmail.com"));   
        //----------------------------------------------------------------- 

        //System.out.println(user2.getUActividades().ordenar()toString());
        //Iterator<Actividade> it = user2.getUActividades().ordenar().iterator();
        //System.out.println(".............Actividades do Nelson..............");
        //  while (it.hasNext())
        //    System.out.println(it.next().toString());

            
        return l1;
    }
}
