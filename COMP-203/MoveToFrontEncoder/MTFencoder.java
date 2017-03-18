public class MTFencoder
{
    public static void main( String [] args )
    {
        WordList list = new WordList();
        int val = 0;
        val = list.insert( "the" );
        System.out.println( val + " " + list.at( val ) );
        val = list.insert( "car" );
        System.out.println( val + " " + list.at( val ) );
        val = list.insert( "on" );
        System.out.println( val + " " + list.at( val ) );
        val = list.insert( "the" );
        System.out.println( val + " " + list.at( val ) );
        list.printList();
        return;
    }
}
