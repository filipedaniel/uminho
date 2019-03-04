
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import static java.lang.System.out;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 
 * @author (your name) 
 */

public class MenuPrincipal implements Serializable, Admin
{
    private final String menuPrincipal;

    MenuUtilizador mUtil = new MenuUtilizador();
    MenuAdmin mAdm = new MenuAdmin();

    public MenuPrincipal(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n <<>><<>><<>><<>> Home <<>><<>><<>><<>> \n\n");
        sb.append("\t1 - Login\n");
        sb.append("\t2 - Registar\n");
        sb.append("\t3 - Admin\n");
        sb.append("\t0 - Voltar\n");
        sb.append("\t\tOpção:");
        menuPrincipal = sb.toString();
    }

    public String toString(){return this.menuPrincipal;}

    public void printMenuPrincipal(){out.print(this.toString());}

    public Utilizador execOpcao(Utilizadores list) throws Exception{
        int op = 0;
        Utilizador a = null;
        Scanner input = new Scanner(System.in);

        do{
            this.printMenuPrincipal();

            while(!input.hasNextInt()){
                input.next();
                out.println("\n Introduza o INTEIRO que corresponde à opção!!!!!!\n");
            }

            op = input.nextInt();

            switch(op){
                case 0 : out.print('\u000C'); 
                break;

                case 1 : Utilizador us = new Utilizador(); 
                HashMap<String,Utilizador> huser = new HashMap<String,Utilizador>();
                huser = list.getUtilizadores();
                us = getUz(huser,getUserEmail(),getUserPass());
                if(us == null) 
                {out.print('\u000C');
                    out.println("\n Dados incorretos! \n");
                    break;}
                else {  a = us; 
                    out.print('\u000C');
                    mUtil.execOpcao(a,list);
                    break;}

                case 2 : out.print('\u000C');
                out.print("\n -> Novo Registo: \n \n");
                a = novoRegisto();
                int ok = 0;
                out.print('\u000C');
                try{
                    list.addUtilizador(a);
                    ok = 1;
                }
                catch(UtilizadorExistException e){out.println("Erro ao inserir Utilizador! - " + e.getMessage());} 
                if(ok == 1){
                    out.print('\u000C');
                    out.println("\n Registo efectuado com sucesso! \n");
                    break;}
                else{break;}
                case 3 : String m = getUserEmail();
                String p = getUserPass();
                if((m.equals(ADMIN_email)) && (p.equals(ADMIN_password))){
                    out.print('\u000C');
                    mAdm.execOpcao(list);
                    break;} 
                else{out.print('\u000C');
                    out.print("Dados de Admistração incorretos!! \n \n \n" );
                    break;}

                default : out.print('\u000C');
                out.println("Opção Inválida!");
            }    

        }while(op != 0);

        return a;
    }

    private String getUserEmail() throws Exception{
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);
        String email;

        out.println("\nE-Mail: "); email = in.readLine();
        return email;
    }

    private String getUserPass() throws Exception{
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);
        String pass;

        out.println("\nPassword: "); pass = in.readLine();
        return pass;
    }

    private Utilizador novoRegisto()throws Exception{
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);

        String email, nome, password, despFavor;

        String genero;
        int dia,mes,ano;
        double altura, peso;
        Amigos l = new Amigos();
        Actividades ac = new Actividades();

        out.println("\n E-Mail: "); email = in.readLine();
        out.println("\n Nome: "); nome = in.readLine();
        out.println("\n Password: "); password = in.readLine();
        out.println("\n Desporto Favorito: "); despFavor = in.readLine();
        out.println("\n Genero: M/F: "); genero = leGenero();
        out.println("\n Altura: "); altura = leDouble();
        out.println("\n Peso: "); peso = leDouble();
        out.println("\n Data de Nascimento: ");
        out.println("\n    Dia: "); dia = leInteiroDia();
        out.println("\n    Mes: "); mes = leInteiroMes();
        out.println("\n    Ano: "); ano = leInteiro();

        out.println("\n");
        return new Utilizador(email,nome,password,genero,new Data(dia,mes,ano),
            despFavor,altura,peso,l,ac);
    } 

    public Utilizador getUz(HashMap<String,Utilizador> x,String email,String pass){
        Utilizador u = new Utilizador();
        u = null;

        if(x.get(email) == null) {return null;}

        u = x.get(email);

        if(pass.equals(u.getPassword())) {return u;}
        else return null;
    }

    private double leDouble(){
        Scanner input = new Scanner(System.in);
        while(!input.hasNextDouble()){
            input.next();
            out.println("\nEste campo precisa de um valor to tipo númerico!!!\n");
        }
        return input.nextDouble();
    }

    private int leInteiro(){
        Scanner input = new Scanner(System.in);        
        while(!input.hasNextInt()){
            input.next();
            out.println("\nEste campo precisa de um valor to tipo Inteiro!!!\n");
        }
        return input.nextInt();
    }

    private String leGenero(){
        Scanner input = new Scanner(System.in);
        String s = input.next();
        while(s.compareTo("M") != 0 && s.compareTo("F") != 0){
            out.println("\nResportas possiveis: M ou F!");
            s = input.next();
        }
        return s;
    }

    private int leInteiroDia(){
        Scanner input = new Scanner(System.in);        
        boolean ok = false;
        int x = 0;

        while(!ok){
            while(!input.hasNextInt()){
                input.next();
                out.println("\nEste campo precisa de um valor to tipo Inteiro!!!\n");
            }
            x = input.nextInt();
            if(x<=31 && x>0) ok = true;
            else {out.println("\nInteiro entre 1 e 31!!!\n");}
        }
        return x;
    }

    private int leInteiroMes(){
        Scanner input = new Scanner(System.in);        
        boolean ok = false;
        int x = 0;

        while(!ok){
            while(!input.hasNextInt()){
                input.next();
                out.println("\nEste campo precisa de um valor to tipo Inteiro!!!\n");
            }
            x = input.nextInt();
            if(x<=12 && x>0) ok = true;
            else{out.println("\nInteiro entre 1 e 12!!!\n");;}
        }
        return x;
    }

}

