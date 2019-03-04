
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import static java.lang.System.out;
import java.util.Scanner;

/**
 * 
 * @author (your name) 
 */

public class MenuNovaActividade implements Serializable
{
    private final String menuNovaActividade;

    private int id = 0;

    public MenuNovaActividade(){
        StringBuilder sb = new StringBuilder();
        sb.append(">>>>>>>>>>>>>>>> Menu Nova Actividade <<<<<<<<<<<<<<<<\n\n");
        sb.append("\t1 - Corrida\n");
        sb.append("\t2 - Caminhada\n");
        sb.append("\t3 - Ciclismo\n");
        sb.append("\t4 - Andebol\n");
        sb.append("\t5 - Basketball\n");
        sb.append("\t6 - Futebol\n");
        sb.append("\t7 - Rugby\n");
        sb.append("\t8 - Natação\n");
        sb.append("\t9 - Polo Aquatico\n");
        sb.append("\t10 - Hidroginástica\n");
        sb.append("\t11 - Pilates\n");
        sb.append("\t12 - Dança\n");
        sb.append("\t13 - Cardio Fitness\n");
        sb.append("\t14 - Step\n");
        sb.append("\t0 - Voltar ao menu principal\n");
        sb.append("\t\tOpção:");
        menuNovaActividade = sb.toString();
    }

    public String toString(){return this.menuNovaActividade;}

    public void menuNovaActividade(){out.print(this.toString());}

    public Actividade execOpcao()throws Exception{
        int op = 0;

        Actividade ac = null;
        Scanner input = new Scanner(System.in);

        do{
            this.menuNovaActividade();

            while(!input.hasNextInt()){
                input.next();
                out.println("\n Introduza o INTEIRO que corresponde à opção!!!!!!\n");
            }

            op = input.nextInt();

            switch(op){
                case 0 : System.out.print('\u000C');
                break;
                case 1 : System.out.print('\u000C');
                ac = novaCorrida();
                break;
                case 2 : System.out.print('\u000C');
                ac = novaCaminhada();
                break;
                case 3 : System.out.print('\u000C');
                ac = novaCiclismo();
                break;
                case 4 : System.out.print('\u000C');
                ac = novaAndebol();
                break;
                case 5 : System.out.print('\u000C');
                ac = novaBasketball();
                break;
                case 6 : System.out.print('\u000C');
                ac = novaFutebol();
                break;
                case 7 : System.out.print('\u000C');
                ac = novaRugby();
                break;
                case 8 : System.out.print('\u000C');
                ac = novaNatacao();                           
                break;
                case 9 : System.out.print('\u000C');
                ac = novaPoloAquatico();
                break;
                case 10 : System.out.print('\u000C');
                ac = novaHidroginastica();
                break;
                case 11 : System.out.print('\u000C');
                ac = novaPilates();
                break;
                case 12 : System.out.print('\u000C');
                ac = novaDanca();
                break;
                case 13 : System.out.print('\u000C');
                ac = novaCardioFitness();
                break;
                case 14 : System.out.print('\u000C');
                ac = novaStep();
                break;

                default : out.println("Opção Inválida!");
            }    

            return ac;
        }while(op != 0);
    }

    //Nova corrida

    private Corrida novaCorrida()throws Exception{
        id++;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);

        int dia, mes,ano;
        double duracao;
        double distancia;
        double altMax,altMin;
        String condMet;
        out.println("\n Nova Actividade Corrida: \n");
        out.println("\n Data: ");
        out.println("\n Dia: "); dia = leInteiroDia();
        out.println("\n Mes: "); mes = leInteiroMes();
        out.println("\n Ano: "); ano = leInteiro();
        out.println("\n Duarção (min): "); duracao = leDouble();
        out.println("\n Distância percorrida (kms): "); distancia = leDouble();
        out.println("\n Altitude Maxima (ms): "); altMax = leDouble();
        out.println("\n Altitude Minima (ms): "); altMin = leDouble();
        out.println("\n Condição Metriologica: "); condMet = in.readLine();
        out.println("\n");

