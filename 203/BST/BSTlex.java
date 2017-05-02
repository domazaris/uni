public class BSTlex
{
    /**
     *  @brief Creates a new tree.
     *  @param key The key for the tree.
     */
    public BSTlex( String key )
    {
        data = key;
        smaller = null;
        bigger = null;
        previous = null;
    }

    /**
     *  @brief Inserts a key into the tree
     *  @param key The key to insert.
     */
    public void insert( String key )
    {
        // Return if the key is in the tree
        if( find( key ) )
        {
            return;
        }

        // Else insert
        int cmp = data.compareTo(key);
        if( cmp < 0 )
        {
            // Bigger
            if( bigger == null )
            {
                // No bigger, create a bigger
                bigger = new BSTlex( key, this );
                return;
            }
            else
            {
                bigger.insert( key );
            }
        }
        else
        {
            // Smaller
            if( smaller == null )
            {
                // No smaller, create a smaller
                smaller = new BSTlex( key, this );
                return;
            }
            else
            {
                smaller.insert( key );
            }
        }
    }

     /**
     *  @brief Check whether the tree has a key.
     *  @param key The key to check.
     *  @return A bool to show whether tree contains the key
     */
    public Boolean find( String key )
    {
        int cmp = data.compareTo(key);
        if( cmp == 0 )
        {
            return true;
        }
        else if( cmp < 0 )
        {
            if( bigger == null )
            {
                return false;
            }
            else
            {
                return bigger.find( key );
            }
        }
        else
        {
            if( smaller == null )
            {
                return false;
            }
            else
            {
                return smaller.find( key );
            }
        }
    }

    /**
     *  @brief Removes a key from the tree
     *  @param key The key to remove.
     */
    public void delete( String key )
    {
        // Remove this node
        int cmp = data.compareTo(key);
        if( cmp == 0 )
        {
            if( previous == null )
            {
            }
            else if( this == previous.smaller )
            {
            }
            else if( this == previous.bigger )
            {
            }
            return;
        }

        // Find key
        if( cmp < 0 )
        {
            if( bigger != null )
            {
                bigger.delete(key);
            }
        }
        else
        {
            if( smaller != null )
            {
                smaller.delete(key);
            }
        }
    }


    public void printAll()
    {
        println( "All" );
    }

    // ---------------- Helper methods ----------------

    private void println(String line)
    {
        System.out.println(line);
    }

    /**
     *  @brief Creates a new tree.
     *  @param key The key for the tree.
     *  @param previous The tree that points to the new tree.
     */
    protected BSTlex( String key, BSTlex previous )
    {
        data = key;
        smaller = null;
        bigger = null;
        previous = previous;
    }

    // ----------------- Connections ------------------
    protected BSTlex smaller;
    protected BSTlex bigger;
    private BSTlex previous;

    // ------------------- Members --------------------
    private String data;
}
