/*
 * Name: Dominic Azaris
 * ID:   1297845
 */

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

class LineReader
{
    /**
     * Opens the file at the given path
     */
    public LineReader( String filename ) throws FileNotFoundException, IOException
    {
        // Read 10MB max at a time
        int readahead = 1024*1024*10;

        // Open the file
        reader = new BufferedReader( new FileReader( filename ), readahead );
    }

    /**
     * Reads a line at a time, including the newline character
     */
    public String readLine() throws IOException
    {
        String line = new String();
        int i;
        char c;

        // For each character
        while( (i = reader.read() ) != -1 )
        {
            // Add the character to the line
            c = (char)i;
            line +=  (char)c;

            // If the character was a newline, return as a full line
            if( c == '\n' )
            {
                return line;
            }
        }

        if( line.length() == 0 )
        {
            // Nothing in the line
            return null;
        }
        else
        {
            // If there were characters read but no newline ( EOF )
            return line;
        }
    }

    private BufferedReader reader;
}
