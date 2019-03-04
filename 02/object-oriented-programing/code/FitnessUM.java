import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.System.out;
import java.util.Scanner;

/**
 * 
 * @author (your name) 
 */
public class FitnessUM
{
    public static void main(String args[]) throws IOException, Exception{

        Scanner input = new Scanner(System.in);

        MenuPrincipal mPrinc = new MenuPrincipal();

        Utilizadores lUsers = new Utilizadores();

        StringBuilder s = new StringBuilder();
        s.append("\n >>>>>> Bem-vindo ao Fitness UM <<<<<< \n\n");
        s.append("\t1 - Entrar\n");
        s.append("\t2 - Salvar programa\n");
        s.append("\t3 - Carregar programa\n");
        s.append("\t0 - Sair\n");
        s.append("\t\tOpcao:");

        int op = 0;
        do{
            System.out.print(s);

            while(!input.hasNextInt()){
                input.next();
                out.println("\n Introduza o INTEIRO que corresponde à opção!!!\n");
            }    
            op = input.nextInt();

            switch(op){
                case 0 : out.println("Adeus! Bom Treino!"); 
                break;

                case 1 : out.print('\u000C');
                mPrinc.execOpcao(lUsers); 
                break;

                case 2 : out.print('\u000C');
                try {
                    FileOutputStream fot = new FileOutputStream("lutilizadores.dat");
                    ObjectOutputStream oos = new ObjectOutputStream(fot);
                    oos.writeObject(lUsers);
                    oos.close();
                    out.println("\nSistema guardado!\n");
                }
                catch (Exception e){out.println("Erro ao gravar!!");}
                break;

                case 3 : out.print('\u000C');
                try {
                    FileInputStream fin = new FileInputStream("lutilizadores.dat");
                    ObjectInputStream ois = new ObjectInputStream(fin);
                    lUsers = (Utilizadores) ois.readObject();
                    ois.close();
                    out.println("\nSistema carregado!\n");
                }
                catch (Exception e) {out.println("Erro ao carregar!!");}
                break;

                default : out.print('\u000C');
                out.println("Opcao Invalida!");
            }
        }while(op != 0);
    }
}
