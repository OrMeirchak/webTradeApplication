package stock;
import java.util.*;

import data.TradeData;
import deal.Deal;
import deal.SingleDealEntry;
import exception.*;
import trade.Ordinance;
import user.SingleUserEntry;
import user.User;
import user.UsersList;

public class StocksList {
    private Collection<Stock> stocks;

    private static StocksList singleInstance=null;

    public static StocksList getStocksList(){
        if(singleInstance==null){
            singleInstance=new StocksList();
        }
        return singleInstance;
    }

    //constructor
    private StocksList(){
        stocks = new LinkedHashSet<Stock>();
    }

    //set to protected
    public Stock addStock(String SYMBOL, String companyName, int currentPrice) throws SymbolAlreadyExistException, CompanyNameAlreadyExistException {
        if(getStockBySymbol(SYMBOL)!=null){
            throw new SymbolAlreadyExistException(SYMBOL);
        }
        else if(getStockByCompanyName(companyName)!=null){
            throw new CompanyNameAlreadyExistException(companyName);
        }
        else {
            Stock newStock=new Stock(SYMBOL, companyName, currentPrice);
            stocks.add(newStock);
            return newStock;
        }
    }

    //get
    public List<SingleStockEntry> getListEntries(){
        List<SingleStockEntry> res=new ArrayList<SingleStockEntry>();

        for (Stock stock:stocks){
            res.add(stock.getStockEntry());
        }
        return res;
    }

    public SingleStockEntry getSingleStockEntry(String symbol) throws SymbolDidntExistException {
        Stock stock=getStockBySymbol(symbol);
        if(stock==null){
            throw new SymbolDidntExistException(symbol);
        }
        else{
            return stock.getStockEntry();
        }
    }

    public List<SingleDealEntry> getDealsListEntries(String stockSymbol) throws SymbolDidntExistException {
    Stock stock=getStockBySymbol(stockSymbol);
    if(stock==null){
        throw new SymbolDidntExistException(stockSymbol);
    }
    else{
        return stock.getDealsListEntries();
    }
    }

    public int getDealsListSize(String stockSymbol) throws SymbolDidntExistException {
        Stock stock=getStockBySymbol(stockSymbol);

        if(stock==null){
            throw new SymbolDidntExistException(stockSymbol);
        }
        else{
            return stock.getDealsListSize();
        }
    }

    //if didnt found return null
    public Stock getStockBySymbol(String SYMBOL) {
        if(stocks.isEmpty()) {
            return null;
        }
        else {
            String SymbolToUpperCase=SYMBOL.toUpperCase(Locale.ROOT);
            for (Stock stock:stocks) {
                if (stock.getSYMBOL().equals(SymbolToUpperCase)==true) {
                    return stock;
                }}}
        return null;
    }

    //if didnt find return null
    private Stock getStockByCompanyName(String companyName) {
        if(stocks.isEmpty()) {
            return null;
        }
        else {
            for (Stock stock : stocks) {
                if (stock.getCompanyName().toLowerCase(Locale.ROOT).equals(companyName.toLowerCase(Locale.ROOT))) {
                    return stock;
                } } }
        return null;
    }

    public int getSalesRate(String symbol) throws SymbolDidntExistException {
        Stock stock=getStockBySymbol(symbol);
        if (stock==null){
            throw new SymbolDidntExistException(symbol);
        }
        else{
            return stock.getSalesRate();
        }
    }

    public int getBuyRate(String symbol) throws SymbolDidntExistException {
        Stock stock=getStockBySymbol(symbol);
        if (stock==null){
            throw new SymbolDidntExistException(symbol);
        }
        else{
            return stock.getBuyRate();
        }
    }

    public int getSize() {
        return stocks.size();
    }
}
