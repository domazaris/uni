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

        int cmp = data.compareTo( key );

        // Not this node, find the key
        if( cmp < 0 )
        {
            if( bigger != null )
            {
                bigger.delete(key);
            }
        }
        else if( cmp > 0 )
        {
            if( smaller != null )
            {
                smaller.delete(key);
            }
        }
        else
        {
            if( childCount() == 1 )
            {
                deleteOneChild();
            }
            else if( childCount() == 2 )
            {
                deleteTwoChildren();
            }
            else
            {
                deleteNoChildren();
            }

            // TODO: Add this back in
            data = null;
        }
    }

    private void deleteNoChildren()
    {
        // No children
        if( parent != null )
        {
            if( parent.smaller == this )
            {
                parent.smaller = null;
            }
            else if( parent.bigger == this )
            {
                parent.bigger = null;
            }
        }
        parent = null;
    }

    private void deleteOneChild()
    {
        if( parent == null )
        {
            // Root node
            if( smaller != null )
            {
                // Swap root with smaller
                String temp = data;
                data = smaller.swapKey( data );
                smaller.delete( temp );
            }
            else if( bigger != null)
            {
                String temp = data;
                data = bigger.swapKey( data );
                bigger.delete( temp );
            }
        }
        else
        {
            println("DELETEING: " + data);
            if( smaller != null )
            {
                if( parent.smaller == this )
                {
                    parent.smaller = smaller;
                }
                if( parent.bigger == this )
                {
                    parent.bigger = smaller;
                }
            }
            else if (bigger != null)
            {
                if( parent.smaller == this )
                {
                    parent.smaller = bigger;
                }
                if( parent.bigger == this )
                {
                    parent.bigger = bigger;
                }
            }
        }
    }

    private void deleteTwoChildren()
    {
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
     *  @param p The tree that points to the new tree.
     */
    protected BSTlex( String key, BSTlex p )
    {
        this.data = key;
        this.smaller = null;
        this.bigger = null;
        this.parent = p;
    }

    protected String swapKey( String key )
    {
        String temp = data;
        data = key;
        return temp;
    }

    /**
     *  @brief Returns the amount of children in the tree node.
     *  @return The amount of children in the tree node.
     */
    protected int childCount()
    {
        int count = 0;
        if( smaller != null )
        {
            count++;
        }
        if( bigger != null )
        {
            count++;
        }
        return count;
    }

    // ----------------- Connections ------------------
    private BSTlex smaller;
    private BSTlex bigger;
    private BSTlex parent;

    // ------------------- Members --------------------
    private String data;
}
