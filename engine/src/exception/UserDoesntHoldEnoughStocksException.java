package exception;
public class UserDoesntHoldEnoughStocksException extends Exception {

    private final String EXCEPTION_MESSAGE;
    private final String SYMBOL;
    private final String USER_NAME;
    private final String STOCK_HOLD;

    public UserDoesntHoldEnoughStocksException(String symbol, String userName, int stockHold) {
        this.SYMBOL = symbol;
        this.USER_NAME = userName;
        this.STOCK_HOLD = String.valueOf(stockHold);
        this.EXCEPTION_MESSAGE = "The user " + USER_NAME + " hold only " + STOCK_HOLD + ' ' + SYMBOL + " stocks";
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}