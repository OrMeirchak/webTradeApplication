package exception;

public class NoStocksException extends Exception{

    private final String EXCEPTION_MESSAGE = "There are no stocks in the system";

    public NoStocksException() {
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}


