package deal;

import java.util.Date;

public class SingleDealEntry {
    private final String date;
    private final String amount;
    private final String price;
    private final String buyer;
    private final String seller;


    protected SingleDealEntry(Date date, int amount, int price, String buyerName, String sellerName) {
        this.date = String.valueOf(date);
        this.amount = String.valueOf(amount);
        this.price = String.valueOf(price);
        this.buyer = buyerName;
        this.seller = sellerName;
    }
}
