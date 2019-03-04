package GESTHIPER;

import Hipermercado.CodigoInexistenteException;
import Hipermercado.EstatisticaAnual;
import Hipermercado.Hipermercado;
import Hipermercado.Par_CodQuant;
import Hipermercado.RegistoCompra;
import Hipermercado.RegistosInvalidos;
import Hipermercado.Trio_CodQuantFat;
import Hipermercado.VendaAnual;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import static java.lang.System.in;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;


/**
 *
 * @author fdr
 */
public class GESTHIPER implements Serializable{
    
        private static final int elemsPagina = 24;
        
        private static Hipermercado mercado = new Hipermercado();
        
        private static final String fileProdutos = "FichProdutos.txt";
        private static final String fileClientes = "FichClientes.txt";
        private static String fileCompras = "";
        
        private static int totalProdutos = 0;
        private static int totalClientes = 0; 
        private static int totalCompras = 0;
       
        private static int comprasValor0 = 0;
        
        private static RegistosInvalidos rInvalidos = new RegistosInvalidos();
        
        private static FileReader fileReader;
        
        private static final String[] meses = {"Jan","Fev","Mar","Abr","Mai",
                                               "Jun", "Jul","Ago","Set",
                                               "Out","Nov","Dez"};
    
    
    public static void bemVindo(){
        StringBuilder s = new StringBuilder();
        s.append("\n\n");
        s.append("  __ _  ___  ___| |_| |__ (_)_ __   ___ _ __ \n");
        s.append(" / _` |/ _ \\/ __| __| '_ \\| | '_ \\ / _ \\ '__|\n");
        s.append("| (_| |  __/\\__ \\ |_| | | | | |_) |  __/ |  \n");
        s.append(" \\__, |\\___||___/\\__|_| |_|_| .__/ \\___|_|  \n");
        s.append(" |___/                      |_|     \n");
        s.append("\n");
        System.out.println(s.toString());
    }
    
    public static void menuLoad(){
        StringBuilder s = new StringBuilder();
        s.append("   1 - Load (.txt) file Compras \n");
        s.append("   2 - Load (.obj) file         \n");
        s.append("   0 - Exit                     \n");
        s.append("> ");
        System.out.print(s.toString());
    }
    
   
    public static void menuQueries(){
        StringBuilder s = new StringBuilder();
        s.append("       OPÇÃO: \n");
        s.append("\n"); 
        s.append("   1 - Consultas estáticas      \n");
        s.append("   2 - Consultas interativas   \n");
        s.append("   3 - Save (.obj) file         \n");
        s.append("   4 - Recarregar ficheiro      \n");
        s.append("   0 - Exit                     \n");
        s.append("\n");
	s.append("> ");
        System.out.print(s.toString());
        
    }
    
    
    public static void menuQueriesIteractivas(){
        StringBuilder s = new StringBuilder();
        s.append("                     Escolha uma Opção                   \n");
        s.append("                                                         \n"); 
        s.append("   1 - Produtos nunca comprados                          \n");
        s.append("   2 - Clientes que nunca efetuaram compras              \n");
        s.append("   3 - Info de compras de um mês                         \n");
        s.append("   4 - Registo de compras anual de cliente               \n");
        s.append("   5 - Registo de vendas anual de produto                \n");
        s.append("   6 - Para código de produto registo numero de compras \n");
        s.append("   7 - Produtos mais comprados por cliente               \n");
        s.append("   8 - Top X produtos mais vendidos em todo ano          \n");
        s.append("   9 - Top X clientes que compraram mais produtos        \n");
        s.append("  10 - Info do top X clientes de um porduto              \n");
        s.append("   0 - back                                              \n");
        s.append("\n");
        s.append("OPÇÃO:...\n");
	s.append("> ");
        System.out.print(s.toString());
    }
    
    
    public static void pauseProg(){
        System.out.println("PRESS ENTER TO CONTINUE...");
        Scanner keyboard = new Scanner(System.in);
        while ( keyboard.nextLine().length() > 0 );
    }
    
    
    public static void seeMore(){
        System.out.println("PRESS ENTER TO SEE MORE...");
        Scanner keyboard = new Scanner(System.in);
        while ( keyboard.nextLine().length() > 0 );
    }
    
    
   
