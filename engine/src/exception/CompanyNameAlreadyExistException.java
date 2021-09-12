package exception;

public class CompanyNameAlreadyExistException  extends Exception{

    private final String EXCEPTION_MESSAGE;
    String companyName;

    public CompanyNameAlreadyExistException(String companyName) {
        this.companyName=companyName;
        EXCEPTION_MESSAGE = "A stock with the company name "+companyName+ " already exists in the system";//לבדוק עם האנגלית נכונה
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }

    public String getCompanyName() {
        return companyName;
    }
}
