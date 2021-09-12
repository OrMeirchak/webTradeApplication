package user;

import exception.NegativeHoldingException;
import exception.UserDoesntHoldEnoughStocksException;
import exception.UserDoesntHoldingTheStockException;
import stock.Stock;

import java.util.*;

public class Trader extends User {
    Collection<Holding> holdings;
    Collection<Traffic> traffics;
    int budget;

    public Trader(String name) {
        super(name);
        this.holdings=new HashSet<Holding>();
        this.traffics=new LinkedHashSet<Traffic>();
        this.budget=0;
    }

    public void deposit(int amount){

        int budgetBefore=this.budget;

        addMoneyToBudget(amount);

        int budgetAfter=this.budget;

        traffics.add(new Traffic("Deposit",amount,budgetBefore,budgetAfter));

    }

    public void addMoneyToBudget(int amount){
        this.budget+=amount;
    }

    public void subMoneyFromBudget(int amount){
        this.budget-=amount;
    }

    public int getBalance(){
        return budget;
    }

    int getTotalValue(){
        int res=0;
        for(Holding holding:holdings){
            res+=(holding.getTotalValue());
        }
        return res;
    }

    public SingleUserEntry getUserEntry(){
        return new SingleUserEntry(NAME,"Trader");
    }

    Collection<Holding> getHoldingData(){
        return holdings;
    }

    public void saleStockDeal(Stock stock, int amount,int transactionValue) throws UserDoesntHoldEnoughStocksException, UserDoesntHoldingTheStockException {
        int budgetBefore=this.budget;

        subAmount(stock,amount);
        addMoneyToBudget(transactionValue);

        int budgetAfter=this.budget;

        traffics.add(new Traffic("Sale stock "+" (symbol = "+stock.getSYMBOL()+')' ,transactionValue,budgetBefore,budgetAfter));
    }

    public void buyStockDeal(Stock stock, int amount,int transactionValue)  {
        int budgetBefore=this.budget;

        addAmount(stock,amount);
subMoneyFromBudget(transactionValue);

        int budgetAfter=this.budget;

        traffics.add(new Traffic("Buy stock "+" (symbol = "+stock.getSYMBOL()+')' ,(-1)*transactionValue,budgetBefore,budgetAfter));
    }

    public void subAmount(Stock stock, int amount) throws UserDoesntHoldingTheStockException, UserDoesntHoldEnoughStocksException {
        for (Holding holding:holdings) {
            if (holding.getSTOCK() == stock) {
                try {
                    holding.subAmount(amount);
                } catch (NegativeHoldingException e) {
                    throw new UserDoesntHoldEnoughStocksException(e.getStockSymbol(),this.NAME,e.getHoldAmount());
                }
                return;
            }
        }
        throw new UserDoesntHoldingTheStockException(stock.getSYMBOL(),this.NAME);
    }

    public int getHoldingAmount(Stock stock) throws UserDoesntHoldingTheStockException {
        for (Holding holding : holdings) {
            if (holding.getSTOCK() == stock) {
                return holding.getAmount();
            }
        }
        return 0;
    }

    public void addAmount(Stock stock, int amount){
        for (Holding holding:holdings){
            if(holding.getSTOCK()==stock){
                holding.addAmount(amount);
                return;
            }
        }
        holdings.add(new Holding(stock,amount));
    }

    //the function return hashset with the names of stocks symbol of the user
    public   HashSet<String> getHoldingsSymbols(){
        HashSet<String> res=new HashSet<String>();

        for (Holding holding: holdings){
            res.add(holding.getStockSymbol());
        }
        return res;
    }

    public List<SingleTrafficEntry> getTrafficsEntries(){
        List<SingleTrafficEntry> res=new ArrayList<SingleTrafficEntry>();

        for (Traffic traffic:traffics){
            res.add(traffic.getSingleTrafficEntry());
        }
        return res;
    }

    public int getTrafficsListSize(){
        return traffics.size();
    }
}
