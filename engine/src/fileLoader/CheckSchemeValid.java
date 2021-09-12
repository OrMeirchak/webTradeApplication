package fileLoader;

import exception.SymbolExistOnlyInHoldingException;
import generated.RizpaStockExchangeDescriptor;
import generated.RseItem;
import generated.RseStock;
import stock.Stock;
import stock.StocksList;

import java.util.ArrayList;
import java.util.List;

public class CheckSchemeValid {
    private final RizpaStockExchangeDescriptor SCHEME;
    private List<StockNameAndSymbol> stocksNameAndSymbol;

   public CheckSchemeValid(RizpaStockExchangeDescriptor scheme) throws SymbolExistOnlyInHoldingException {
        this.SCHEME=scheme;
        stocksNameAndSymbol=new ArrayList<StockNameAndSymbol>();
       check();
    }

    private void check() throws SymbolExistOnlyInHoldingException {
        List<RseStock> rseStockList=  SCHEME.getRseStocks().getRseStock();
int x=8;

        for (RseStock rseStock:rseStockList){
           Stock stock= StocksList.getStocksList().getStockBySymbol(rseStock.getRseSymbol());
            stocksNameAndSymbol.add(new StockNameAndSymbol(rseStock.getRseCompanyName(), rseStock.getRseSymbol()));
        }

        List<RseItem> rseHoldingsList=  SCHEME.getRseHoldings().getRseItem();

        for (RseItem rseItem:rseHoldingsList){
           if (!checkIfExist(rseItem)){
               throw new SymbolExistOnlyInHoldingException(rseItem.getSymbol());
           }
        }
    }


    private boolean checkIfExist(RseItem rseItem){

        for (StockNameAndSymbol stockNameAndSymbol:stocksNameAndSymbol){
         if(stockNameAndSymbol.getSymbol().equals(rseItem.getSymbol())){
             return true;
         }
        }
        return false;
    }

    class StockNameAndSymbol{

       private final String NAME;
       private final String SYMBOL;

        public StockNameAndSymbol(String name, String symbol){
            NAME = name;
            SYMBOL = symbol;
        }

        private String getSymbol(){
            return SYMBOL;
        }

        private String getName(){
          return NAME;
        }
    }

}




