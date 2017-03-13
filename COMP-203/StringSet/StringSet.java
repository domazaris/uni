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
        if( !hasString( str ) )
            return;
        
        // Add the string to the array
        strings[ count++ ] = str;
        
        // Check the size of the array
        if( count == MAX )
            growArray();
    }

    public boolean hasString( String str )
    {
        return true;
    }

    public int indexOf( String str )
    {
        return 1;
    }

    public String stringAt( int index )
    {
        return "";
    }

    private void growArray()
    {

    }

    // Members
    private String [] strings; // Holds the strings
    private int count; // Count of current items in the set
    private int MAX; // Current max items in the set
}