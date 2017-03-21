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
            LineReader reader = new LineReader( filename );

            while( ( line = reader.readLine() ) != null )
            {
                // Split the line into words
                // String [] words = line.split("(?<=\\s+)|(?=\\s+)");
                String [] words = line.split("(?=\\s+)");

                // Insert each token into the dictionary
                for( int i = 0; i < words.length; i++ )
                {
                    // Check for spaces
                    String word = words[i];
                    if( word.length() == 1 && word.charAt(0) == ' ' )
                    {
                        System.out.println( "s" );
                        continue;
                    }
                    else if( word.length() == 1 && word.charAt(0) == '\n')
                    {
                        System.out.println();
                        continue;
                    }

                    // Insert the word
                    int location = dict.insert( word );
                    if( location <= 0 )
                    {
                        // New word
                        System.out.println( "0 " + word );
                    }
                    else
                    {
                        // Word already in dictionary
                        System.out.println( location );
                    }
                }
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
