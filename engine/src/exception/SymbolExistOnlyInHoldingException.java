package exception;

public class SymbolExistOnlyInHoldingException extends Exception{

    private final String EXCEPTION_MESSAGE;
    private final String SYMBOL ;


    public SymbolExistOnlyInHoldingException(String symbol) {
        this.SYMBOL=symbol;
        EXCEPTION_MESSAGE="The stock symbol "+SYMBOL+" exist in your holdings scheme but not in your stock scheme";
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}


