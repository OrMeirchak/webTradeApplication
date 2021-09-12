package trade;

import deal.Deal;
import deal.Deals;
import exception.UserDoesntHoldEnoughStocksException;
import exception.UserDoesntHoldingTheStockException;
import stock.OrdersStack;
import stock.Stock;
import user.Trader;
import user.UsersList;

import java.util.*;

public class Trade {

    private Ordinance ordinance;
    private Collection<Deal> deals;
    private UsersList usersList;
    final private Stock STOCK;
    final private String ordinanceType;

    public Trade(Ordinance ordinance) throws UserDoesntHoldingTheStockException, UserDoesntHoldEnoughStocksException {
        this.ordinance = ordinance;
        this.deals = new LinkedList<Deal>();
        this.STOCK = ordinance.getStock();
        this.ordinanceType=ordinance.getClass().toString().toLowerCase(Locale.ROOT);
        this.usersList=UsersList.getUsersList();

        makeTrade();
    }

    private void makeTrade() throws UserDoesntHoldEnoughStocksException, UserDoesntHoldingTheStockException {
        if (ordinance instanceof Sale) {
            if (ordinance.getType() == OrdinanceType.LMT) {
                LMTSaleOrdinance();
                handlingTheRestOfTheOrdinance();
            } else if (ordinance.getType() == OrdinanceType.MKT) {
                MKTSaleOrdinance();
                handlingTheRestOfTheOrdinance();
            }
              else if (ordinance.getType() == OrdinanceType.FOK) {
                  if(checkIfFokIsPossible()) {
                      LMTSaleOrdinance();
                  }
            }
              else if (ordinance.getType() == OrdinanceType.IOC) {
                LMTSaleOrdinance();
           }
        }

        else if (ordinance instanceof Buy) {
            if (ordinance.getType() == OrdinanceType.LMT) {
                LMTBuyOrdinance();
                handlingTheRestOfTheOrdinance();
            } else if (ordinance.getType() == OrdinanceType.MKT) {
                MKTBuyOrdinance();
                handlingTheRestOfTheOrdinance();
            }
            else if (ordinance.getType() == OrdinanceType.FOK) {
                if(checkIfFokIsPossible()) {
                    LMTBuyOrdinance();
                }
            }
            else if (ordinance.getType() == OrdinanceType.IOC) {
                LMTBuyOrdinance();
            }
        }

        if(!deals.isEmpty()){
        STOCK.addDeals(this.deals);
    }
    }

    private void LMTSaleOrdinance() throws UserDoesntHoldingTheStockException, UserDoesntHoldEnoughStocksException {

        if (STOCK.getBuyOrdersStack().isEmpty())
        {
            return;
        }

        Ordinance buyOrdinance=STOCK.getBuyOrdersStack().top();
        Ordinance saleOrdinance=ordinance;

       final int BUY_ORDINANCE_AMOUNT = buyOrdinance.getAmount();
        final int BUY_ORDINANCE_LIMIT = buyOrdinance.getLimit();
        final Trader BUYER=buyOrdinance.getUser();

        final int SALE_ORDINANCE_AMOUNT = saleOrdinance.getAmount();
       final int SALE_ORDINANCE_LIMIT = saleOrdinance.getLimit();
        final Trader SELLER=saleOrdinance.getUser();

        if (SALE_ORDINANCE_LIMIT > BUY_ORDINANCE_LIMIT) {
            return;
        }
        else {
            if (BUY_ORDINANCE_AMOUNT > SALE_ORDINANCE_AMOUNT) {

                Deal newDeal=new Deal(SALE_ORDINANCE_AMOUNT, BUY_ORDINANCE_LIMIT,SELLER,BUYER, STOCK);

                deals.add(newDeal);

                buyOrdinance.subtractAmount(SALE_ORDINANCE_AMOUNT);

                saleOrdinance.setAmount(0);

                usersList.addUserInform(new UserInforms(BUYER,newDeal,buyOrdinance));

            } else if (BUY_ORDINANCE_AMOUNT < SALE_ORDINANCE_AMOUNT) {
                Deal newDeal=new Deal(BUY_ORDINANCE_AMOUNT, BUY_ORDINANCE_LIMIT,SELLER,BUYER, STOCK);

                deals.add(newDeal);

                saleOrdinance.subtractAmount(BUY_ORDINANCE_AMOUNT);

                STOCK.getBuyOrdersStack().pop();

                usersList.addUserInform(new UserInforms(BUYER,newDeal));

                LMTSaleOrdinance();
            } else {
                Deal newDeal=new Deal(BUY_ORDINANCE_AMOUNT, BUY_ORDINANCE_LIMIT,SELLER,BUYER, STOCK);

                deals.add(newDeal);

                STOCK.getBuyOrdersStack().pop();

                saleOrdinance.setAmount(0);

                usersList.addUserInform(new UserInforms(BUYER,newDeal));
            }
        }
    }

