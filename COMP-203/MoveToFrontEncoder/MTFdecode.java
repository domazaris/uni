import java.io.FileReader;
import java.io.BufferedReader;

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
                    // The word is in the dictionary already
                    int index = Integer.parseInt( words[0] );
                    System.out.print( dict.at( index ) );
                }
                else if( words.length > 1 )
                {
                    dict.insert( words[1] );
                    System.out.print( words[1] );
                }
            }
        }
        catch( Exception e ){ System.err.println(e); }
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
