package trade;

import stock.Stock;
import user.Trader;
import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

abstract public class Ordinance {
    final private Stock STOCK;
    private int amount;
    private int LIMIT;
    final private Date DATE;
    final private Trader TRADER;
    final private OrdinanceType TYPE;

    public Ordinance(Stock stock, int amount, int limit, Trader trader, OrdinanceType type){
        this.DATE = new Date();
        this.STOCK = stock;
        this.amount = amount;
        this.LIMIT = limit;
        this.TRADER=trader;
        this.TYPE=type;
    }

    public OrdinanceType getType() {
        return TYPE;
    }

    public int getAmount() {
        return amount;
    }

    protected void setAmount(int amount) {
        this.amount = amount;
    }

    public int getLimit() {
        return LIMIT;
    }

    public Date getDate() {
        return DATE;
    }

    public void setLimit(int limit){
        LIMIT=limit;
    }

    protected Stock getStock() {
        return STOCK;
    }

    public Trader getUser() {
        return TRADER;
    }

    protected void subtractAmount(int amountToSubtract){
        amount-=amountToSubtract;
    }

    @Override
    public String toString() {
        return "LIMIT=" + LIMIT +
                "\nAmount=" + amount +
                "\nDate=" + new SimpleDateFormat("HH:mm:ss:SSS").format(DATE) +
                '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ordinance ordinance = (Ordinance) o;
        return amount == ordinance.amount && LIMIT == ordinance.LIMIT && Objects.equals(STOCK, ordinance.STOCK) && Objects.equals(DATE, ordinance.DATE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(STOCK, amount, LIMIT, DATE);
    }
}
