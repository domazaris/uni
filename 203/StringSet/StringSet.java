class StringSet 
{
    public StringSet()
    {
        MAX = 100;
        count = 0;
        strings = new String[ MAX ];
    }

    public void add( String str )
    {
        // Check to see if the string exists
        if( hasString( str ) )
        {
            return;
        }
        
        // Add the string to the array
        strings[ count++ ] = str;
        
        // Check the size of the array
        if( count == MAX )
        {
            growArray();
        }
    }

    public boolean hasString( String str )
    {
        // Get the index of the string
        int index = indexOf( str );

        // If there is no instance of the string, the index will be -1
        if( index == -1 )
        {
            return false;
        }
        return true;
    }

    public int indexOf( String str )
    {
        // Loop through and compare each element with str
        for( int i = 0; i < count; i++ )
        {
            if( strings[i].compareTo( str ) == 0 )
            { 
                // Return the index
                return i;
            }
        }
        
        // Return an error code
        return -1;
    }

    public String stringAt( int index )
    {
        // Bounds check
        if( index < count )
        {
            return strings[index];
        }
        return null;
    }

    private void growArray()
    {
        // Create a new array 10% larger
        MAX += MAX/10;
        String [] new_strings = new String[ MAX ];

        // Copy the old array into the new array
        System.arraycopy( strings, 0, new_strings, 0, count );
        strings = new_strings;
    }

    // Members
    private String [] strings; // Holds the strings
    private int count; // Count of current items in the set
    private int MAX; // Current max items in the set
}