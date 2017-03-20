import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MTFdecode
{
    private static void decode( String filename )
    {
        // Create the dictionary
        WordList dict = new WordList();

        // Read the file
        try
        {
            // Open up a line at a time
            String line;
            BufferedReader reader = new BufferedReader( new FileReader( filename ), 1024*1024 );
            while( ( line = reader.readLine() ) != null )
            {
                // Split the line into words
                String [] words = line.split(" ",2);

                if( words.length == 1 )
                {
                    // Check for newline
                    if( words[0].compareTo( "" ) == 0 )
                    {
                        System.out.println();
                        continue;
                    }

                    // The word is in the dictionary already
                    int index = Integer.parseInt( words[0] );
                    String word = dict.at( index );
                    System.out.print( word );
                }
                else if( words.length > 1 )
                {
                    // Add the word to the dictionary
                    System.out.print(words[1]);
                    dict.insert( words[1] );
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
            System.err.println("Usage:\n\tjava MTFdecode [input file]");
            return;
        }

        decode( args[0] );
        return;
    }
}
