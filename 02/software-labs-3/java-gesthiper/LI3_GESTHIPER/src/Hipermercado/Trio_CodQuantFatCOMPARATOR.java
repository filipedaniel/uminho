package Hipermercado;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author fdr
 * Comparator da class Trio_CodQuantFat
 */
public class Trio_CodQuantFatCOMPARATOR implements Comparator<Trio_CodQuantFat> {

    /**
     * Ordena pela quantidade de compras(maior para menor)
     */
    @Override
    public int compare(Trio_CodQuantFat o1, Trio_CodQuantFat o2) {
      
        if(o1.getQuantidade() > o2.getQuantidade())
            return -1;
        else 
            if(o1.getQuantidade() < o2.getQuantidade())
                    return 1;
            else    
                if(o1.getFaturacao() > o2.getFaturacao())
                    return -1;
                else if(o1.getFaturacao() < o2.getFaturacao())
                        return 1;
                     else 
                            return o1.getCodigo().compareTo(o2.getCodigo());        
    }
}
