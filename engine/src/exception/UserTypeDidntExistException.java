package exception;

import java.util.Locale;

public class UserTypeDidntExistException extends Exception{

    private final String EXCEPTION_MESSAGE;
    private final String TYPE;

    public UserTypeDidntExistException(String type) {
        this.TYPE=type.toLowerCase(Locale.ROOT);
        this.EXCEPTION_MESSAGE = "A user type "+type+ " doesnt exists in the system";
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
