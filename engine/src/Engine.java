package Engine;
import data.TradeData;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

import deal.SingleDealEntry;
import exception.*;
import fileLoader.CheckSchemeValid;
import fileLoader.XmlFileLoader;
import generated.RizpaStockExchangeDescriptor;
import generated.RseStock;
import stock.*;
import stock.TraderHoldingsMaker;
import trade.*;
import user.*;

//Singelton
public class Engine {
    private static Engine singleInstance = null;
    private StocksList stocksList;
    private UsersList usersList;


    private Engine() {
        stocksList = StocksList.getStocksList();
        usersList = UsersList.getUsersList();
    }

    public static Engine getEngine() {
        if (singleInstance == null) {
            singleInstance = new Engine();
        }
        return singleInstance;
    }

//stocks
    //ordinance
    //type = "MKT","LMT","FOK","IOC"
    public String saleOrdinance(String stockSymbol, int amount, int limit, String userName, String type) throws SymbolDidntExistException, OnlyPositiveNumberException, UserDidntExistException, UserDoesntHoldingTheStockException, UserDoesntHoldEnoughStocksException, TraderDidntExistException, NonTraderUserException, OrdinanceTypeDoesNotExistException {
        OrdinanceType ordinanceType = null;

        Trader trader = usersList.getTrader(userName);
        if (trader == null) {
            throw new TraderDidntExistException(userName);
        }


        if (type.equalsIgnoreCase("MKT") == true) {
            ordinanceType = OrdinanceType.MKT;
        } else if (type.equalsIgnoreCase("LMT") == true) {
            ordinanceType = OrdinanceType.LMT;
        }
     else if (type.equalsIgnoreCase("FOK") == true) {
        ordinanceType = OrdinanceType.FOK;
    }
 else if (type.equalsIgnoreCase("IOC") == true) {
        ordinanceType = OrdinanceType.IOC;
        }
        else{
            throw new OrdinanceTypeDoesNotExistException(type);
        }

        Stock stock = stocksList.getStockBySymbol(stockSymbol);
        if (stock == null) {
            throw new SymbolDidntExistException(stockSymbol);
        }

        if (trader.getHoldingAmount(stock) < amount) {
            throw new UserDoesntHoldEnoughStocksException(stockSymbol, userName, trader.getHoldingAmount(stock));
        } else if (amount <= 0) {
            throw new OnlyPositiveNumberException(amount);
        } else {
            return (new Trade(new Sale(stock, amount, limit, trader, ordinanceType))).toString();
        }
    }

    public String buyOrdinance(String stockSymbol, int amount, int limit, String userName, String type) throws SymbolDidntExistException, OnlyPositiveNumberException, UserDidntExistException, NonTraderUserException, UserDoesntHoldEnoughStocksException, UserDoesntHoldingTheStockException, OrdinanceTypeDoesNotExistException {
        Stock stock = stocksList.getStockBySymbol(stockSymbol);
        OrdinanceType ordinanceType = null;
        Trader trader = usersList.getTrader(userName);
        if (trader == null) {
            throw new UserDidntExistException(userName);
        }

        if (type.equalsIgnoreCase("MKT") == true) {
            ordinanceType = OrdinanceType.MKT;
        } else if (type.equalsIgnoreCase("LMT") == true) {
            ordinanceType = OrdinanceType.LMT;
        }     else if (type.equalsIgnoreCase("FOK") == true) {
            ordinanceType = OrdinanceType.FOK;
        }
        else if (type.equalsIgnoreCase("IOC") == true) {
            ordinanceType = OrdinanceType.IOC;
        }
        else{
            throw new OrdinanceTypeDoesNotExistException(type);
        }


        if (stock == null) {
            throw new SymbolDidntExistException(stockSymbol);
        } else if (amount <= 0) {
            throw new OnlyPositiveNumberException(amount);
        } else {
            return (new Trade(new Buy(stock, amount, limit, trader, ordinanceType))).toString();
        }
    }

    public int getHoldingsAmount(String userName, String stockSymbol) throws SymbolDidntExistException, UserDoesntHoldingTheStockException, TraderDidntExistException, UserDidntExistException, NonTraderUserException {
        Stock stock = stocksList.getStockBySymbol(stockSymbol);

        if (stock == null) {
            throw new SymbolDidntExistException(stockSymbol);
        } else {
            return usersList.getHoldingAmount(userName, stock);
        }
    }

