/*
 * Name: Dominic Azaris
 * ID:   1297845
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class FileSort
{
    private static void readFile( String filename, LineList list )
    {
        try
        {
            // Open the file
            BufferedReader reader = new BufferedReader( new FileReader( filename  ) );

            // Read a line at a time
            String line;
            while( ( line = reader.readLine() ) != null )
            {
                list.pushFront( line );
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
        if( args.length != 2 )
        {
            System.err.println("Incorrect amount of arguments.");
            System.err.println("Usage:\n\tjava MTFencoder [input file]");
            System.exit( 1 );
        }

        if( args[0].equals("i") && args[0].equals("q") )
        {
            System.err.println("Invalid parameter: " + args[0]);
            System.err.println("Usage:\n\tjava MTFencoder [input file]");
            System.exit( 1 );
        }

        String filename = args[1];
        LineList list = new LineList();

        readFile( filename, list );

        int count = 0;
        if( args[0].equals( "i" ) )
        {
            count = list.isort();
            list.printList();
        }
        else if( args[0].equals( "q" ) )
        {
            count = list.qsort();
            list.printList();
        }
        System.err.println( "COUNT: " + count );
        return;
    }
}
