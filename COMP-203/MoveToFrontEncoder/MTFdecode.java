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
            // Open the file
            int readahead = 10*1024*1024; // 10MB
            BufferedReader reader = new BufferedReader( new FileReader( filename ), readahead );

            // Read a line at a time
            String line;
            while( ( line = reader.readLine() ) != null )
            {
                // Split the line into words
                String [] words = line.split(" ",2);

                // Check if word is in dictionary
                if( words.length == 1 )
                {
                    // Check for newline
                    if( words[0].equals( "" ) )
                    {
                        System.out.println();
                        continue;
                    }

                    // Check for space
                    if( words[0].equals( "s" ) )
                    {
                        System.out.print(" ");
                        continue;
                    }

                    // The word is in the dictionary already
                    int index = Integer.parseInt( words[0] );
                    String word = dict.at( index );

                    // Print the word
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
