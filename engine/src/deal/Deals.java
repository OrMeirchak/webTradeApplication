package deal;

import data.TradeData;
import trade.Ordinance;

import java.util.ArrayList;
import java.util.Collection;

abstract public class Deals {

    public static String toString(Collection<Deal> deals){
        String res="";
        int totalTurnover=0;

        if(deals.isEmpty()){
            res="No trades were made for this stock";
        }
        else {
            deals.stream().sorted(Deal::compareTo);
            for (Deal deal : deals) {
                res += '\n'+deal.toString();
                totalTurnover+=deal.getValue();
            }

            res+="\nTotal turnover for the list ="+String.valueOf(totalTurnover);
        }
        return res;

    }

}
