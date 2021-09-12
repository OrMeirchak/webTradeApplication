package stock;

public class SingleStockEntry {
    private final String name;
    private final String symbol;
    private final String gate;
    private final String turnover;

    SingleStockEntry(String name, String symbol, int gate, int turnover){
        this.name = name;
        this.symbol = symbol;
        this.gate = String.valueOf(gate);
        this.turnover = String.valueOf(turnover);
    }
}
