import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MTFencoder
{
    private static void encode( String filename )
    {
        // Create the dictionary
        WordList dict = new WordList();

        // Read the file
        try
        {
            // Open up a line at a time
            String line;
            BufferedReader reader = new BufferedReader( new FileReader( filename ), 1024*1024 );
            int counter = 0;
            while( ( line = reader.readLine() ) != null )
            {
                // Split the line into words
                // String [] words = line.split("(?=\\.)|(?=\\?)|(?<='s)|(?=,)|(?=!)|(?=\")|(?<=\\ )|(?=\\ )");
                String [] words = line.split("(?=\\.)|(?=\\?)|(?=,)|(?=!)|(?<=\\ )|(?=\\ )");

                // Insert each token into the dictionary
                for( int i = 0; i < words.length; i++ )
                {
                    // Check for spaces
                    String word = words[i];
                    int location = dict.insert( word );
                    if( location <= 0 )
                    {
                        System.out.println( "0 " + word );
                    }
                    else
                    {
                        System.out.println( location );
                    }
                }

                // Print a newline for each line
                System.out.println();
            }
        }
        catch( FileNotFoundException e ){}
        catch( IOException e ){}
    }

    public static void main( String [] args )
    {
        // Parse args - should be just a filename
        if( args.length != 1 )
        {
            System.err.println("Incorrect amount of arguments.");
            System.err.println("Usage:\n\tjava MTFencoder [input file]");
            return;
        }

        encode( args[0] );
        return;
    }
}
