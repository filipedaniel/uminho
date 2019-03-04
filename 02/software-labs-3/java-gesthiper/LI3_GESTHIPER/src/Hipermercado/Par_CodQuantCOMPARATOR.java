package Hipermercado;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author fdr
 * Comparator da class Par_CodQuantC
 */
public class Par_CodQuantCOMPARATOR implements Comparator<Par_CodQuant> {

    //Ordena por quantidade, maior para o menor
    @Override
    public int compare(Par_CodQuant o1, Par_CodQuant o2) {
       
        if(o1.getQuantidade() > o2.getQuantidade()) 
            return -1;
        else 
            if(o1.getQuantidade() < o2.getQuantidade())
                return 1;
                else
                    return o1.getCodigo().compareTo(o2.getCodigo());
    }
    
}
