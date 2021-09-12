package exception;

public class SymbolDidntExistException extends Exception{

    private final String EXCEPTION_MESSAGE;
String SYMBOL;

    public SymbolDidntExistException(String SYMBOL) {
        this.SYMBOL=SYMBOL;
        EXCEPTION_MESSAGE = "We didn't find a stock with "+SYMBOL+" SYMBOL";
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
