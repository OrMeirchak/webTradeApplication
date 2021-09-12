package stock;
import exception.CompanyNameAlreadyExistException;
import exception.SymbolAlreadyExistException;
import generated.*;
import java.util.List;

public class StocksListMaker {

   private RizpaStockExchangeDescriptor xmlInput;
   private StocksList stocksListFromEngine;

   public StocksListMaker(RizpaStockExchangeDescriptor stocksListFromXml) throws CompanyNameAlreadyExistException, SymbolAlreadyExistException {
        this.xmlInput=stocksListFromXml;
        this.stocksListFromEngine=StocksList.getStocksList();
        makeStocksList();
    }

    private void makeStocksList() throws CompanyNameAlreadyExistException, SymbolAlreadyExistException {
        List<RseStock> rseStockList=  xmlInput.getRseStocks().getRseStock();

        for (RseStock rseStock:rseStockList){
            addStock(rseStock);
        }
    }

    private void addStock(RseStock rseStock)  {
       try {
           stocksListFromEngine.addStock(rseStock.getRseSymbol(), rseStock.getRseCompanyName(), rseStock.getRsePrice());
       }
       catch(SymbolAlreadyExistException|CompanyNameAlreadyExistException e){

       }
    }
}
