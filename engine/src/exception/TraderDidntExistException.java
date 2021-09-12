package exception;

public class TraderDidntExistException extends Exception{

    private final String EXCEPTION_MESSAGE;
    String NAME;

    public TraderDidntExistException(String name) {
        this.NAME=name;
        EXCEPTION_MESSAGE = "We didn't find a trader with  the name"+NAME;
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}