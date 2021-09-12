package user;

public class Admin extends User{
    public Admin(String name) {
        super(name);
    }

    public SingleUserEntry getUserEntry(){
        return new SingleUserEntry(NAME,"Admin");
    }
}
