import java.io.Serializable;
import static java.lang.System.out;
import java.util.Scanner;
/**
 * 
 * @author (your name) 
 */
public class MenuConsultaEstatisticas implements Serializable
{
    private final String menuEst;

    public MenuConsultaEstatisticas(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n <<>><<>><<>><<>> Menu Estatística <<>><<>><<>><<>>\n\n");
        sb.append("\t1 - Estatísticas gerais total\n");
        sb.append("\t2 - Estatísticas gerais ultimo mês\n");
        sb.append("\t3 - Estatísticas gerais Anual (Ano)\n");
        sb.append("\t\tOpcao:");
        menuEst = sb.toString();
    }

    public String toString(){return this.menuEst;}

    private void printmenuEst(){System.out.print(this.toString());}

    public int execOpcao(Actividades lista) throws Exception{
        int op = 0;
        Scanner input = new Scanner(System.in);
        do{
            this.printmenuEst();

            while(!input.hasNextInt()){
                input.next();
                out.println("\n Introduza o INTEIRO que corresponde à opção!!!!!!\n");
            }

            op = input.nextInt();

            StringBuilder c = new StringBuilder();
            switch(op){
                case 0 : out.print('\u000C');
                break;

                case 1 : out.print('\u000C');
                c.append("\n Estatisticas Gerais total: \n");
                c.append("\n Total de Actividades praticadas: \n");
                c.append("   " + lista.totalActividades());
                c.append("\n Duração total em Actividade: \n");
                c.append("   " + lista.totalDuracao());
                c.append("\n Total de calorias queimadas: \n");
                c.append("   " + lista.totalCalorias());
                c.append("\n Total Distancia percorrida: \n");
                c.append("   " + lista.totalkilometros());
                out.println(c.toString()); 
                break;

                case 2 : out.print('\u000C');
                Actividades aux = lista.getUltimoMes();
                c.append("\n Estatisticas Ultimo Mes: \n");
                c.append("\n Total de Actividades praticadas: \n");
                c.append("   " + aux.totalActividades());
                c.append("\n Duração total em Actividade: \n");
                c.append("   " + aux.totalDuracao());
                c.append("\n Total de calorias queimadas: \n");
                c.append("   " + aux.totalCalorias());
                c.append("\n Total Distancia percorrida: \n");
                c.append("   " + aux.totalkilometros());
                out.println(c.toString()); 
                break;

                case 3 : out.print('\u000C');
                int ano = getAno();
                Actividades aux2 = lista.getAnual(ano);
                c.append("\n Estatisticas Do ano: "+ ano +"\n");
                c.append("\n Total de Actividades praticadas: \n");
                c.append("   " + aux2.totalActividades());
                c.append("\n Duração total em Actividade: \n");
                c.append("   " + aux2.totalDuracao());
                c.append("\n Total de calorias queimadas: \n");
                c.append("   " + aux2.totalCalorias());
                c.append("\n Total Distancia percorrida: \n");
                c.append("   " + aux2.totalkilometros());
                out.println(c.toString()); 
                break;

                default : out.println("Opcao Invalida!");
            }
        }while(op != 0);

        return op;               
    } 

    private int getAno() throws Exception{
        int ano;
        out.println("\nAno a procurar: "); ano = leInteiro();
        return ano;
    } 

   private int leInteiro(){
        Scanner input = new Scanner(System.in);        
        while(!input.hasNextInt()){
            input.next();
            out.println("\nEste campo precisa de um valor to tipo Inteiro!!!\n");
        }
        return input.nextInt();
    }
}