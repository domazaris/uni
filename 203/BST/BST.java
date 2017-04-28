public class BST
{
    public static void println( String p )
    {
        System.out.println(p);
    }

    public static void err( String p )
    {
        System.err.println("Error: " + p);
        System.exit(1);
    }

    public static void main( String args[] )
    {
        int iterations = 100;
        if(args.length > 0)
        {
            iterations = Integer.parseInt( args[0] );
        }

        // Create Tree
        BinarySearchTree tree = new BinarySearchTree( 0 );
        if( ! tree.hasKey( 0 ) )
        {
            err("Could not find inserted key 0." );
        }

        // Do a bunch of iterations
        for(int i = 1; i <= iterations; i++)
        {
            // Insert and check
            tree.insert( i );
            if( ! tree.hasKey(i) )
            {
                err("Could not find inserted key " + i + "." );
            }

            // Remove and check
            tree.remove( i );
            if( tree.hasKey(i) )
            {
                err("Found removed key in tree " + i + "." );
            }
        }

        println("Test completed " + iterations + " iterations.");
        return;
    }

}
