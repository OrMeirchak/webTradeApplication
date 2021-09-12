package user;

import java.sql.Timestamp;
import java.util.Date;

public class Traffic {
    private final String DESCRIPTION;
    private final Timestamp TIME_STAMP;
    private final int CHANGE;
    private final int BUDGET_BEFORE;
    private final int BUDGET_AFTER;

    public Traffic(String description, int change, int budgetBefore, int budgetAfter) {
        this.DESCRIPTION = description;
        this.TIME_STAMP = new Timestamp(System.currentTimeMillis());
        this.CHANGE = change;
        this.BUDGET_BEFORE = budgetBefore;
        this.BUDGET_AFTER = budgetAfter;
    }

    public SingleTrafficEntry getSingleTrafficEntry(){
        return new SingleTrafficEntry(DESCRIPTION,String.valueOf(TIME_STAMP),String.valueOf(CHANGE),
                String.valueOf(BUDGET_BEFORE),String.valueOf(BUDGET_AFTER));
    }
}
