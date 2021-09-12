package trade;
import interfaces.Stringable;
import stock.Stock;
import user.Trader;

public class Buy extends Ordinance implements Comparable<Buy>,Stringable {

    public Buy(Stock stock, int amount, int limit, Trader trader, OrdinanceType type) {
      super(stock,amount,limit,trader,type);
    }

    @Override
    public int compareTo(Buy o) {
        if(super.getLimit() - o.getLimit()!=0){
            return o.getLimit() - super.getLimit();
        }
        else{
            return (super.getDate()).compareTo(o.getDate());
        }
    }



    }

