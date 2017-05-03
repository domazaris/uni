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
        if( data == null )
        {
            return false;
        }

        int cmp = data.compareTo( key );
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
        if( data == null )
        {
            return;
        }

        // Comparison
        int cmp = data.compareTo(key);
        if( cmp == 0 )
        {
            // This node - delete
            println("PRED: " + key);
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

    /**
     *  @brief Removes the current tree node from the tree.
     */
    protected void deleteThis()
    {
        println("DATA: " + data);
        if( bigger != null )
        {
            // Bigger - swap key
            BSTlex smallest = bigger.findSmallest();
            data = smallest.swapKey( data );

            if( smallest.bigger != null )
            {
                bigger = smallest.bigger;
            }
        }
        else if( smaller != null )
        {
            // Has smaller - swap key
            BSTlex biggest = smaller.findBiggest();
            data = biggest.swapKey( data );

            if( biggest.smaller != null )
            {
                smaller = biggest.smaller;
            }
        }

        // Remove Node
        if( parent != null )
        {
            // Leaf
            if( parent.smaller == this )
            {
                parent.smaller = null;
            }
            else if( parent.bigger == this )
            {
                parent.bigger = null;
            }
            parent = null;
        }
        else if( parent == null )
        {
            // Leaf with no parent - i.e. root
            data = null;
        }
    }

    protected BSTlex findSmallest()
    {
        if( smaller != null )
        {
            return smaller.findSmallest();
        }
        else
        {
            // This is the last node in the tree
            return this;
        }
    }

    protected BSTlex findBiggest()
    {
        if( bigger != null )
        {
            return bigger.findBiggest();
        }
        else
        {
            // This is the last node in the tree
            return this;
        }
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

    protected String swapKey( String key )
    {
        println("SWAP: " + key + ", " + data);
        String temp = data;
        data = key;
        return data;
    }

    // ----------------- Connections ------------------
    private BSTlex smaller;
    private BSTlex bigger;
    private BSTlex parent;

    // ------------------- Members --------------------
    private String data;
}
