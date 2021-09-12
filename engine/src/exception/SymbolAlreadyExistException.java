package exception;

public class SymbolAlreadyExistException extends Exception{

    private final String EXCEPTION_MESSAGE;
    String SYMBOL;

    public SymbolAlreadyExistException(String SYMBOL) {
        this.SYMBOL=SYMBOL;
        EXCEPTION_MESSAGE = "A stock with the company name"+SYMBOL+ "already exists in the system";//לבדוק עם האנגלית נכונה
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }

    public String getSymbol() {
        return SYMBOL;
    }
}