    public void loadFile(InputStream inputStream, String userName) throws NonTraderUserException, UserDidntExistException, CompanyNameAlreadyExistException, SymbolAlreadyExistException, SymbolDidntExistException, SymbolExistOnlyInHoldingException {

        Trader trader = usersList.getTrader(userName);

        XmlFileLoader<RizpaStockExchangeDescriptor> xmlFile = new XmlFileLoader<RizpaStockExchangeDescriptor>(inputStream);

        RizpaStockExchangeDescriptor xmlInput = xmlFile.getObject();

        new CheckSchemeValid(xmlInput);

        new StocksListMaker(xmlInput);

        new TraderHoldingsMaker(xmlInput, trader);
    }

    public int getSalesRate(String symbol) throws SymbolDidntExistException {
        return stocksList.getSalesRate(symbol);
    }

    public int getBuyRate(String symbol) throws SymbolDidntExistException {
        return stocksList.getBuyRate(symbol);
    }

    //stocks
    public synchronized void makeIssue(String userName, String companyName, String symbol, int amountOfStocks, int valueOfCompany) throws SymbolAlreadyExistException, CompanyNameAlreadyExistException, NonTraderUserException, UserDidntExistException {
        Trader trader = usersList.getTrader(userName);

        Stock newStock = stocksList.addStock(symbol, companyName, valueOfCompany / amountOfStocks);

        trader.addAmount(newStock, amountOfStocks);
    }

    public boolean isSymbolExist(String stockSymbol) {
        return !(stocksList.getStockBySymbol(stockSymbol) == null);
    }

    //users

    //for user type = "trader"
    //for admin type = "admin"
    //in case of (type != "admin")&&(type != "user) the method throw exception
    public synchronized User addUser(String userName, String type) throws UserNameAlreadyExistException, UserTypeDidntExistException {
        User newUser = null;

        if (type.equals("trader")) {
            newUser = usersList.addTrader(userName);
        } else if (type.equals("admin")) {
            newUser = usersList.addAdmin(userName);
        } else {
            throw new UserTypeDidntExistException(type);
        }
        return newUser;
    }

    public void deposit(String traderName, int amount) throws NonTraderUserException, UserDidntExistException {
        usersList.deposit(traderName, amount);
    }

    public int getTraderBalance(String userName) throws NonTraderUserException, UserDidntExistException {
        return usersList.getTraderBalance(userName);
    }

    ///web
    public String getTraderInformation(String traderName) throws UserDidntExistException, NonTraderUserException {
        return usersList.getTraderInformation(traderName);
    }

    public List<SingleUserEntry> getUsersListEntries(int fromIndex) {
        List<SingleUserEntry> userListEntries = usersList.getListEntries();

        if (fromIndex < 0 || fromIndex > userListEntries.size()) {
            fromIndex = 0;
        }
        return userListEntries.subList(fromIndex, userListEntries.size());
    }

    public int getUserListVersion() {
        return usersList.getSize();
    }

    public List<SingleStockEntry> getStocksListEntries(int fromIndex) {
        List<SingleStockEntry> stocksListEntries = stocksList.getListEntries();

        if (fromIndex < 0 || fromIndex > stocksListEntries.size()) {
            fromIndex = 0;
        }
        return stocksListEntries.subList(fromIndex, stocksListEntries.size());
    }

    public int getStocksListVersion() {
        return stocksList.getSize();
    }

    public SingleStockEntry getSingleStockEntry(String stockSymbol) throws SymbolDidntExistException {
        return stocksList.getSingleStockEntry(stockSymbol);
    }

    public List<SingleDealEntry> getDealsListEntries(int fromIndex, String stockSymbol) throws SymbolDidntExistException {
        List<SingleDealEntry> dealsListEntries = stocksList.getDealsListEntries(stockSymbol);

        if (fromIndex < 0 || fromIndex > dealsListEntries.size()) {
            fromIndex = 0;
        }
        return dealsListEntries.subList(fromIndex, dealsListEntries.size());
    }

    public int getDealsListVersion(String stockSymbol) throws SymbolDidntExistException {
        return stocksList.getDealsListSize(stockSymbol);
    }

    public List<SingleTrafficEntry> getTraderTrafficsEntries(int fromIndex, String userName) throws UserDidntExistException, NonTraderUserException {

        List<SingleTrafficEntry> TrafficsListEntries = usersList.getTraderTrafficsEntries(userName);

        if (fromIndex < 0 || fromIndex > TrafficsListEntries.size()) {
            fromIndex = 0;
        }
        return TrafficsListEntries.subList(fromIndex, TrafficsListEntries.size());
    }

    public int getTrafficsListVersion(String userName) throws UserDidntExistException, NonTraderUserException {
        return usersList.getTrafficsListSize(userName);
    }
}

