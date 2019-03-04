import java.util.Iterator;

/** 
 * @author (your name) 
 */
public class TesteActividades
{

    public static void main(String args[]) throws Exception{ 

        Actividades desp = new Actividades();

        //Coletivas
        try{  
            desp.addActividade(new Futebol(4,new Data(8,8,2013),10,true,"Chuva"));
            desp.addActividade(new Andebol(5,new Data(9,9,2013),10,true,"Nuvens"));
            desp.addActividade(new Basketball(6,new Data(10,10,2013),10,true,"Sol"));
            desp.addActividade(new Rugby(6,new Data(10,10,2013),10,true,"Sol"));
        }
        catch(ActividadeExistException e){System.out.println("(6) Erro ao inserir Actividade! - "+ e.getMessage());}

        //Fitness
        try{   
            desp.addActividade(new Pilates(7,new Data(12,12,2013),10));
            desp.addActividade(new Danca(8,new Data(13,1,2013),20,"Zumba"));
            desp.addActividade(new Step(9,new Data(14,2,2013),30));
            desp.addActividade(new CardioFitness(9,new Data(14,2,2013),30));
        }
        catch(ActividadeExistException e){System.out.println("(7) Erro ao inserir Actividade! - "+ e.getMessage());} 

        //Outdoor  
        try{   
            desp.addActividade(new Corrida(10,new Data(16,4,2013),120,60,200,50,"Bom Tempo"));
            desp.addActividade(new Ciclismo(11,new Data(17,5,2013),60,20,100,50,"Sol"));
            desp.addActividade(new Caminhada(10,new Data(16,4,2013),120,60,200,50,"Bom Tempo"));
        }
        catch(ActividadeExistException e){System.out.println("(7) Erro ao inserir Actividade! - "+ e.getMessage());}

        //Aquatica
        try{   
            desp.addActividade(new Natacao(12,new Data(19,9,2013),120));
            desp.addActividade(new PoloAquat(23,new Data(20,8,2013),60));
            desp.addActividade(new Hidroginastica(23,new Data(20,8,2013),60));
        }
        catch(ActividadeExistException e){System.out.println("(8) Erro ao inserir Actividade! - "+ e.getMessage());} 

         
        //System.out.println(".............Listagem de Activadades.............");
        //System.out.println(desp.toString());

        
        /* 
        //Listagem de Actividades ordenados pela Data

        System.out.println(".............Teste Listagem de Actividades ordenados pela data..............");
        System.out.println(desp.ordenar().toString());    

         */
        //Iterator<Actividade> it = desp.ordenar().iterator();
        //System.out.println(".............Teste Listagem de Actividades ordenados pela data..............");
        //     while (it.hasNext())
        //         System.out.println(it.next().toString());

        /*         
        Iterator<Actividade> it = desp.getUltimas10Actividades().iterator();
        System.out.println("............. Listagem das ultimas 10 Actividades ordenados pela data..............");
        while (it.hasNext())
        System.out.println(it.next().toString());

        //System.out.println(desp.getUltimas10Actividades().toString());
        //System.out.println(desp.getActividadePorTipo("Musculacao").toString());
        //System.out.println(desp.totalCalorias());    
        //System.out.println(desp.totalActividades());
         */    
    }
}