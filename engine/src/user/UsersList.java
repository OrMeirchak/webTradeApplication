package user;

import exception.*;
import stock.Stock;
import trade.Trade;

import java.util.*;

public class UsersList {

    private Collection<User> users;

    private Collection<Trade.UserInforms> usersInforms;

    private static UsersList singleInstance=null;

    private UsersList() {
        this.users = new HashSet<User>();
        this.usersInforms = new LinkedList<Trade.UserInforms>();
    }

    public static UsersList getUsersList(){
        if(singleInstance==null){
            singleInstance=new UsersList();
        }
        return singleInstance;
    }

    public List<SingleUserEntry> getListEntries(){
        List<SingleUserEntry> res=new ArrayList<SingleUserEntry>();

        for (User user:users){
          res.add(user.getUserEntry());
        }
        return res;
    }

    public List<SingleTrafficEntry> getTraderTrafficsEntries(String userName) throws NonTraderUserException, UserDidntExistException {
      Trader trader=getTrader(userName);

      return trader.getTrafficsEntries();
    }

    public int getTrafficsListSize(String userName) throws NonTraderUserException, UserDidntExistException {
        Trader trader=getTrader(userName);

        return trader.getTrafficsListSize();
    }

   public  Trader getTrader(String name) throws UserDidntExistException, NonTraderUserException {
      User user=getUser(name);

      if(user==null){
          throw new UserDidntExistException(name);
      }
      else if (user instanceof Trader){
          return (Trader)user;
      }
      else{
          throw new NonTraderUserException(name);

      }
    }

    public  User getUser(String name){
        for (User user:users){
            if(user.getName().toLowerCase(Locale.ROOT).equals(name.toLowerCase(Locale.ROOT))){
                return user;
            }
        }
        return null;
    }

    public String getTraderInformation(String traderName) throws NonTraderUserException, UserDidntExistException {
        Trader trader=getTrader(traderName);

        for (Trade.UserInforms userInform: usersInforms){
            if (userInform.getTrader()==trader){
                String res=userInform.getMassage();
                usersInforms.remove(userInform);
                return res;
            }
        }
        return null;
    }

    public int getTraderBalance(String userName) throws NonTraderUserException, UserDidntExistException {
        Trader trader=getTrader(userName);

        return trader.getBalance();
    }

    public int getHoldingAmount(String userName,Stock stock) throws TraderDidntExistException, UserDoesntHoldingTheStockException, NonTraderUserException, UserDidntExistException {
        Trader trader=getTrader(userName);

        if (trader==null){
            throw new TraderDidntExistException(userName);
        }
        else{

            return trader.getHoldingAmount(stock);
        }
    }

    public int getSize() {
        return users.size();
    }

    public  Trader addTrader(String name) throws UserNameAlreadyExistException {
        if(isUserExist(name)){
           throw new UserNameAlreadyExistException(name);
        }

        else{
            Trader newTrader=new Trader(name);
            users.add(newTrader);
            return newTrader;
        }}

    public  Admin addAdmin(String name) throws UserNameAlreadyExistException {
        if(isUserExist(name)){
            throw new UserNameAlreadyExistException(name);
        }

        else{
            Admin newAdmin=new Admin(name);
            users.add(newAdmin);
            return newAdmin;
        }}

        public void addUserInform(Trade.UserInforms userInforms){
            usersInforms.add(userInforms);
        }

    public boolean isUserExist(String userName){
        if (getUser(userName)!=null){
            return true;
        }
        else{
            return false;
        }
    }

    public void deposit(String traderName,int amount) throws NonTraderUserException, UserDidntExistException {

Trader trader=getTrader(traderName);

trader.deposit(amount);
    }

}
