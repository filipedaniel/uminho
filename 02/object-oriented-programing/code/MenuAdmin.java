import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import static java.lang.System.out;
import java.util.Scanner;

/**
 * 
 * @author (your name) 
 */
public class MenuAdmin implements Serializable
{
    private final String menuAdmin;

    public MenuAdmin(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n <<>><<>><<>><<>> Administração <<>><<>><<>><<>> \n\n");
        sb.append("\t1 - Total de Utilizadores\n");
        sb.append("\t2 - Listar:\n");
        sb.append("\t3 - Apagar:\n");
        sb.append("\t0 - Voltar\n");
        sb.append("\t\tOpção:");
        menuAdmin = sb.toString();
    }

    public String toString(){return this.menuAdmin;}

    public void printMenuAdmin(){out.print(this.toString());}

    public void execOpcao(Utilizadores list)throws Exception{

        int op = 0;

        Scanner input = new Scanner(System.in);

        do{
            this.printMenuAdmin();

            while(!input.hasNextInt()){
                input.next();
                out.println("\n Introduza o INTEIRO que corresponde à opção!!!!!!\n");
            }

            op = input.nextInt();

            switch(op){
                case 0 : out.print('\u000C'); 
                break;

                case 1 : out.print('\u000C');
                out.println("\n Total Utilizadores: \n");
                out.println(list.totalUtilizadores());
                break;

                case 2 : out.print('\u000C');
                if(list.totalUtilizadores() == 0) {out.print('\u000C');
                    out.println(" \n Não existe Utilizadores. \n");
                    break;
                }
                out.println(list.toString());
                break;

                case 3 : out.print('\u000C');
                out.println("E-Mail do Utilizador a remover:"); 
                try{
                    list.removeUtilizador(getRemove());
                }
                catch(UtilizadorEnexException e){out.println("Erro ao remover Utilizador! - " + e.getMessage());}
                break;         

                default : out.println("Opção Inválida!");
            }    

        }while(op != 0);

    }

    private String getRemove() throws Exception{
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);
        String email;
        out.println("\nE-Mail: "); email = in.readLine();
        return email;
    }  

}
