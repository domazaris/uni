/*
 * Name: Dominic Azaris
 * ID:   1297845
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * This class opens and reads in an input file, sorts its lines based on a given
 * parameter, then prints the sorted file to stdout.
 */
public class FileSort
{
    /**
     * Reads a file into a list.
     *
     * @param filename The name of the file to read
     * @param list The list to read into
     */
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

    /**
     * The main function
     */
    public static void main( String [] args )
    {
        // Parse args - should be a filename and arg for which sort to use
        if( args.length != 2 )
        {
            System.err.println("Incorrect amount of arguments.");
            System.err.println("Usage:\n\tjava MTFencoder [input file]");
            System.exit( 1 );
        }

        // Make sure the first arg is either 'i' or 'q'
        if( args[0].equals("i") && args[0].equals("q") )
        {
            System.err.println("Invalid parameter: " + args[0]);
            System.err.println("Usage:\n\tjava MTFencoder [input file]");
            System.exit( 1 );
        }

        // Create the list and filename string
        String filename = args[1];
        LineList list = new LineList();

        // Read the file into the list
        readFile( filename, list );

        int count = 0;
        if( args[0].equals( "i" ) )
        {
            // Sort the list with the insertion sort
            count = list.isort();
        }
        else if( args[0].equals( "q" ) )
        {
            // Sort the list with the quick sort
            count = list.qsort();
        }

        // Print the count and count
        list.printList();
        System.err.println( "COUNT: " + count );
        return;
    }
}
