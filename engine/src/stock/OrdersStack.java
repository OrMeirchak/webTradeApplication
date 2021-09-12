package stock;

import data.TradeData;
import interfaces.Stringable;
import trade.Ordinance;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

public class OrdersStack<E extends Ordinance &Comparable& Stringable> {

    private TreeSet<E> stack;

    public OrdersStack() {
        stack = new TreeSet<E>();
    }

    public void push(E order) {
        stack.add(order);
    }

    public E pop(){
        return stack.pollFirst();
    }

    public int getRate(){
        if(!stack.isEmpty()) {
            return top().getLimit();
        }
        else{
            return 0;
        }
    }

    public E top(){
        return stack.first();
    }

    public Collection<TradeData> toData(){
        Collection<TradeData> res=new ArrayList<TradeData>();
        for (Ordinance ordinance:stack){
            res.add(new TradeData(ordinance));
        }

        return res;
    }

    public void addAll(OrdersStack ordersStack){
        this.stack.addAll(ordersStack.stack);
    }

    public boolean isEmpty(){
        return stack.isEmpty();
    }

    @Override
    public String toString() {
String res="";
int totalTurnover=0;

for (E ordinance:stack){
    res=res+'\n'+ordinance.toString();
    totalTurnover+=((ordinance.getLimit())*(ordinance.getAmount()));
}
return res+"\nTotal turn over for ths list=" +String.valueOf(totalTurnover) ;
    }

}
