import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import static java.lang.System.out;
import java.util.Scanner;

/**
 * 
 * @author (your name) 
 */

public class MenuUtilizador implements Serializable
{
    private final String menuUtilizador;

    //Interface
    MenuNovaActividade mNActs = new MenuNovaActividade();
    MenuConsultaAmigos mAmigos = new MenuConsultaAmigos();
    MenuConsultaActividades mAct = new MenuConsultaActividades();
    MenuConsultaEstatisticas mEst = new MenuConsultaEstatisticas();
    MenuConsultaRecordes mRec = new MenuConsultaRecordes();

    //Lista de Actividades.
    Actividades lAct = new Actividades();
    //Lista de Amigos
    Amigos lAmig = new Amigos();
    //Lista de Utilizadores.
    Utilizadores lUsers = new Utilizadores();

    public MenuUtilizador(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n>>>>>>>>>>>> Fitness UM <<<<<<<<<<<<\n\n");
        sb.append("\t1 - Consultar Actividades\n");
        sb.append("\t2 - Adicionar Actividade\n");
        sb.append("\t3 - Consultar Amigos\n");
        sb.append("\t4 - Adicionar Amigo\n");
        sb.append("\t5 - Remover Amigo\n");
        sb.append("\t6 - Consultar Estatísticas\n");
        sb.append("\t7 - Consultar Records\n");
        sb.append("\t8 - Alterar dados Pessoais\n");
        sb.append("\t0 - Sair\n");
        sb.append("\t\tOpcao:");
        menuUtilizador = sb.toString();
    }

    public String toString(){return this.menuUtilizador;}

    public void printMenuUtilizador(){out.print(this.toString());}

    public void execOpcao(Utilizador user,Utilizadores list) throws Exception{
        int op = 0;
        Scanner input = new Scanner(System.in);

        lUsers = list.clone();
        lAct = user.getUActividades();
        lAmig = user.getUAmigos();

        do{
            this.printMenuUtilizador();

            while(!input.hasNextInt()){
                input.next();
                out.println("\n Introduza o INTEIRO que corresponde à opção!!!!!!\n");
            }
            op = input.nextInt();

            switch(op){
                case 0 : out.print('\u000C');
                out.println("Adeus! Bom Treino!  " + user.getNome()); 
                break;

                case 1 : out.print('\u000C');
                mAct.execOpcao(lAct); 
                break;

                case 2 : out.print('\u000C');
                Actividade ac = mNActs.execOpcao();
                if(ac!=null){
                    try{
                        lAct.addActividade(ac);
                    }
                    catch(ActividadeExistException e){out.println("Erro ao adicionar actividade: " + e.getMessage());}
                    list.actualizaActividades(user.getEmail(),lAct);                                
                    ac = null;
                    break;
                }
                break;

                case 3 : out.print('\u000C');
                mAmigos.execOpcao(lAmig,list);
                break;

                case 4 : out.print('\u000C');
                Amigo am = novoAmigo();
                if(am!=null) {
                    if(am.getEmail().equals(user.getEmail())) {out.print('\u000C');
                        out.println("Erro! Não é possivel ser amigo de você mesmo!!");} 
                    if(list.existe(am.getEmail())){
                        try{
                            lAmig.addAmigo(am);
                        }
                        catch(AmigoExistException e){out.println("Erro ao adicionar Amigo: " + e.getMessage());}
                        list.actualizaAmigos(user.getEmail(),lAmig);
                        user.setUAmigos(lAmig);
                        am = null;}
                    else { out.print('\u000C');
                        out.println("Erro! Utilizador não registado!!"); 
                        break;}
                }
                break;

                case 5 : out.print('\u000C');
                String mailAmigo = getEmailAmig();
                try{
                    lAmig.removeAmigo(mailAmigo);
                }
                catch(AmigoEnexException e){out.println("Erro ao remover Amigo! - "+ e.getMessage());}
                user.setUAmigos(lAmig);
                list.actualizaAmigos(user.getEmail(),lAmig);
                break;

                case 6 : out.print('\u000C');
                mEst.execOpcao(lAct); 
                break;

                case 7 : out.println('\u000C');
                mRec.execOpcao(lAct);
                break; 

                case 8 : Utilizador u = new Utilizador();
                list.alteraRegisto(dadosRegisto(user));
                user = (list.getUtilizadorM(user.getEmail()).clone());
                out.println(user.toString());
                break; 

                default : out.println("Opção Inválida!");
            }    

        }while(op != 0);       
    }

    private String getEmailAmig() throws Exception{
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);
        String email;
        out.println("\nE-Mail: "); email = in.readLine();
        return email;
    }  

    private Amigo novoAmigo()throws Exception{
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);

        String email, nome;

        out.println("\n E-mail: "); email = in.readLine();
        out.println("\n Nome : "); nome = in.readLine();

        out.println("\n");
        return new Amigo(email,nome);
    }

    private Utilizador dadosRegisto(Utilizador u)throws Exception{
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);

        Utilizador us = new Utilizador();
        String password, despFavor;
        double altura, peso;

        us = u.clone();
        out.println("\n Password: "); password = in.readLine();
        us.setPassword(password);
        out.println("\n Desporto Favorito: "); despFavor = in.readLine();
        us.setDesportoFav(despFavor);
        out.println("\n Altura: "); altura = leDouble();
        us.setAltura(altura);
        out.println("\n Peso: "); peso = leDouble();
        us.setPeso(peso);

        out.println("\n");

        return us;
    }

    private double leDouble(){
        Scanner input = new Scanner(System.in);
        while(!input.hasNextDouble()){
            input.next();
            out.println("\nEste campo precisa de um valor to tipo númerico!!!\n");
        }
        return input.nextDouble();
    } 
}
