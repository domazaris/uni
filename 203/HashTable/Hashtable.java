public class Hashtable
{
    private Entry [] table;
    private int size;
    private String type;

    private int op_count;
    private int probe_count;

    private int load;

    public Hashtable( int s, String t )
    {
        table = new Entry[ s ];
        size = s;
        type = t;
        op_count = 0;
        probe_count = 0;
        load = 0;
    }

    public float load()
    {
        return (float)load / (float)size;
    }

    public void put( int k, String s )
    {
        ++op_count;
        ++probe_count;

        int hc = k % size;
        while( table[ hc ] != null )
        {
            // Slot is not empty, rehash
            if( type.equals("L") )
            {
                hc = rehashLinear( k, hc );
            }
            else if( type.equals("K") )
            {
                hc = rehashKey( k, hc );
            }
        }

        // Empty slot, put
        table[ hc ] = new Entry( k, s );
        load += 1;
    }

    public String get( int k )
    {
        ++op_count;
        ++probe_count;

        int hc = k % size;
        while( table[ hc ] != null )
        {
            // Check if the key matches
            if( table[ hc ].getKey() == k )
            {
                // Match - return data
                return table[ hc ].getData();
            }
            else
            {
                // Mismatch - rehash and try again
                if( type.equals("L") )
                {
                    hc = rehashLinear( k, hc );
                }
                else if( type.equals("K") )
                {
                    hc = rehashLinear( k, hc );
                }
            }
        }

        // Not found
        return null;
    }

    public float performance()
    {
        return (float)probe_count / (float)op_count;
    }

   private int rehashLinear( int k, int hc )
    {
        ++probe_count;
        return ( hc + 1 ) % size;
    }

    private int rehashKey( int k, int hc )
    {
        ++probe_count;
        int offset = k / size;
        return ( hc + offset ) % size;
    }

    private class Entry
    {
        public Entry( int k, String s )
        {
            key = k;
            data = s;
        }

        public int getKey()
        {
            return key;
        }

        public String getData()
        {
            return data;
        }

        private int key;
        private String data;
    }
}
