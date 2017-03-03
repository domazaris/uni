public class Person {
    String _name;
    int _age;

    public Person( String name, int age ) {
        _name = name;
        _age = age;
    }

    public int getAge() {
        return _age;
    }

    public String getName() {
        return _name;
    }
}