    private void MKTSaleOrdinance() throws UserDoesntHoldingTheStockException, UserDoesntHoldEnoughStocksException {

        if (STOCK.getBuyOrdersStack().isEmpty()) {
            return;
        }

        Ordinance buyOrdinance=STOCK.getBuyOrdersStack().top();
        Ordinance saleOrdinance=ordinance;

        final int BUY_ORDINANCE_AMOUNT = buyOrdinance.getAmount();
        final int BUY_ORDINANCE_LIMIT = buyOrdinance.getLimit();
        final Trader BUYER=buyOrdinance.getUser();


        final int SALE_ORDINANCE_AMOUNT = saleOrdinance.getAmount();
        final Trader SELLER=saleOrdinance.getUser();


            if (BUY_ORDINANCE_AMOUNT > SALE_ORDINANCE_AMOUNT) {


               Deal newDeal=new Deal(SALE_ORDINANCE_AMOUNT, BUY_ORDINANCE_LIMIT,SELLER,BUYER, STOCK);
                deals.add(newDeal);

                buyOrdinance.subtractAmount(SALE_ORDINANCE_AMOUNT);

                usersList.addUserInform(new UserInforms(BUYER,newDeal,buyOrdinance));

                saleOrdinance.setAmount(0);

            } else if (BUY_ORDINANCE_AMOUNT < SALE_ORDINANCE_AMOUNT) {

                Deal newDeal=new Deal(BUY_ORDINANCE_AMOUNT, BUY_ORDINANCE_LIMIT,SELLER,BUYER, STOCK);

                deals.add(newDeal);

                saleOrdinance.subtractAmount(BUY_ORDINANCE_AMOUNT);

                STOCK.getBuyOrdersStack().pop();

                usersList.addUserInform(new UserInforms(BUYER,newDeal));

                MKTSaleOrdinance();
            } else {
                Deal newDeal=new Deal(BUY_ORDINANCE_AMOUNT, BUY_ORDINANCE_LIMIT,SELLER,BUYER, STOCK);

                deals.add(newDeal);

                STOCK.getBuyOrdersStack().pop();

                usersList.addUserInform(new UserInforms(BUYER,newDeal));

                saleOrdinance.setAmount(0);
            }
        }

