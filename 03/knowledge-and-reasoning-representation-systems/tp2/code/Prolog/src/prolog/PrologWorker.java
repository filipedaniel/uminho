/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prolog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import se.sics.jasper.ConversionFailedException;
import se.sics.jasper.IllegalTermException;
import se.sics.jasper.SICStus;
import se.sics.jasper.SPException;
import se.sics.jasper.SPTerm;
import se.sics.jasper.Query;


/**
 *
 * @author Carlos Morais
 */
public class PrologWorker {
    
    private SICStus sicstus;
    
    public PrologWorker(String path) throws SPException{
        sicstus = new SICStus();
        sicstus.load(path);
    }
    
    
    private List<String> parseTermListAsString(SPTerm spt) throws IllegalTermException, ConversionFailedException{
        List<String> elementos = new ArrayList<>();
        SPTerm newSPT = new SPTerm(this.sicstus);
                
        while(!spt.isEmptyList()){
            spt.getList(newSPT, spt);
            elementos.add(newSPT.toString());
        }
        
        return elementos;
    }

    public String QueryQuestion(String q) throws SPException{
        HashMap map = new HashMap();
        boolean success = this.sicstus.query(q, map);

        if(success){
            Object R = null;
            for(Object aux : map.keySet())
                R = aux;
            
            SPTerm distTerm = (SPTerm) map.get(R);            
            return distTerm.toString();     
        }
        else
            return null;
    }
    
    public List<String> QueryQuestionList(String q) throws SPException{
        HashMap map = new HashMap();
        boolean success = this.sicstus.query(q, map);
        
        if(success){
            Object R = null;
            for(Object aux : map.keySet())
                R = aux;
                
            List<String> nodes = parseTermListAsString((SPTerm) map.get(R));
            return nodes;    
        }
        else
            return null;
    }
    
    public boolean Query(String q) throws SPException{
        boolean success = this.sicstus.query(q, null);

        if(success)
            return true;
        else 
            return false;                  
    }
    
    //nao é um predicado implementado na Base de Conhecimento!
    public String infoMatricula(String matricula) throws SPException{
        List<String> respostas;
        String resposta;
        StringBuilder res = new StringBuilder();
        HashMap map = new HashMap();
        
        
        //Automovel
        if (this.sicstus.query("automovel("+matricula+",C,Ma,Mo).", map)){    
            res.append("Automovel: construtor=");
            res.append(map.get("C").toString());

            res.append(", marca=");
            res.append(map.get("Ma").toString());

            res.append(", modelo=");
            res.append(map.get("Ma").toString());
            res.append(";\n");
        }
        map.clear();
        
        //proprietario
        if(this.sicstus.query("proprietario("+matricula+",R).", map)){
            res.append("Proprietario: ");
            res.append(map.get("R").toString());
            res.append(";\n");
        }
        map.clear();
        
        //estado
        if(this.sicstus.query("estado("+matricula+",R).", map)){        
            res.append("Estado: ");
            res.append(map.get("R").toString());
            res.append(";\n");
        }
        map.clear();        
        
        //cor
        if(this.sicstus.query("cor("+matricula+",R).", map)){        
            res.append("Cor: ");
            res.append(map.get("R").toString());
            res.append(";\n");
        }
        map.clear();
        
        //registos
        if(this.sicstus.query("listareg("+matricula+",R).", map)){        
            List<String> nodes = parseTermListAsString((SPTerm) map.get("R"));
            
            if(nodes.size()>0){
                res.append("Registos: ");            
                for(String aux:nodes)
                    res.append("->"+aux+"\n");
                res.append("\n");
            }
        }
        map.clear();
                        
        return res.toString();
    }    
    
}
