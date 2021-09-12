package deal;

import exception.UserDoesntHoldEnoughStocksException;
import exception.UserDoesntHoldingTheStockException;
import stock.Stock;
import trade.OrdinanceType;
import user.Trader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Deal implements Comparable<Deal>{
    final private Date DATE;
    final private int AMOUNT;
    final private int PRICE;
    final private int VALUE;//סה"כ שווי עסקה
    final private Trader SELLER;
    final private Trader BUYER;
    final private OrdinanceType TYPE;
    final private Stock STOCK;

    public Deal(int AMOUNT, int PRICE, Trader seller, Trader buyer, Stock stock) throws UserDoesntHoldEnoughStocksException, UserDoesntHoldingTheStockException {
        this.STOCK = stock;
        this.DATE = new Date();
        this.AMOUNT = AMOUNT;
        this.PRICE = PRICE;
        this.VALUE = AMOUNT*PRICE;
        this.SELLER= seller;
        this.BUYER=buyer;
        this.TYPE=OrdinanceType.LMT;

        updatePrice();
        updateUsers();
    }

    private void updateUsers() throws UserDoesntHoldingTheStockException, UserDoesntHoldEnoughStocksException {
        SELLER.saleStockDeal(STOCK,AMOUNT,VALUE);
        BUYER.buyStockDeal(STOCK,AMOUNT,VALUE);
    }

    private void updatePrice(){
        STOCK.setPrice(PRICE);
    }

    public SingleDealEntry getSingleDealEntry(){return new SingleDealEntry(DATE,AMOUNT,PRICE,BUYER.getName(),SELLER.getName());}

    public OrdinanceType getType() {
        return TYPE;
    }

    public float getValue() {
        return VALUE;
    }

    public int getPrice() {
        return PRICE;
    }

    public int getAmount() {
        return AMOUNT;
    }

    public Date getDate() {
        return DATE;
    }

    public Trader getSeller() {
        return SELLER;
    }

    public Trader getBuyer() {
        return BUYER;
    }

    @Override
    public String toString() {
        return "Date=" + new SimpleDateFormat("HH:mm:ss:SSS").format(DATE) +
                "\nAmount=" + AMOUNT +
                "\nSales price=" + PRICE +
                "\nValue of transaction=" + VALUE +
                "\nStock symbol=" + STOCK.getSYMBOL() +
                "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deal deal = (Deal) o;
        return AMOUNT == deal.AMOUNT && Float.compare(deal.PRICE, PRICE) == 0 && Float.compare(deal.VALUE, VALUE) == 0 && Objects.equals(DATE, deal.DATE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(DATE, AMOUNT, PRICE, VALUE);
    }

    @Override
    public int compareTo(Deal o) {
        return this.DATE.compareTo(o.DATE);
    }
}
