package exception;

public class OnlyPositiveNumberException extends Exception{

    private final String EXCEPTION_MESSAGE;
    int number;

    public OnlyPositiveNumberException(int number) {
        this.number = number;
        EXCEPTION_MESSAGE = number + " is not a positive number";
    }
    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
