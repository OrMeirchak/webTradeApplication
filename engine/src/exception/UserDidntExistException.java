package exception;

public class UserDidntExistException  extends Exception{

    private final String EXCEPTION_MESSAGE;
    String NAME;

    public UserDidntExistException(String name) {
        this.NAME=name;
        EXCEPTION_MESSAGE = "We didn't find a User with  the name"+NAME;
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}