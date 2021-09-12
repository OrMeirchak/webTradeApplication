package user;


public class SingleUserEntry {
    private final String name;
    private final String type;

    SingleUserEntry(String name, String type){
        this.name = name;
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public String getType(){
        return type;
    }
}
