package stock;

import data.TradeData;
import deal.Deal;
import deal.Deals;
import deal.SingleDealEntry;
import trade.*;
import javafx.collections.ObservableList;
import user.SingleUserEntry;
import user.Trader;

import java.util.*;

public class Stock {
    private final String SYMBOL;
    private String companyName;
    private int currentPrice;
    private int totalTurnover;
    private Collection<Deal> deals;
    private OrdersStack<Sale> saleOrders;
    private OrdersStack<Buy> buyOrders;

    //constructor
    public Stock(String symbol, String companyName, int currentPrice) {
        this.SYMBOL = symbol.toUpperCase(Locale.ROOT);
        this.companyName = companyName;
        this.currentPrice = currentPrice;
        this.totalTurnover=0;
        this.deals=new LinkedList<Deal>();
        this.saleOrders=new OrdersStack<Sale>();
        this.buyOrders=new OrdersStack<Buy>();
    }

    //getters
    public String getSYMBOL() {
        return SYMBOL;
    }

    public String getCompanyName() {
        return companyName;
    }

    public OrdersStack<Sale> getSaleOrdersStack() {
        return saleOrders;
    }

    public OrdersStack<Buy> getBuyOrdersStack() {
        return buyOrders;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getSalesRate(){
        return buyOrders.getRate();
    }

    public int getBuyRate(){
        return saleOrders.getRate();
    }

    //add
    public void addDeals(Collection<Deal> deals){
        this.deals.addAll(deals);
        updatePrice();
        updateTurnOver();
    }

    public void addOrdinance(Ordinance ordinance){
        if(ordinance instanceof Sale){
            saleOrders.push((Sale)ordinance);
        }
        else if(ordinance instanceof Buy){
            buyOrders.push((Buy)ordinance);
        }

    }

    public SingleStockEntry getStockEntry(){
        return new SingleStockEntry(companyName,SYMBOL,currentPrice,totalTurnover);
    }

    public List<SingleDealEntry> getDealsListEntries(){
        List<SingleDealEntry> res=new ArrayList<SingleDealEntry>();

        for (Deal deal:deals){
            res.add(deal.getSingleDealEntry());
        }
        return res;
    }

    public int getDealsListSize(){
        return deals.size();
    }

    @Override
    public String toString() {
        return "Stock:" +
                "\nSYMBOL=" + SYMBOL +
                "\nCompany name=" + companyName +
                "\nCurrent price=" + currentPrice +
                "\nHow many deals was be done=" + deals.size() +
                "\nTransaction turn over that done in the stock=" + totalTurnover +
                '\n';
    }

    private void updatePrice() {
        if (!deals.isEmpty()) {
            deals.stream().sorted(Deal::compareTo);
            for (Deal deal:deals){
                this.currentPrice=deal.getPrice();
            }
        }
    }

    private void updateTurnOver() {
       int numToAdd=0;
            for (Deal deal:deals){
               numToAdd+=deal.getValue();
            }
            this.totalTurnover+=numToAdd;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Float.compare(stock.currentPrice, currentPrice) == 0 && Float.compare(stock.totalTurnover, totalTurnover) == 0 && Objects.equals(SYMBOL, stock.SYMBOL) && Objects.equals(companyName, stock.companyName) && Objects.equals(deals, stock.deals) && Objects.equals(saleOrders, stock.saleOrders) && Objects.equals(buyOrders, stock.buyOrders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(SYMBOL, companyName, currentPrice, totalTurnover, deals, saleOrders, buyOrders);
    }
}