    private void LMTBuyOrdinance() throws UserDoesntHoldingTheStockException, UserDoesntHoldEnoughStocksException {

        if (STOCK.getSaleOrdersStack().isEmpty()) {
            return;
        }
        Ordinance saleOrdinance=STOCK.getSaleOrdersStack().top();
        Ordinance buyOrdinance=ordinance;

        final int BUY_ORDINANCE_AMOUNT = buyOrdinance.getAmount();
        final int BUY_ORDINANCE_LIMIT = buyOrdinance.getLimit();
        final Trader BUYER=buyOrdinance.getUser();

        final int SALE_ORDINANCE_AMOUNT = saleOrdinance.getAmount();
        final int SALE_ORDINANCE_LIMIT = saleOrdinance.getLimit();
        final Trader SELLER=saleOrdinance.getUser();

        if (SALE_ORDINANCE_LIMIT > BUY_ORDINANCE_LIMIT) {
            return;
        }
        else {
            if (BUY_ORDINANCE_AMOUNT > SALE_ORDINANCE_AMOUNT) {

                Deal newDeal=new Deal(SALE_ORDINANCE_AMOUNT, SALE_ORDINANCE_LIMIT,SELLER,BUYER, STOCK);
                deals.add(newDeal);
                buyOrdinance.subtractAmount(SALE_ORDINANCE_AMOUNT);
                STOCK.getSaleOrdersStack().pop();
                usersList.addUserInform(new UserInforms(SELLER,newDeal));
                LMTBuyOrdinance();
            }
            else if (BUY_ORDINANCE_AMOUNT < SALE_ORDINANCE_AMOUNT) {
                Deal newDeal=new Deal(BUY_ORDINANCE_AMOUNT, SALE_ORDINANCE_LIMIT,SELLER,BUYER, STOCK);
                    deals.add(newDeal);
                    saleOrdinance.subtractAmount(BUY_ORDINANCE_AMOUNT);
                usersList.addUserInform(new UserInforms(SELLER,newDeal,saleOrdinance));
                    buyOrdinance.setAmount(0);
                }
            else{
                Deal newDeal=new Deal(SALE_ORDINANCE_AMOUNT, SALE_ORDINANCE_LIMIT,SELLER,BUYER, STOCK);
                    deals.add(newDeal);
                    buyOrdinance.setAmount(0);
                STOCK.getSaleOrdersStack().pop();
                usersList.addUserInform(new UserInforms(SELLER,newDeal));
                } } }

    private void MKTBuyOrdinance() throws UserDoesntHoldingTheStockException, UserDoesntHoldEnoughStocksException {

        if (STOCK.getSaleOrdersStack().isEmpty()) {
            return;
        }
        Ordinance saleOrdinance=STOCK.getSaleOrdersStack().top();
        Ordinance buyOrdinance=ordinance;

        final int BUY_ORDINANCE_AMOUNT = buyOrdinance.getAmount();
        final Trader BUYER=buyOrdinance.getUser();

        final int SALE_ORDINANCE_AMOUNT = saleOrdinance.getAmount();
        final int SALE_ORDINANCE_LIMIT = saleOrdinance.getLimit();
        final Trader SELLER=saleOrdinance.getUser();

        if (BUY_ORDINANCE_AMOUNT > SALE_ORDINANCE_AMOUNT) {
            Deal newDeal=new Deal(SALE_ORDINANCE_AMOUNT, SALE_ORDINANCE_LIMIT,SELLER,BUYER, STOCK);
                deals.add(newDeal);
                buyOrdinance.subtractAmount(SALE_ORDINANCE_AMOUNT);
                STOCK.getSaleOrdersStack().pop();
            usersList.addUserInform(new UserInforms(SELLER,newDeal));
                MKTBuyOrdinance();
            }
            else if (BUY_ORDINANCE_AMOUNT < SALE_ORDINANCE_AMOUNT) {
                Deal newDeal=new Deal(BUY_ORDINANCE_AMOUNT, SALE_ORDINANCE_LIMIT,SELLER,BUYER, STOCK);
                deals.add(newDeal);
                saleOrdinance.subtractAmount(BUY_ORDINANCE_AMOUNT);
                buyOrdinance.setAmount(0);
            usersList.addUserInform(new UserInforms(SELLER,newDeal,saleOrdinance));
            }
            else{
            Deal newDeal=new Deal(SALE_ORDINANCE_AMOUNT, SALE_ORDINANCE_LIMIT,SELLER,BUYER, STOCK);
                deals.add(newDeal);
                buyOrdinance.setAmount(0);
                STOCK.getSaleOrdersStack().pop();
            usersList.addUserInform(new UserInforms(SELLER,newDeal));
            } }

