public class BSTTest
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

        for(int i = 8; i < iterations; i++)
        {
            // Remove and check
            String i_str = Integer.toString(i);
            tree.delete( i_str );
            if( tree.find(i_str) )
            {
                err("Found removed key in tree " + i_str + "." );
            }
        }

        println("Test completed " + iterations + " iterations.");
        return;
    }

}
