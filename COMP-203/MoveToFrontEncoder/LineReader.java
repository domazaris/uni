import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

class LineReader
{
    public LineReader( String filename ) throws FileNotFoundException, IOException
    {
        int readahead = 1024*1024*10;
        reader = new BufferedReader( new FileReader( filename ), readahead );
    }

    public String readLine() throws IOException
    {
        String line = new String();
        int i;
        char c;
        while( (i = reader.read() ) != -1 )
        {
            c = (char)i;
            line +=  (char)c;
            
            if( c == '\n' )
            {
                return line;
            }        
        }

        if( line.length() == 0 )
            return null;
        else
            return line;
    }

    private BufferedReader reader;
}