    public static String lerString() {
        Scanner scan = new Scanner(in);
        boolean ok = false; 
        String txt = "";
        while(!ok) {
            try {
                txt = scan.nextLine();
                ok = true;
            }
            catch(InputMismatchException e) { 
                out.println("Texto Inválido"); 
                out.print("Novo valor: ");
                scan.nextLine(); 
            }
        }     
        return txt;
    } 

	 
    public static int lerInt() {
        Scanner scan = new Scanner(in);
        boolean ok = false; 
        int i = 0; 
        while(!ok) {
            try {
                i = scan.nextInt();
                ok = true;
            }
            catch(InputMismatchException e) { 
                out.println("Inteiro Inválido"); 
                out.print("Novo valor: ");
                scan.nextLine(); 
            }
        }
        return i;
     } 
    
    public static int lerMes() {
        Scanner scan = new Scanner(in);
        boolean ok = false; 
        int i = 0; 
        while(!ok) {
            try {
                i = scan.nextInt();
                if(i<=12 && i>=1)
                    ok = true;
                else
                    System.out.println("<1 ... 12>");
            }
            catch(InputMismatchException e) { 
                out.println("Mês Inválido"); 
                out.print("Mês: ");
                scan.nextLine(); 
            }
        }
        return i;
     } 
    
    
    
  
    public static <T> List<List<T>> getFormatPages(Collection<T> c, Integer pageSize) {
    
        if (c == null) return Collections.emptyList();
        List<T> list = new ArrayList<>(c);
        
        if (pageSize == null || pageSize <= 0 || pageSize > list.size())
            pageSize = list.size();
    
        int numPages = (int) ((double)list.size() / (double)pageSize);
        List<List<T>> pages = new ArrayList<>(numPages);
        
        for (int pageNum = 0; pageNum < numPages;)
            pages.add(list.subList(pageNum * pageSize, Math.min(++pageNum * pageSize, list.size())));
        
        return pages;
}
    
    
    public static String imprimePagina(List<String> l){
        
        StringBuilder sb = new StringBuilder();
        Iterator it = l.iterator();
        
        while(it.hasNext())
            sb.append(it.next()).append("\n");
        
        return sb.toString();
    }
    
   
    public static void loadFichProdutos(){
        try {     
            FileReader f = new FileReader("FichProdutos.txt");
            BufferedReader bufferedReader = new BufferedReader(f);
            StringBuilder sb = new StringBuilder();
            String codigo;
            while ((codigo = bufferedReader.readLine()) != null) {
                mercado.insertCodigoProduto(codigo);
                totalProdutos++;
            }
            f.close();
            bufferedReader.close();
        } catch(IOException e) {
                System.out.println("Ficheiro definido na Path não encontrado!!");
        }
    }
    
    
    
    
    public static void loadFichClientes(){
        try {     
            FileReader f = new FileReader("FichClientes.txt");
            BufferedReader bufferedReader = new BufferedReader(f);
            StringBuilder sb = new StringBuilder();
            String codigo;
            while ((codigo = bufferedReader.readLine()) != null) {
                mercado.insertCodigoCliente(codigo);
                totalClientes++;
            }
            f.close();
            bufferedReader.close();
        } catch(IOException e) {
                System.out.println("Ficheiro definido na Path não encontrado!!");
        }
    }
    
    public static void loadFichCompras(String file) throws FileNotFoundException, IOException{

        GESTHIPER.fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String linha;
        String er = " ";
        RegistoCompra c;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] tokens = linha.split(er);
                if(!GESTHIPER.mercado.existeCodigoProduto(tokens[0])){
                    totalCompras++;
                    rInvalidos.insereRegistoInvalido(linha+ " PRODUTO NÂO EXISTE\n", GESTHIPER.totalCompras);
                    continue;
                }
                if(!GESTHIPER.mercado.existeCodigoCliente(tokens[4])){
                    totalCompras++;
                    rInvalidos.insereRegistoInvalido(linha+ " CLIENTE NÂO EXISTE\n", GESTHIPER.totalCompras);
                    continue;
                }
                       
