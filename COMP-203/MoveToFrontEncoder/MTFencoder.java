import java.io.FileReader;
import java.io.BufferedReader;

public class MTFencoder
{
    private static void encode( String filename )
    {
        // Create the dictionary
        WordList dict = new WordList();

        // Read the file
        try
        {
            String line;
            BufferedReader reader = new BufferedReader( new FileReader( filename ) );
            while( ( line = reader.readLine() ) != null )
            {
                //System.out.println(line);
                String [] words = line.split("(?=\\.)|(?=,)|(?=\n)|(?=!)| ");
                
                for( int i = 0; i < words.length; i++  )
                {
                    int location = dict.insert( words[i] );
                    if( location <= 0 )
                    {
                        System.out.println( "0 " + words[i] );
                    }
                    else
                    {
                        System.out.println( location );
                    }
                }
            }
        }
        catch( Exception e ){}
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
