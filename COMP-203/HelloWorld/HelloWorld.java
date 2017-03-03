public class HelloWorld {
    public static void main(String[] args) {
        // Create an array of people
        people = new Person[ args.length ];
        for( int i = 0; i < args.length; i++) {
            people[i] = new Person( args[i] );
        }
        
        // Print each persons name
        if( people.length > 0 ){
            System.out.println("ID\t\t\t\t\tName");
            System.out.println("");
            for( int i = 0; i < people.length; i++) {
                System.out.println(people[i].getID() + "\t" + people[i].getName());
            }
        } else {
            System.out.println("Hello, World.");
        }
    }

    static Person [] people;
}