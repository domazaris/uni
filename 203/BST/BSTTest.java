public class BSTTest
{
    private static int[] test_data;

    private static void fillData( int amount )
    {
        test_data = new int[ amount ];

    }

    public static void out( String p )
    {
        System.out.println(p);
    }

    public static void err( String p )
    {
        System.err.println("Error: " + p);
//         System.exit(1);
    }

    public static void main( String args[] )
    {
        int iterations = 100;
        if(args.length > 0)
        {
            iterations = Integer.parseInt( args[0] );
        }

        // Create Tree
        BSTlex tree = new BSTlex();

        // Do a bunch of iterations
        for(int i = 0; i < iterations; i++)
        {
            // Insert and check
            String i_str = Integer.toString(i);
            tree.insert( i_str );
            if( ! tree.find(i_str) )
            {
                err("Could not find inserted key " + i_str + "." );
            }
        }

        // Print everything
        tree.printAll();
        for(int i = 0; i < iterations; i++)
        // for(int i = iterations-1; i >= 0; i--)
        {
            // Remove and check
            String i_str = Integer.toString(i);
            if(! tree.find(i_str) )
            {
                err("Key was removed by wrong code." );
            }

            out("Deleteing " + i_str );
            tree.delete( i_str );

            tree.printAll();

            if( tree.find(i_str) )
            {
                err("Found removed key in tree " + i_str + "." );
            }
        }

        out("Test completed " + iterations + " iterations.");
        return;
    }
}