                c = new RegistoCompra(tokens[0], Float.parseFloat(tokens[1]), 
                                      Integer.parseInt(tokens[2]), tokens[3].charAt(0), tokens[4], 
                                      Integer.parseInt(tokens[5]));
                
                GESTHIPER.mercado.registerSale(c);
                if(c.getValorUnitario() == 0)
                    comprasValor0++;
                totalCompras++;
                c = null;
            }
            GESTHIPER.fileReader.close();
            bufferedReader.close();
    }
    
    
    public static void loadFichComprasScanner(String file) throws FileNotFoundException, IOException{
            
            
            GESTHIPER.fileReader = new FileReader(file);
            Scanner fileS = new Scanner(fileReader);
            fileS.useDelimiter(System.getProperty("line.separator"));
            
            String linha;
            GESTHIPER.rInvalidos = new RegistosInvalidos();
            RegistoCompra c = new RegistoCompra();
            
            while (fileS.hasNext()) {
                linha = fileS.next();
                
                StringTokenizer strtok = new StringTokenizer(linha);
                c.setCodigoProduto(strtok.nextToken());
                c.setValorUnitario(Float.parseFloat(strtok.nextToken()));
                c.setQuantidade(Integer.parseInt(strtok.nextToken()));
                c.setQuantidade(strtok.nextToken().charAt(0));
                c.setCodigoCliente(strtok.nextToken());
                c.setMes(Integer.parseInt(strtok.nextToken()));
        
                
                if(!GESTHIPER.mercado.existeCodigoProduto(c.getCodigoProduto())){
                    totalCompras++;
                    rInvalidos.insereRegistoInvalido(linha+ " PRODUTO NÂO EXISTE\n", GESTHIPER.totalCompras);
                    continue;
                }
                if(!GESTHIPER.mercado.existeCodigoCliente(c.getCodigoCliente())){
                    totalCompras++;
                    rInvalidos.insereRegistoInvalido(linha+ " CLIENTE NÂO EXISTE\n", GESTHIPER.totalCompras);
                    continue;
                }
               
                GESTHIPER.mercado.registerSale(c);

                if(c.getValorUnitario() == 0)
                    comprasValor0++;
                totalCompras++;
        }
            GESTHIPER.fileReader.close();
            fileS.close();
     
    }
    
   
    public static void loadObj() throws IOException, ClassNotFoundException{
        String file;
        System.out.println("Nome do ficheiro? (hipermecado.obj PRESS ENTER) :");
        String in = GESTHIPER.lerString();
        file = ("".equals(in))? "hipermecado.obj" : in;
        ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file));
        mercado = (Hipermercado) oin.readObject();
        oin.close();
    }
    
    public static void saveObj() throws IOException{
        String file;
        System.out.println("Nome do ficheiro?( hipermecado.obj PRESS ENTER) :");
        String in = GESTHIPER.lerString();
        file = ("".equals(in))? "hipermecado.obj" : in;
        ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(file));
        oout.writeObject(mercado);
        oout.flush(); oout.close();
        System.out.println("Gravação efetuada. Ficheiro: "+file);
    }
    
    
    public static void queriesEstatiscas() throws IOException{
        int np = mercado.totalDiferentesProdutosComprados();
        int nc = mercado.totalDiferentesClientesQueEfetuaramCompras();
        float tf = mercado.getFaturacaoTotal();

        System.out.println("Estatisticas:\n");
        System.out.println("- Produtos: "+fileProdutos);
        System.out.println("     Total                : "+totalProdutos);
        System.out.println("     Foram Comprados      : "+np);
        int nfc = totalProdutos-np;
        System.out.println("     Não foram Comprados  : "+nfc);

        System.out.println("Clientes: "+fileClientes);
        System.out.println("     Total                : "+totalClientes);
        System.out.println("     Efetuaram compras    : "+nc);
        int nefc = totalClientes-nc;
        System.out.println("     Não Efetuaram compras: "+nefc);

        System.out.println("Compras: "+fileCompras);
        System.out.println("     Total                : "+totalCompras);
        System.out.println("     Com valor 0          : "+comprasValor0);
        System.out.println("     Total Faturado       : "+tf+" €");

        seeMore();

        // ***********  1.2

        System.out.println("Total de Compras por mês:");
        for(int i = 1; i<=12 ; i++){
            System.out.println(meses[i-1] + " : " + mercado.totalComprasMes(i));
        }

        seeMore();

        float fat = 0.0f; float total_fat = 0.0f;
        System.out.println("Total de Faturado por mês:");

        for(int i = 1; i<=12; i++){
            fat = mercado.totalFaturadoMes(i);
            total_fat += fat;
            System.out.println(meses[i-1] + " : " +fat+"€");
        }
        System.out.println("Total de Faturado: "+total_fat+" €");

        seeMore();

        System.out.println("Numero de clientes distintos que compraram em cada mês:");
        for(int i = 1; i<=12; i++){
            System.out.println(meses[i-1] + " : " + mercado.totalClientesDistintosEfetuaramCompras(i));
        }

        seeMore();

        System.out.println("Existe "+rInvalidos.totalInvalidos() +" Registos de compras invalidos");
        if(rInvalidos.totalInvalidos()>0){
            Character c = 'a';
            while(c!='y' && c!='n' ){
                System.out.print("Guadar registos inválilos em ficheiro?(y/n) ");
                    c = GESTHIPER.lerString().charAt(0);
            }
            if(c == 'y'){ 
                rInvalidos.saveToText();
                System.out.println("Guardado em REGISTOSINVALIDOS.txt");
            }
        }
        pauseProg();
    }
    
    
    public static void queriesInterativas(){
        boolean flag = true;
        int s;
        double time;
        while(flag)
        {
            GESTHIPER.menuQueriesIteractivas();   
            s = GESTHIPER.lerInt();
            switch(s)
            {
                case 1:
                {
                    Crono.start();
                    List<String> l = mercado.produtosQueNaoFaramComprados();
                    time = Crono.stop();
                    int t = l.size();
                    int npaginas = (int) t/elemsPagina + 1;
                    int actPag = 1;
                    List<List<String>> pags = GESTHIPER.getFormatPages(l,elemsPagina);
                    boolean ok = true;
                    System.out.println("Tempo: "+time+ "s");
                    for(int i = 0; i<pags.size() && i>=0;){
                        System.out.println("Existem um total de "+t+" registos - pagina: "+actPag+"/"+npaginas);
                        if(ok) System.out.print(GESTHIPER.imprimePagina(pags.get(i)));
                        System.out.print("front / back / exit (f / b / e): ");
                        String sc = GESTHIPER.lerString();
                        if(sc.length()==1){
                            Character c = sc.charAt(0);
                            if(c == 'f') {i++; ok = true;actPag++;}
                                else if(c == 'b') {i--; ok = true;actPag--;}
                                        else if(c == 'e') i = pags.size(); 
                                            else ok = false;
                        }
                    }      
                    pauseProg();
                    break;
                }
                case 2:
                {
                    Crono.start();
                    List<String> l = mercado.clientesNaoQueNaoEfetuaramCompras();
                    time = Crono.stop();
                    int t = l.size();
                    int npaginas = (int) t/elemsPagina +1;
                    int actPag = 1;
                    List<List<String>> pags = GESTHIPER.getFormatPages(l, elemsPagina);

                    boolean ok = true;
                    System.out.println("Tempo: "+time+ "s");
                    for(int i = 0; i<pags.size() && i>=0;){
                        System.out.println("Existem um total de "+t+" registos - pagina: "+actPag+"/"+npaginas);
                        if(ok) System.out.print(GESTHIPER.imprimePagina(pags.get(i)));
                        System.out.print("front / back / exit (f / b / e): ");
                        String sc = GESTHIPER.lerString();
                        if(sc.length()==1){
                            Character c = sc.charAt(0);
                            if(c == 'f') {i++; ok = true;actPag++;}
                                else if(c == 'b') {i--; ok = true;actPag--;}
                                    else if(c == 'e') i = pags.size(); 
                                        else ok = false;
                        }
                    }      
                    pauseProg();
                    break;
                }
                case 3:
                {
                    System.out.print("Mês?: ");
                    int mes = GESTHIPER.lerMes();
                    int tl_compras, cl_distintos;
                    
                    Crono.start();
                    tl_compras = mercado.totalNumeroComprasPorMes(mes);
                    cl_distintos = mercado.totalClientesDistintosEfetuaramCompras(mes);
                    time = Crono.stop();
                    System.out.println("< Tempo: "+time+ "s >");
                    System.out.println("Mês "+ GESTHIPER.meses[mes-1]);
                    System.out.println("Numero total de Compras   : "+tl_compras);
                    System.out.println("Numero clientes distintos : "+cl_distintos);
                    pauseProg();

                    break;
                }
                case 4:
                {
                    System.out.print("Código Cliente?: ");
                    String cod = GESTHIPER.lerString();
                    try{
                        Crono.start();
                        EstatisticaAnual estCliente = mercado.getEstatisticaAnualCliente(cod);
                        time = Crono.stop();
                        System.out.println("< Tempo: "+time+ "s >");
                        System.out.println(estCliente.toString());
                        pauseProg();
                    } catch (CodigoInexistenteException ex){
                        System.out.println("Codigo não existe: "+ex);
                    }
                    break;
                }
                case 5:
                {
                    System.out.print("Código Produto?: ");
                    String cod = GESTHIPER.lerString();
                    try{
                        Crono.start();
                        EstatisticaAnual estProduto = mercado.getEstatisticaAnualProduto(cod);
                        time = Crono.stop();
                        System.out.println("< Tempo: "+time+ "s >");
                        System.out.println(estProduto.toString());
                        pauseProg();
                    } catch (CodigoInexistenteException ex){
                        System.out.println("Código não existe: "+ ex);
                    }
                    break;
                }
                case 6:
                {
                    System.out.print("Código Produto?: ");
                    String cod = GESTHIPER.lerString();
                    try{
                        Crono.start();
                        VendaAnual va = mercado.registoAnualProduto(cod);
                        time = Crono.stop();
                        System.out.println("< Tempo: "+time+ "s >");
                        for(int i = 0; i<12; i++){
                            System.out.println(meses[i]);
                            System.out.print(" - Num. Total Compras : ");
                            System.out.println(va.getNumeroTotalVendasMes(i+1));
                            System.out.print("                  - N : ");
                            System.out.println(va.getNumeroVendasNormalMes(i+1));
                            System.out.print(" -                  P : ");
                            System.out.println(va.getNumeroVendasPromocaoMes(i+1));
                            System.out.print(" -  Faturação Total   : ");
                            System.out.println(va.getTotalFaturacaoMes(i+1)+" €");
                        }
                        pauseProg();
                    }catch (CodigoInexistenteException ex){
                        System.out.println("Codigo não existe: "+ex);
                    }
                    break;
                }
                case 7:
                {
                    System.out.println("Código Cliente?: ");
                    String cod = GESTHIPER.lerString();
                    try{
                        Crono.start();
                        List<Par_CodQuant> p = mercado.topComprasCliente(cod);
                        time = Crono.stop();
                        System.out.print("Total produdutos comprados: ");
                        System.out.println(p.size());
                        Iterator<Par_CodQuant> it = p.iterator();
                        while(it.hasNext())
                            System.out.println(it.next());
                        System.out.println("< Tempo: "+time+ "s >");
                        pauseProg();
                    }catch (CodigoInexistenteException ex){
                        System.out.println("Codigo não existe: "+ex);
                    }
                    break;
                }
                case 8:
                {
                    System.out.println("Top: " );
                    int top = GESTHIPER.lerInt();
                    Crono.start();
                    List<Par_CodQuant> topProd = mercado.topXProdutosComprados(top);
                    time = Crono.stop();
                    Iterator<Par_CodQuant> it = topProd.iterator();
                    while(it.hasNext())
                        System.out.println(it.next());
                    System.out.println("< Tempo: "+time+ "s >");
                    pauseProg();
                    break;
                }
                case 9:
                {
                    System.out.println("Top: : ");
                    int top = GESTHIPER.lerInt();
                    Crono.start();
                    List<Par_CodQuant> topCli = mercado.topXClientesMaisProdutosDistintosComprados(top);
                    time = Crono.stop();
                    Iterator<Par_CodQuant> it = topCli.iterator();
                    while(it.hasNext())
                        System.out.println(it.next());
                    System.out.println("< Tempo: "+time+ "s >");
                    pauseProg();
                    break;
                }
                case 10:
                {
                    System.out.println("Código de Produto?: ");
                    String cod = GESTHIPER.lerString();
                    System.out.println("Top: : ");
                    int top = GESTHIPER.lerInt();
                    try{
                        Crono.start();
                        List<Trio_CodQuantFat> topComp = mercado.topXCompradoresDeProduto(cod, top);
                        time = Crono.stop();
                        Iterator<Trio_CodQuantFat> it = topComp.iterator();
                        while(it.hasNext())
                            System.out.println(it.next());
                        System.out.println("< Tempo: "+time+ "s >");
                        pauseProg();
                    }catch (CodigoInexistenteException ex){
                        System.out.println("Codigo não existe: "+ex);
                    }
                    break;
                }
                case 0:
                {
                    flag = false;
                    break;
                }
                default:
                {
                    System.out.println("Comando errado!!");
                    break;
                }
            }
        }
    }
    
    
    public static void menuPrincial(){
        boolean back = false;
        boolean flag = true;
        int op;
        while(flag)
        {
            GESTHIPER.menuQueries();
            op = GESTHIPER.lerInt();
            switch(op)
            {
                case 1:
                {
                    try{GESTHIPER.queriesEstatiscas();}
                    catch(IOException e){System.out.println(e.getMessage());}
                    break;
                }
                case 2:
                {
                    GESTHIPER.queriesInterativas();
                    break;
                }
                case 3:   // save ojs
                {
                    try{    GESTHIPER.saveObj();
                    }catch(IOException e){System.out.println("Erro na gravação do ficheiro:" + e.getMessage());}
                    break;
                }
                case 4:{
                    double time;
                    System.out.println("Nome do Ficheiro de Compras: ");
                    fileCompras = GESTHIPER.lerString();
                    GESTHIPER.comprasValor0 = 0;
                    GESTHIPER.totalClientes = 0;
                    GESTHIPER.totalCompras = 0;
                    GESTHIPER.totalProdutos = 0;
                    GESTHIPER.rInvalidos.clear();
                    GESTHIPER.mercado = GESTHIPER.mercado.clearMercado();
                    loadFichClientes();
                    loadFichProdutos();
                    Crono.start();
                    try { GESTHIPER.loadFichCompras(fileCompras); } 
                    catch (IOException ex) {System.out.println(ex.getMessage()); }
                    time = Crono.stop();
                    System.out.println("TEMPO: "+time);
                    break;
                }
                case 0:
                {
                    flag = false;
                    GESTHIPER.mercado = null;
                    GESTHIPER.rInvalidos = null;
                    System.gc();
                    break;
                }
            }   
        }
    }
    
