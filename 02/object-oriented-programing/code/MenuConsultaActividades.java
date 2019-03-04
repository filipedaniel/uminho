
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import static java.lang.System.out;
import java.util.Scanner;
import java.util.TreeSet;
import java.text.Normalizer;

/**
 * 
 * @author (your name) 
 */

public class MenuConsultaActividades implements Serializable
{
    private final String menuActi;

    public MenuConsultaActividades(){
        StringBuilder sb = new StringBuilder();
        sb.append(">>>>>>>>>>>> Menu de Consulta das Actividades <<<<<<<<<<<<\n\n");
        sb.append("\t1 - Consultar toda as Actividades\n");
        sb.append("\t2 - Consultar Ultimos 10 Actividades\n");
        sb.append("\t3 - Consultar todas de Outdoor\n");
        sb.append("\t4 - Consultar todas de Aquaticas\n");
        sb.append("\t5 - Consultar todas de Coletivas\n");
        sb.append("\t6 - Consultar todas de Fitness\n");
        sb.append("\t7 - Consultar Actividade específica (nome)\n");
        sb.append("\t0 - Voltar ao menu principal\n");        
        sb.append("\t\tOpcao:");
        menuActi = sb.toString();
    }

    public String toString(){return this.menuActi;}

    private void printmenuActi(){System.out.print(this.toString());}

    public int execOpcao(Actividades lista) throws Exception{
        int op = 0;
        Scanner input = new Scanner(System.in);
        do{
            this.printmenuActi();

            while(!input.hasNextInt()){
                input.next();
                out.println("\n Introduza o INTEIRO que corresponde à opção!!!!!!\n");
            }

            op = input.nextInt();

            switch(op){
                case 0 : System.out.print('\u000C');
                break;

                case 1 : System.out.print('\u000C');
                System.out.println(lista.ordenar().toString()); break;

                case 2 : System.out.print('\u000C');
                System.out.println(lista.getUltimas10Actividades().toString()); break;

                case 3 : System.out.print('\u000C');
                TreeSet<Actividade> c1 = lista.getActividadePorTipo("Corrida");
                TreeSet<Actividade> c2 = lista.getActividadePorTipo("Caminhada");
                TreeSet<Actividade> c3 = lista.getActividadePorTipo("Ciclismo");
                c1.addAll(c2); c1.addAll(c3);
                System.out.println(c1.toString()); break;

                case 4 : System.out.print('\u000C');
                TreeSet<Actividade> a1 = lista.getActividadePorTipo("Natacao");
                TreeSet<Actividade> a2 = lista.getActividadePorTipo("PoloAquat");
                TreeSet<Actividade> a3 = lista.getActividadePorTipo("Hidroginastica");
                a1.addAll(a2); a1.addAll(a3);
                System.out.println(a1.toString()); break;

                case 5 : System.out.print('\u000C');
                TreeSet<Actividade> cl1 = lista.getActividadePorTipo("Futebol");
                TreeSet<Actividade> cl2 = lista.getActividadePorTipo("Basketball");
                TreeSet<Actividade> cl3 = lista.getActividadePorTipo("Andebol");
                TreeSet<Actividade> cl4 = lista.getActividadePorTipo("Rugby");
                cl1.addAll(cl2); cl1.addAll(cl3);cl1.addAll(cl4);
                System.out.println(cl1.toString()); break;

                case 6 : System.out.print('\u000C');
                TreeSet<Actividade> f1 = lista.getActividadePorTipo("Pilates");
                TreeSet<Actividade> f2 = lista.getActividadePorTipo("Danca");
                TreeSet<Actividade> f3 = lista.getActividadePorTipo("CardioFitness");
                TreeSet<Actividade> f4 = lista.getActividadePorTipo("Step");
                f1.addAll(f2); f1.addAll(f3);f1.addAll(f4);
                System.out.println(f1.toString()); break;

                case 7 : System.out.print('\u000C');
                String n = getNomeAct();
                if(n.equals("Corrida") || n.equals("Caminhada") || n.equals("Ciclismo") || n.equals("Futebol") || n.equals("Basketball") ||
                n.equals("Andebol") || n.equals("Rugby") || n.equals("Pilates") || n.equals("Step"))
                {
                    TreeSet<Actividade> a = lista.getActividadePorTipo(n);
                    if(a==null || a.isEmpty()){
                        out.println("Não exite actividades de " + n +".\n");
                        break;
                    }
                    else{ out.println(a.toString()); 
                        break;
                    }
                }
                else{
                    TreeSet<Actividade> b = null;
                    String sa = removerAcentos(n);
                    switch(sa){
                        case "Natao" : b = lista.getActividadePorTipo("Natacao");break;
                        case "Polo Aquatico" : b = lista.getActividadePorTipo("PoloAquat");break;
                        case "Hidroginstica" : b = lista.getActividadePorTipo("Hidroginastica");break;
                        case "Dana" : b = lista.getActividadePorTipo("Danca");break;
                        case "Cardio Fitness" : b = lista.getActividadePorTipo("CardioFitness");break;
                        default : System.out.println("Actividade não conhecida!");                                
                    }
                    if(b==null || b.isEmpty()){out.println("Não exite actividades de " + n +".\n");break;}
                    else{
                        out.println(b.toString()); break;}
                }
                default : System.out.println("Opcao Invalida!");
            }
        }while(op != 0);

        return op;               
    }   

    private String getNomeAct() throws Exception{
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);
        String nome;
        out.println("\nNome de Actividade a procurar: "); nome = in.readLine();
        return nome;
    }  

    public String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }  

}
