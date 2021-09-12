package user;

public abstract class User {
    final String NAME;

    public User(String name) {
        NAME = name;
    }

    public String getName(){
        return NAME;
    }

    public abstract SingleUserEntry getUserEntry();
}
