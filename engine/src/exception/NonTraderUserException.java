package exception;

public class NonTraderUserException extends Exception {
    private final String EXCEPTION_MESSAGE;
    String NAME;

    public NonTraderUserException(String name) {
        this.NAME=name;
        EXCEPTION_MESSAGE = "The user "+NAME+" is not a trader";
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
