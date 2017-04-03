import java.util.Scanner;
import java.io.File;

class Compress 
{
    public static void compress( String file_name )
    {
        try
        {
            Scanner scanner = new Scanner( new File(file_name) );

            String token;
            StringSet dict = new StringSet();
            while( scanner.hasNext() )
            {
                token = scanner.next();
                int idx = dict.indexOf( token );
                if( idx == -1 )
                {
                    System.out.println( idx + " " + token );
                    dict.add( token );
                }
                else
                {
                    System.out.println( idx );
                }
            }
        }
        catch(Exception e){}
    }

    public static void decompress( String file_name )
    {
        try
        {
            Scanner scanner = new Scanner( new File(file_name) );

            String token;
            StringSet dict = new StringSet();
            while( scanner.hasNext() )
            {
                token = scanner.next();
                if( token.compareTo("-1") == 0 )
                {
                    token = scanner.next();
                    dict.add( token );
                    System.out.println( token + " " );
                }
                else
                {
                    System.out.println( dict.stringAt( Integer.parseInt(token) ) + " ");
                }
            }
        }
        catch(Exception e){}
    }

    public static void main(String[] args) 
    {
        // Check that we got an argument
        if( args.length != 2 )
        {
            System.err.println("Incorrect amount of arguments.");
            System.err.println("Usage:\n\tjava Compress [-c|-d] [input file]");
            return;
        }

        // Compress the file
        if( args[0].compareTo("-c") == 0 )
        {
            compress( args[1] );
        }
        else if( args[0].compareTo("-d") == 0 )
        {
            decompress( args[1] );
        }
        else
        {
            System.err.println("Incorrect flag. \"" + args[0] + "\"");
            System.err.println("Usage:\n\tjava Compress [-c|-d] [input file]");
        }
    }
}