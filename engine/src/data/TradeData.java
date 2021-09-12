package data;

import deal.Deal;
import trade.Ordinance;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collection;

public class TradeData {
    private final SimpleStringProperty PRICE;
    private final SimpleStringProperty AMOUNT;
    private final SimpleStringProperty TYPE;
    private final SimpleStringProperty DATE;
    private final SimpleStringProperty USER;
    private final SimpleStringProperty SALLER;
    private final SimpleStringProperty BUYER;

    public String getPrice() {
        return PRICE.get();
    }

    public SimpleStringProperty PRICEProperty() {
        return PRICE;
    }

    public String getAmount() {
        return AMOUNT.get();
    }

    public SimpleStringProperty AMOUNTProperty() {
        return AMOUNT;
    }

    public String getType() {
        return TYPE.get();
    }

    public SimpleStringProperty TYPEProperty() {
        return TYPE;
    }

    public String getDate() {
        return DATE.get();
    }

    public SimpleStringProperty DATEProperty() {
        return DATE;
    }

    public String getUser() {
        return USER.get();
    }

    public SimpleStringProperty USERProperty() {
        return USER;
    }

    public String getSaller() {
        return SALLER.get();
    }

    public SimpleStringProperty SALLERProperty() {
        return SALLER;
    }

    public String getBuyer() {
        return BUYER.get();
    }

    public SimpleStringProperty BUYERProperty() {
        return BUYER;
    }

    public TradeData(Ordinance ordinance) {
        this.PRICE=new SimpleStringProperty();
        PRICE.set(String.valueOf(ordinance.getLimit()));

        this.AMOUNT=new SimpleStringProperty();
        AMOUNT.set(String.valueOf(ordinance.getAmount()));


        this.TYPE = new SimpleStringProperty();
        TYPE.set(ordinance.getType().toString());

        this.DATE = new SimpleStringProperty();
        DATE.set(ordinance.getDate().toString());

        this.USER = new SimpleStringProperty();
        USER.set(ordinance.getUser().getName());

        //not uses for this kind of data
        this.BUYER = null;

        this.SALLER = null;

    }

    public TradeData(Deal deal) {
        this.PRICE=new SimpleStringProperty();
        PRICE.set(String.valueOf(deal.getPrice()));

        this.AMOUNT=new SimpleStringProperty();
        AMOUNT.set(String.valueOf(deal.getAmount()));


        this.TYPE = new SimpleStringProperty();
        TYPE.set(deal.getType().toString());

        this.DATE = new SimpleStringProperty();
        DATE.set(deal.getDate().toString());

        this.BUYER = new SimpleStringProperty();
        BUYER.set(deal.getBuyer().getName());

        this.SALLER = new SimpleStringProperty();
        SALLER.set(deal.getSeller().getName());

        //not uses for this kind of data
        this.USER=null;
    }

    public static Collection<TradeData> convertDealsToData(Collection<Deal> deals){
        Collection<TradeData> res=new ArrayList<TradeData>();

        for (Deal deal:deals){
            res.add(new TradeData(deal));
        }

        return res;
    }
}