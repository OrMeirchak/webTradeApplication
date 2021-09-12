package exception;

public class UserNameAlreadyExistException extends Exception{

    private final String EXCEPTION_MESSAGE;
    private final String USER_NAME;

    public UserNameAlreadyExistException(String userName) {
        this.USER_NAME=userName;
        this.EXCEPTION_MESSAGE = "A user name "+USER_NAME+ " already exists in the system";//לבדוק עם האנגלית נכונה
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }

    public String getUserName() {
        return USER_NAME;
    }
}

