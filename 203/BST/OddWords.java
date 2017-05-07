import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class OddWords
{
    private static void out( String p )
    {
        System.out.print( p );
    }

    private static void err( String p )
    {
        System.err.print( p );
    }

    private static void lexicon( String filename )
    {
        // Create list reference
        BSTlex lex = new BSTlex();
        try
        {
            // Create a buffered reader - 5MB readahead
            BufferedReader reader = new BufferedReader( new FileReader( filename ), 1*1024*1024 );

            String line;

            // Read line at a time
            while( ( line = reader.readLine() ) != null )
            {
                // Split into words
                String[] words = line.replaceAll("[^a-zA-Z ]", " ").split("\\s+");

                // Iterate words array
                for( int i = 0; i < words.length; i++ )
                {
                    // Get word from array
                    String word = words[ i ].toLowerCase();

                    // Check tree for word
                    if( ! lex.find( word ) )
                    {
                        // Insert
                        out("ADD: " + word + "\t");
                        lex.insert( word );
                        out("ADDED\n");
                    }
                    else
                    {
                        // Remove
                        lex.delete( word );
                        out("REMOVED\n");
                    }
                }
            }

            // Print "LEXICON"
            out("LEXICON\n");

            // Print remainder of list
            lex.printAll();

            // Close file
            reader.close();
        }
        catch( FileNotFoundException e )
        {
            // File not found
            System.err.println( e );
            System.exit(1);
        }
        catch( IOException e )
        {
            // Other exception
            System.err.println( e );
            System.exit(1);
        }
    }

    public static void main( String args[] )
    {
        // Arg parse
        if( args.length != 1 )
        {
            // Error usage
            err( "Usage:\n" );
            err( "\tjava OddWords [input file]\n" );
            System.exit(1);
        }

        // Run function
        lexicon( args[ 0 ] );
    }
}
