import java.util.UUID;

public class Person {
    String _name;
    UUID _id;

    public Person( String name ) {
        _name = name;
        _id = UUID.randomUUID();
    }

    public String getName() {
        return _name;
    }

    public UUID getID() {
        return _id;
    }
}