        return new Corrida(id,new Data(dia,mes,ano),duracao,distancia, altMax,altMin,condMet);
    }

    //Nova Caminhada
    private Caminhada novaCaminhada()throws Exception{
        id++;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);

        int dia, mes,ano;
        double duracao;
        double distancia;
        double altMax,altMin;
        String condMet;

        out.println("\n Nova Actividade Caminhada: \n");
        out.println("\n Data: ");
        out.println("\n Dia: "); dia = leInteiroDia();
        out.println("\n Mes: "); mes = leInteiroMes();
        out.println("\n Ano: "); ano = leInteiro();
        out.println("\n Duarção (min): "); duracao = leDouble();
        out.println("\n Distância percorrida (kms): "); distancia = leDouble();
        out.println("\n Altitude Maxima (ms): "); altMax = leDouble();
        out.println("\n Altitude Minima (ms): "); altMin = leDouble();
        out.println("\n Condição Metriologica: "); condMet = in.readLine();
        out.println("\n");

        return new Caminhada(id,new Data(dia,mes,ano),duracao,distancia, altMax,altMin,condMet);
    }

    //Nova Ciclismo
    private Ciclismo novaCiclismo()throws Exception{
        id++;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);

        int dia, mes,ano;
        double duracao;
        double distancia;
        double altMax,altMin;
        String condMet;

        out.println("\n Nova Actividade Ciclismo: \n");
        out.println("\n Data: ");
        out.println("\n Dia: "); dia = leInteiroDia();
        out.println("\n Mes: "); mes = leInteiroMes();
        out.println("\n Ano: "); ano = leInteiro();
        out.println("\n Duarção (min): "); duracao = leDouble();
        out.println("\n Distância percorrida (kms): "); distancia = leDouble();
        out.println("\n Altitude Maxima (ms): "); altMax = leDouble();
        out.println("\n Altitude Minima (ms): "); altMin = leDouble();
        out.println("\n Condição Metriologica: "); condMet = in.readLine();
        out.println("\n");

        return new Ciclismo(id,new Data(dia,mes,ano),duracao,distancia, altMax,altMin,condMet);
    }

    //Nova Andebol
    private Andebol novaAndebol()throws Exception{
        id++;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);
        int dia, mes,ano;
        double duracao;
        boolean arLivre;
        String condMet;

        out.println("\n Nova Actividade Andebol: \n");
        out.println("\n Data: ");
        out.println("\n Dia: "); dia = leInteiroDia();
        out.println("\n Mes: "); mes = leInteiroMes();
        out.println("\n Ano: "); ano = leInteiro();
        out.println("\n Duarção (min): "); duracao = leDouble();
        out.println("\n ArLivre (S/N): "); arLivre = leBoolean();
        if(arLivre == true) {
            out.println("\n Condição Metriologica: "); condMet = in.readLine();
        } else {condMet = "";}

        out.println("\n");
        return new Andebol(id,new Data(dia,mes,ano),duracao,arLivre,condMet);
    }

    //Nova Basketball
    private Basketball novaBasketball()throws Exception{
        id++;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);
        int dia, mes,ano;
        double duracao;
        boolean arLivre;
        String condMet;

        out.println("\n Nova Actividade Basketball: \n");
        out.println("\n Data: ");
        out.println("\n Dia: "); dia = leInteiroDia();
        out.println("\n Mes: "); mes = leInteiroMes();
        out.println("\n Ano: "); ano = leInteiro();
        out.println("\n Duarção (min): "); duracao = leDouble();
        out.println("\n ArLivre (S/N): "); arLivre = leBoolean();
        if(arLivre == true) {
            out.println("\n Condição Metriologica: "); condMet = in.readLine();
        } else {condMet = "";}

        out.println("\n");
        return new Basketball(id,new Data(dia,mes,ano),duracao,arLivre,condMet);
    }

    //Nova Futebol
    private Futebol novaFutebol()throws Exception{
        id++;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);
        int dia, mes,ano;
        double duracao;
        boolean arLivre;
        String condMet;

        out.println("\n Nova Actividade Futebol: \n");
        out.println("\n Data: ");
        out.println("\n Dia: "); dia = leInteiroDia();
        out.println("\n Mes: "); mes = leInteiroMes();
        out.println("\n Ano: "); ano = leInteiro();
        out.println("\n Duarção (min): "); duracao = leDouble();
        out.println("\n ArLivre (S/N): "); arLivre = leBoolean();
        if(arLivre == true) {
            out.println("\n Condição Metriologica: "); condMet = in.readLine();
        } else {condMet = "";}

        out.println("\n");
        return new Futebol(id,new Data(dia,mes,ano),duracao,arLivre,condMet);
    }

    //Nova Rugby
    private Rugby novaRugby()throws Exception{
        id++;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);
        int dia, mes,ano;
        double duracao;
        boolean arLivre;
        String condMet;

        out.println("\n Nova Actividade Rugby: \n");
        out.println("\n Data: ");
        out.println("\n Dia: "); dia = leInteiroDia();
        out.println("\n Mes: "); mes = leInteiroMes();
        out.println("\n Ano: "); ano = leInteiro();
        out.println("\n Duarção (min): "); duracao = leDouble();
        out.println("\n ArLivre (S/N): "); arLivre = leBoolean();
        if(arLivre == true) {
            out.println("\n Condição Metriologica: "); condMet = in.readLine();
        } else {condMet = "";}

        out.println("\n");
        return new Rugby(id,new Data(dia,mes,ano),duracao,arLivre,condMet);
    }

    //Nova Natação
    private Natacao novaNatacao()throws Exception{
        id++;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);

        int dia, mes,ano;
        double duracao;

        out.println("\n Nova Actividade Natação: \n");
        out.println("\n Data: ");
        out.println("\n Dia: "); dia = leInteiroDia();
        out.println("\n Mes: "); mes = leInteiroMes();
        out.println("\n Ano: "); ano = leInteiro();
        out.println("\n Duarção (min): "); duracao = leDouble();

        out.println("\n");

        return new Natacao(id,new Data(dia,mes,ano),duracao);
    }

    //Nova Polo Aquatico
    private PoloAquat novaPoloAquatico()throws Exception{
        id++;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);

        int dia, mes,ano;
        double duracao;

        out.println("\n Nova Actividade Polo Aquatico: \n");
        out.println("\n Data: ");
        out.println("\n Dia: "); dia = leInteiroDia();
        out.println("\n Mes: "); mes = leInteiroMes();
        out.println("\n Ano: "); ano = leInteiro();
        out.println("\n Duarção (min): "); duracao = leDouble();

        out.println("\n");

        return new PoloAquat(id,new Data(dia,mes,ano),duracao);
    }

    //nova Hidroginastica
    private Hidroginastica novaHidroginastica()throws Exception{
        id++;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);

        int dia, mes,ano;
        double duracao;

        out.println("\n Nova Actividade Hidroginástica: \n");
        out.println("\n Data: ");
        out.println("\n Dia: "); dia = leInteiroDia();
        out.println("\n Mes: "); mes = leInteiroMes();
        out.println("\n Ano: "); ano = leInteiro();
        out.println("\n Duarção (min): "); duracao = leDouble();

        out.println("\n");

        return new Hidroginastica(id,new Data(dia,mes,ano),duracao);
    }

    //Nova Pilates
    private Pilates novaPilates()throws Exception{
        id++;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);

        int dia, mes,ano;
        double duracao;

        out.println("\n Nova Actividade Pilates: \n");
        out.println("\n Data: ");
        out.println("\n Dia: "); dia = leInteiroDia();
        out.println("\n Mes: "); mes = leInteiroMes();
        out.println("\n Ano: "); ano = leInteiro();
        out.println("\n Duarção (min): "); duracao = leDouble();

        out.println("\n");

        return new Pilates(id,new Data(dia,mes,ano),duracao);
    }

    //Nova Dança
    private Danca novaDanca()throws Exception{
        id++;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);

        int dia, mes,ano;
        double duracao;
        String nome;

        out.println("\n Nova Actividade Dança: \n");
        out.println("\n Estilo de Dança: "); nome = in.readLine();;
        out.println("\n Data: ");
        out.println("\n Dia: "); dia = leInteiroDia();
        out.println("\n Mes: "); mes = leInteiroMes();
        out.println("\n Ano: "); ano = leInteiro();
        out.println("\n Duarção (min): "); duracao = leDouble();

        out.println("\n");

        return new Danca(id,new Data(dia,mes,ano),duracao,nome);
    }

    //Nova CardioFitness
    private CardioFitness novaCardioFitness()throws Exception{
        id++;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);

        int dia, mes,ano;
        double duracao;

        out.println("\n Nova Actividade Cardio Fitness: \n");
        out.println("\n Data: ");
        out.println("\n Dia: "); dia = leInteiroDia();
        out.println("\n Mes: "); mes = leInteiroMes();
        out.println("\n Ano: "); ano = leInteiro();
        out.println("\n Duarção (min): "); duracao = leDouble();

        out.println("\n");

        return new CardioFitness(id,new Data(dia,mes,ano),duracao);
    }

    //Nova Step
    private Step novaStep()throws Exception{
        id++;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);

        int dia, mes,ano;
        double duracao;

        out.println("\n Nova Actividade Step: \n");
        out.println("\n Data: ");
        out.println("\n Dia: "); dia = leInteiroDia();
        out.println("\n Mes: "); mes = leInteiroMes();
        out.println("\n Ano: "); ano = leInteiro();
        out.println("\n Duarção (min): "); duracao = leDouble();

        out.println("\n");

        return new Step(id,new Data(dia,mes,ano),duracao);
    }

    //Verificação de tipos especificos de input

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

    private boolean leBoolean(){
        Scanner input = new Scanner(System.in);
        String s = input.next();
        while(s.compareTo("N") != 0 && s.compareTo("S") != 0){
            out.println("\nResportas possiveis: S ou N!");
            s = input.next();
        }
        if(s.compareTo("N") == 0) return false;
        else return true;
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
            else{out.println("\nInteiro entre 1 e 31!!!\n");}
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
            else{out.println("\nInteiro entre 1 e 12!!!\n");}
        }
        return x;
    }
}
