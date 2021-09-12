package exception;

public class NotFullPathException extends Exception{

    private final String EXCEPTION_MESSAGE;

    public NotFullPathException(String path) {
            this.EXCEPTION_MESSAGE= path+" is not a full path\nfor example C:"+'\\'+"Users"+'\\'+"Desktop"+'\\'+"myFile.xml is a full path" ;

    }
    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}


