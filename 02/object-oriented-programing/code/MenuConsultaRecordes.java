import java.io.Serializable;
import static java.lang.System.out;
import java.util.Scanner;
/**
 * 
 * @author (your name) 
 */
public class MenuConsultaRecordes implements Serializable
{
    private final String menuRec;

    public MenuConsultaRecordes(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n <<>><<>><<>><<>> Recordes <<>><<>><<>><<>>\n\n");
        sb.append("\t1 - Recordes Gerais\n");
        sb.append("\t0 - Voltar ao menu principal\n");        
        sb.append("\t\tOpcao:");
        menuRec = sb.toString();
    }

    public String toString(){return this.menuRec;}

    private void printmenuRec(){System.out.print(this.toString());}

    public int execOpcao(Actividades lista) throws Exception{
        int op = 0;
        Scanner input = new Scanner(System.in);

        do{
            this.printmenuRec();

            while(!input.hasNextInt()){
                input.next();
                out.println("\n Introduza o INTEIRO que corresponde à opção!!!!!!\n");
            }

            op = input.nextInt();

            StringBuilder c = new StringBuilder();

            switch(op){
                case 0 : System.out.print('\u000C');
                break;

                case 1 : System.out.print('\u000C');
                c.append("\n - Recordes Gerais: \n");
                c.append("\n Total de Actividades praticadas: \n");
                c.append("   " + lista.totalActividades());
                c.append("\n Maxima duração numa Actividade: \n");
                c.append("   " + lista.maximaDuracao() + " min");
                c.append("\n Maximo de calorias queimadas numa Actividade: \n");
                c.append("   " + lista.maximoCalorias());
                System.out.println(c.toString()); 
                break;

                default : System.out.println("Opcao Invalida!");
            }
        }while(op != 0);

        return op;               
    } 
}
