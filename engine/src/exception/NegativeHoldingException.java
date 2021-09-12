package exception;
public class NegativeHoldingException extends Exception {

    private final String EXCEPTION_MESSAGE;
    private final int STOCK_HOLD;
    private final String STOCK_SYMBOL;

    public NegativeHoldingException(int stockHoldAmount, String stock_symbol) {
        this.STOCK_HOLD = (stockHoldAmount);
        this.STOCK_SYMBOL = stock_symbol;
        this.EXCEPTION_MESSAGE = "The user hold only " + String.valueOf(STOCK_HOLD) +' '+STOCK_SYMBOL+" stocks";
    }

    @Override
    public String getMessage() { return EXCEPTION_MESSAGE; }

    public int getHoldAmount(){
        return STOCK_HOLD;
    }

    public String getStockSymbol(){ return STOCK_SYMBOL; }
}