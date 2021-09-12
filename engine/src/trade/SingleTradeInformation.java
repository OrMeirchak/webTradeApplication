package trade;

import java.util.Collection;

public class SingleTradeInformation {

    private final String TRADE_MASSAGE;
    private final Collection<Trade.UserInforms> usersInforms;


    public SingleTradeInformation(String trade_massage, Collection<Trade.UserInforms> usersInforms) {
        this.TRADE_MASSAGE = trade_massage;
        this.usersInforms = usersInforms;
    }
}