//***************************************************************************** 
    
    public static void main(String[] args){
        int op;
        
        GESTHIPER.loadFichProdutos();
        GESTHIPER.loadFichClientes();
        
        boolean flag = true;
        double time = 0;
        
        while(flag)
        {
            GESTHIPER.menuLoad();
            boolean back = false; 
            
            op = GESTHIPER.lerInt();
            switch (op)
            {
                case 1:     //carrega ficheiro de compras
                {
                    String file ;
                    System.out.println("Nome do Ficheiro de Compras: ");
                    file = GESTHIPER.lerString();
                    Crono.start();
                    try{ GESTHIPER.loadFichCompras(file); }
                    catch (IOException e) {System.out.println(e.getMessage());}
                    time = Crono.stop();
                    System.out.println("TEMPO: "+time);

                    GESTHIPER.menuPrincial();
                    flag = false;
                      
                   
                    break;
                }
                case 2:     //carrega objeto
                {
                    Crono.start();
                    try { GESTHIPER.loadObj(); } 
                    catch (IOException e) {System.out.println(e.getMessage());}
                    catch (ClassNotFoundException ex) {
                        System.out.println("Ficheiro não é compativel: "+ex.getMessage());
                    }
                    time = Crono.stop();
                    System.out.println("TEMPO: "+time);
                    GESTHIPER.menuPrincial();
                    flag = false;
                    break;
                }
                case 0:
                {
                    flag = false;
                    GESTHIPER.mercado = null;
                    System.gc();
                    break;
                }    
                default: 
                {
                    System.out.println("Comanto errado...");
                    break;
                }
            }        
        }               
    }   
}
