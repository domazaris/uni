public class BSTlex
{
    /**
     *  @brief Creates a new tree.
     */
    public BSTlex()
    {
        data = null;
        smaller = null;
        bigger = null;
        parent = null;
    }

    /**
     *  @brief Inserts a key into the tree
     *  @param key The key to insert.
     */
    public void insert( String key )
    {
        if( data == null )
        {
            data = key;
            return;
        }

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
        // Comparison
        int cmp = data.compareTo(key);
        if( cmp == 0 )
        {
            // This node - delete
            deleteThis();
        }

        // Not this node, find the key
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

    /**
     *  @brief Prints the entire list from smallest to largest in order.
     *  @param key The key for the tree.
     *  @param parent The tree that points to the new tree.
     */
    public void printAll()
    {
        // Print small
        if( smaller != null )
        {
            smaller.printAll();
        }

        // Print self/middle
        if( data != null)
        {
            println(data);
        }

        // Print big
        if( bigger != null )
        {
            bigger.printAll();
        }
    }

    // ---------------- Helper methods ----------------

    private void println(String line)
    {
        System.out.println(line);
    }

    private void deleteThis()
    {
        if( bigger != null )
        {
            // Children - set new middle node
            BSTlex middle = findSmallest( bigger );
            middle.smaller = smaller;

            // Parent
            if( parent == null )
            {
                // Root node
            }
            else if( this == parent.bigger )
            {
                middle.parent = parent.bigger;
            }
            else if( this == parent.smaller )
            {
                middle.parent = parent.smaller;
            }
        }
        else if( smaller != null )
        {
            smaller.parent = parent;
        }
        else
        {
            // No children
        }
    }

    private BSTlex findSmallest( BSTlex node )
    {

    }

    /**
     *  @brief Creates a new tree.
     *  @param key The key for the tree.
     *  @param parent The tree that points to the new tree.
     */
    protected BSTlex( String key, BSTlex parent )
    {
        data = key;
        smaller = null;
        bigger = null;
        parent = parent;
    }

    // ----------------- Connections ------------------
    protected BSTlex smaller;
    protected BSTlex bigger;
    private BSTlex parent;

    // ------------------- Members --------------------
    private String data;
}
