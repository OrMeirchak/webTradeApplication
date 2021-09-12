package user;

public class SingleTrafficEntry {
    private final String description;
    private final String timestamp;
    private final String change;
    private final String budgetbefore;
    private final String budgetafter;


    public SingleTrafficEntry(String description, String timestamp, String change, String budgetbefore, String budgetafter) {
        this.description = description;
        this.timestamp = timestamp;
        this.change = change;
        this.budgetbefore = budgetbefore;
        this.budgetafter = budgetafter;
    }
}
