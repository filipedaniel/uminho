/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prolog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import se.sics.jasper.SPException;

/**
 *
 * @author Carlos Morais
 */
public class Prolog {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws se.sics.jasper.SPException
     */
    public static void main(String[] args) throws IOException, SPException {
        boolean continua = true, found;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String leitura;
        PrologWorker prolog;
        
        prolog = new PrologWorker("TP2_SRCR.pl");       
        
        while(continua){
            found = false;
            leitura = br.readLine();
            
            //removes all whitespaces and non visible characters such as tab,\n
            leitura.replaceAll("\\s+","");            
            
            if(leitura.startsWith("demo(")){
                found = true;
                String resposta = prolog.QueryQuestion(leitura);
                if(resposta!=null)
                    System.out.println("R: a resposta ao demo é: "+resposta);
                else
                    System.out.println("R: Erro no predicado!");
                    
            }
            
            if(leitura.startsWith("demoLista(")){  
                found = true;
                List<String> respostas = prolog.QueryQuestionList(leitura);
                
                if(respostas!=null){
                    if(respostas.size() > 0){
                        System.out.println("as respostas ao demoLista são: ");
                        for(String res:respostas)
                            System.out.println("->"+res);
                    }
                    else
                        System.out.println("R: não existem respostas!");
                }
                else
                    System.out.println("R: Erro no predicado!");
            }
            
            if(leitura.startsWith("demoLogico(")){                           
                found = true;
                String resposta = prolog.QueryQuestion(leitura);
                
                if(resposta!=null)
                    System.out.println("a resposta ao demoLogico é: "+resposta);
                else
                    System.out.println("R: Erro no predicado!");                                
            }
            
            if(leitura.startsWith("evolucao(")){  
                found = true;
                if(prolog.Query(leitura))
                    System.out.println("R: YES!");
                else
                    System.out.println("R: NO!");
            }
            
            if(leitura.startsWith("evolucaoAutomovelNulo(")){  
                found = true;
                if(prolog.Query(leitura))
                    System.out.println("R: YES!");
                else
                    System.out.println("R: NO!");
            }
            
            if(leitura.startsWith("remocao(")){
                found = true;
                if(prolog.Query(leitura))
                    System.out.println("R: YES!");
                else
                    System.out.println("R: NO!");                
            }

            
            if(leitura.startsWith("modeloPro(")){                           
                found = true;
                List<String> respostas = prolog.QueryQuestionList(leitura);
                if(respostas!=null){
                    if(respostas.size() > 0){
                        System.out.println("R: os modelos do proprietario sao:");
                        for(String res:respostas)
                            System.out.println("->"+res);
                    }
                    else
                        System.out.println("R: não existem modelos!");
                }
                else
                    System.out.println("R: Erro no predicado!");
            }
            
            if(leitura.startsWith("automoveisMarca(")){   
                found = true;
                List<String> respostas = prolog.QueryQuestionList(leitura);
                if(respostas!=null){
                    if(respostas.size() > 0){
                        System.out.println("R: os automoveis da marca são:");
                        for(String res:respostas)
                            System.out.println("->"+res);
                    }
                    else
                        System.out.println("R: não existem automoveis!");
                }
                else
                    System.out.println("R: Erro no predicado!");
            }
            
            if(leitura.startsWith("automovel(")){ 
                found = true;
                if(prolog.Query(leitura))
                    System.out.println("R: YES!");
                else
                    System.out.println("R: NO!");
            }
            
            if(leitura.startsWith("atualizarCor(")){ 
                found = true;
                if(prolog.Query(leitura))
                    System.out.println("R: YES!");
                else
                    System.out.println("R: NO!");
            }
            
            
            if(leitura.startsWith("atualizarProprietario(")){   
                found = true;
                if(prolog.Query(leitura))
                    System.out.println("R: YES!");
                else
                    System.out.println("R: NO!");
            }
            
            if(leitura.startsWith("listareg(")){   
                found = true;
                List<String> respostas = prolog.QueryQuestionList(leitura);
                if(respostas!=null){
                    if(respostas.size() > 0){
                        System.out.println("R: os registos são:");
                        for(String res:respostas)
                            System.out.println("->"+res);
                    }
                    else
                        System.out.println("R: não existem registos!");
                }
                else
                    System.out.println("R: Erro no predicado!");
            }
            
            if(leitura.startsWith("exit")){
                found = true;
                continua = false;
            }
            
            //apresenta toda a informaçao na base de conhecimento para uma matricula
            if(leitura.startsWith("infoMatricula(")){
                found = true;
                
                //matricula
                String mat = leitura.substring(14,(leitura.length()-2));
                
                // se a matricula existe
                if(prolog.Query("matricula("+mat+").")){
                    String resposta = prolog.infoMatricula(mat);
                    if(resposta.length()>0)
                        System.out.println(resposta);
                    else
                        System.out.println("R: sem informação!");
                }           
                else
                    System.out.println("R: Não reconhece o a matricula!");                    
            }
            
            //nao reconhece o predicado
            if(!found){
                System.out.println("R: Não reconhece o predicado!");
            }
                                  
         
        }
    }
    
}
