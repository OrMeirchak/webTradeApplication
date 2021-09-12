package exception;

public class ExtensionException extends Exception{
    private final String EXCEPTION_MESSAGE;
    String SYMBOL;

    public ExtensionException(String wrongException,String rightException) {
        EXCEPTION_MESSAGE="Unable to load file with "+wrongException+" extension\n"+
        "You can only upload files with the " +rightException+" extension";
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
