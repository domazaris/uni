import java.util.Iterator;

public class DomTest
{
    private static void out(String msg)
    {
        System.out.println(msg);
    }

    private static void err(String msg)
    {
        System.err.println(msg);
    }

    static private void testTable()
    {
        String[] args = {"L", "K"};
        int size = 10;

        for(int t = 0; t < args.length; t++)
        {
            String type = args[ t ];
            Hashtable map = new Hashtable( size, type );

            for(int i = 0; i < size; i++ )
            {
                int key = i * size;
                String data = Integer.toString( i * size );

                map.put(key, data);
                out("Load: " + map.load());
                out("Perf: " + map.performance());
            }

            for(int i = 0; i < size; i++ )
            {
                int key = i * size;
                String data = Integer.toString( i * size );

                String ret = map.get(key);
                String expect = Integer.toString( i * size );
                if( ! ret.equals( expect ) )
                {
                    err( "ERROR: " + ret + " vs " + expect );
                }
                else
                {
                    out("SUCCESS");
                }
            }
        }
    }

    public static void main( String[] args )
    {
        testTable();
        return;
    }
}