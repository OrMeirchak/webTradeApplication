package stock;
import exception.CompanyNameAlreadyExistException;
import exception.SymbolAlreadyExistException;
import exception.SymbolDidntExistException;
import generated.*;
import user.Trader;
import user.User;
import user.UsersList;

import java.util.List;

public class TraderHoldingsMaker {

    private RizpaStockExchangeDescriptor xmlInput;
    private UsersList userListFromEngine;
    private StocksList stocksListFromEngine;
    private Trader trader;

    public TraderHoldingsMaker(RizpaStockExchangeDescriptor stocksListFromXml, Trader trader) throws CompanyNameAlreadyExistException, SymbolAlreadyExistException, SymbolDidntExistException {
        this.xmlInput=stocksListFromXml;
        this.userListFromEngine=UsersList.getUsersList();
        this.stocksListFromEngine=StocksList.getStocksList();
this.trader=trader;

        makeTraderHolding();
    }

    private void makeTraderHolding() throws SymbolDidntExistException {
        List<RseItem> rseHoldingsList=  xmlInput.getRseHoldings().getRseItem();

        for (RseItem rseItem:rseHoldingsList){
            Stock stock=stocksListFromEngine.getStockBySymbol(rseItem.getSymbol());
            if(stock==null){
                throw new SymbolDidntExistException(rseItem.getSymbol());
            }
            trader.addAmount(stock,rseItem.getQuantity());
        }
    }
}
