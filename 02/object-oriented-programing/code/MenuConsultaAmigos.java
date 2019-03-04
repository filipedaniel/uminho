
/**
 * 
 * @author (your name) 
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.util.Scanner;

public class MenuConsultaAmigos
{
    private final String menuAmig;

    MenuConsultaEstatisticas mEst = new MenuConsultaEstatisticas();
    MenuConsultaRecordes mRec = new MenuConsultaRecordes();

    public MenuConsultaAmigos(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n <<>><<>><<>><<>> Menu de Amigos <<>><<>><<>><<>>\n\n");
        sb.append("\t1 - Consultar Amigos\n");
        sb.append("\t2 - Consultar as ultimas 10 Actividades de um Amigo (E-Mail)\n");
        sb.append("\t3 - Consultar todas as Actividades de um Amigo (E-Mail)\n");
        sb.append("\t4 - Estatística Amigos (E-Mail)\n");
        sb.append("\t5 - Record Amigos (E-Mail)\n");
        sb.append("\t0 - Voltar ao menu principal\n");        
        sb.append("\t\tOpcao:");
        menuAmig = sb.toString();
    }

    public String toString(){return this.menuAmig;}

    private void printmenuAmig(){System.out.print(this.toString());}

    public int execOpcao(Amigos lamigos, Utilizadores list)throws Exception{
        int op;
        Amigo a;
        Scanner input = new Scanner(System.in);

        do{
            this.printmenuAmig();

            while(!input.hasNextInt()){
                input.next();
                out.println("\n Introduza o INTEIRO que corresponde à opção!!!!!!\n");
            }

            op = input.nextInt();

            switch(op){
                case 0 : out.print('\u000C');
                break;

                case 1 : out.print('\u000C');
                out.println(lamigos.toString()); 
                break;

                case 2 : out.print('\u000C');
                String s = getNomeAmigo();
                Utilizador u = new Utilizador();
                try{
                    u = list.getUtilizadorM(s);
                }
                catch(UtilizadorEnexException e){out.println("utilizador não existente: " + e.getMessage());}

                out.println(u.getUActividades().getUltimas10Actividades().toString()); 
                break;

                case 3 : out.print('\u000C');
                String s1 = getNomeAmigo();
                Utilizador u1 = new Utilizador();
                try{
                    u1 = list.getUtilizadorM(s1);
                }
                catch(UtilizadorEnexException e){out.println("utilizador não existente: " + e.getMessage());}

                out.println(u1.getUActividades().ordenar().toString());
                break;

                case 4 : out.print('\u000C');
                String s2 = getNomeAmigo();
                Utilizador u2 = new Utilizador();
                try{
                    u2 = list.getUtilizadorM(s2);
                }
                catch(UtilizadorEnexException e){out.println("utilizador não existente: " + e.getMessage());}

                mEst.execOpcao(u2.getUActividades());
                break;

                case 5 : out.print('\u000C');
                String s3 = getNomeAmigo();
                Utilizador u3 = new Utilizador();
                try{
                    u3 = list.getUtilizadorM(s3);
                }
                catch(UtilizadorEnexException e){out.println("utilizador não existente: " + e.getMessage());}

                mRec.execOpcao(u3.getUActividades());
                break;

                default : out.println("Opção Inválida!");
            }    
        }while(op != 0);

        return op;
    }

    private String getNomeAmigo() throws Exception{
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);
        String email;
        System.out.println("\nE-Mail: "); email = in.readLine();
        return email;
    }  
}
