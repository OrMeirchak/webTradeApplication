package exception;

public class UserDoesntHoldingTheStockException extends Exception{

    private final String EXCEPTION_MESSAGE;
    private final String SYMBOL;
    private final String USER_NAME;

    public UserDoesntHoldingTheStockException(String symbol,String userName) {
        this.SYMBOL=symbol;
        this.USER_NAME=userName;
        EXCEPTION_MESSAGE = "The user "+USER_NAME+" doesnt hold "+SYMBOL+" stocks";
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
