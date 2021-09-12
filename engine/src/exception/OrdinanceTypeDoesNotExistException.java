package exception;

public class OrdinanceTypeDoesNotExistException extends Exception{

    private final String EXCEPTION_MESSAGE;
    private final String TYPE;

    public OrdinanceTypeDoesNotExistException(String type) {
        this.TYPE=type;
        EXCEPTION_MESSAGE = TYPE+" ordinance type does not exist";
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
