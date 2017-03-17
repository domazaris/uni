public class MTFencoder
{
    public static void main( String [] args )
    {
        LinkedList< String > list = new LinkedList<String>();

        for( int i = 0; i < 10; i++ )
        {
            list.insert( Integer.toString(i) );
        }

        for( int i = 0; i < 10; i++ )
        {
            System.out.println( list.at(i) );
        }

        return;
    }
}