    private void handlingTheRestOfTheOrdinance() {
        if(ordinance.getAmount()!=0){
            if(ordinance.getType()==OrdinanceType.MKT){
                ordinance.setLimit(STOCK.getCurrentPrice());
            }
            STOCK.addOrdinance(ordinance);
        }
    }

    private boolean checkIfFokIsPossible() {
        if(ordinance instanceof Sale){
            return checkIfFokSaleOrdinanceIsPossible();
        }
        else if(ordinance instanceof Buy){
            return checkIfFokBuyOrdinanceIsPossible();
        }
        else{
            return false;
        }
    }

    private boolean checkIfFokSaleOrdinanceIsPossible(){
        OrdersStack stack=new OrdersStack();

        stack.addAll(STOCK.getBuyOrdersStack());

        int ordinanceAmount=ordinance.getAmount();
        int ordinanceLimit=ordinance.getLimit();

        while(ordinanceAmount>0){
            if(stack.isEmpty()){
                return false;
            }

            if(ordinanceLimit>(stack.top().getLimit())){
                return false;
            }
            else{
                ordinanceAmount-=(stack.top().getAmount());
                stack.pop();
            }
        }
        return true;
    }

    private boolean checkIfFokBuyOrdinanceIsPossible(){
        OrdersStack stack=new OrdersStack();

        stack.addAll(STOCK.getSaleOrdersStack());

        int ordinanceAmount=ordinance.getAmount();
        int ordinanceLimit=ordinance.getLimit();

        while(ordinanceAmount>0){
            if(stack.isEmpty()){
                return false;
            }

            if(ordinanceLimit<(stack.top().getLimit())){
                return false;
            }
            else{
                ordinanceAmount-=(stack.top().getAmount());
                stack.pop();
            }
        }
        return true;
    }

public String toString(){
String res="";

    boolean onlyImmediate=((ordinance.getType()==OrdinanceType.FOK)||(ordinance.getType()==OrdinanceType.IOC));

    if (!deals.isEmpty()){
            res+="The following deal";
            if(deals.size()!=1){res+='s';};
            res+=" have been done for you:\n";
            res+= Deals.toString(deals)+'\n';
            if((ordinance.getAmount()!=0)&&!onlyImmediate){
                res+="\nAnd this is the res of your ordinance that has been added to the orders list:\n";
                res+=ordinance.toString()+'\n';
            }
        }
        else if(!onlyImmediate){
            res+="No suitable ordinance was found.\n" +
                    "your ordinance has been added to the orders list\n";
            res+=ordinance.toString()+'\n';
        }
        else{
            res="We were unable to make any transaction for you";
    }
        return res;
}

public class UserInforms {
    private final String MASSAGE;
    private final Trader TRADER;

    //In case there is a need to inform the trader about the rest of his ordinance
    public UserInforms(Trader trader, Deal deal, Ordinance ordinance) {
        this.TRADER = trader;

        String massage = "the following deal have been done for you :\n" + deal.toString();

        if (ordinance.getAmount() > 0) {
            massage+= "\nAnd this is the rest of your ordinance that has been added to the orders list:\n" + ordinance.toString();
        }

        this.MASSAGE=massage;
    }

    //In case there is no  need to inform the trader about the rest of his ordinance
    public UserInforms(Trader trader, Deal deal) {
        this.TRADER = trader;

        this.MASSAGE = "the following deal have been done for you :\n" + deal.toString();
    }

    public Trader getTrader(){
        return this.TRADER;
    }

    public String getMassage(){
        return this.MASSAGE;
    }

}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return Objects.equals(ordinance, trade.ordinance) && Objects.equals(deals, trade.deals) && Objects.equals(STOCK, trade.STOCK) && Objects.equals(ordinanceType, trade.ordinanceType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ordinance, deals, STOCK, ordinanceType);
    }

}
