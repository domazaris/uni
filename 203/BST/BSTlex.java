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
            // No root - Insert here
            data = key;
            print(data + " ");
            return;
        }
        else
        {
            // Print
            print(data + " ");
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
                print(key + " ");
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
                print(key + " ");
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
        // Root of tree
        if( data == null )
        {
            return false;
        }

        // Comparison
        int cmp = data.compareTo( key );
        if( cmp == 0 )
        {
            // Match - return
            return true;
        }
        else if( cmp < 0 )
        {
            // Bigger - find in bigger tree
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
            // Smaller - find in smaller tree
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
        // delete
        delete( key, false );
    }

    /**
     *  @brief Removes a key from the tree
     *  @param key The key to remove.
     *  @param quiet Flags the method to print the word or not.
     */
    protected void delete( String key, Boolean quiet )
    {
        if( data == null )
        {
            // Empty root
            return;
        }
        else if( !quiet )
        {
            print(data + " ");
        }

        // Compare
        int cmp = data.compareTo( key );

        // Not this node, find the key
        if( cmp < 0 )
        {
            // Delete from bigger tree
            if( bigger != null )
            {
                bigger.delete(key);
            }
        }
        else if( cmp > 0 )
        {
            // Delete from smaller tree
            if( smaller != null )
            {
                smaller.delete(key);
            }
        }
        else
        {
            // Delete logic
            if( childCount() == 1 )
            {
                // One child
                deleteOneChild();
            }
            else if( childCount() == 2 )
            {
                // Two Children
                deleteTwoChildren();
            }
            else
            {
                // No Children
                deleteLeaf();
            }
        }
    }

    /**
     *  @brief Removes a tree node from a leaf of the tree.
     */
    protected void deleteLeaf()
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
        data = null;
    }

    /**
     *  @brief Removes a tree node that has one child.
     */
    protected void deleteOneChild()
    {
        if( parent == null )
        {
            // Root node
            if( smaller != null )
            {
                // Swap root with smaller
                String temp = data;
                data = smaller.swapKey( temp );
                smaller.delete( temp, true );
            }
            else if( bigger != null)
            {
                String temp = data;
                data = bigger.swapKey( temp );
                bigger.delete( temp, true );
            }
        }
        else
        {
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
                smaller.parent = parent;
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
                bigger.parent = parent;
            }
            parent = null;
            smaller = null;
            bigger = null;
            data = null;
        }
    }

    /**
     *  @brief Removes a tree node that has two children.
     */
    protected void deleteTwoChildren()
    {
        // Swap data with smallest in big tree
        String temp = data;
        BSTlex pivot = bigger.findSmallest();
        data = pivot.swapKey( temp );

        // Remove the pivot ( will be a leaf )
        pivot.delete( temp, true );
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
            print(data + "\n");
        }

        // Print big
        if( bigger != null )
        {
            bigger.printAll();
        }
    }

    // ---------------- Helper methods ----------------

    /**
     *  @brief Helper print method to reduce typing.
     *  @param str The string to print
     */
    private void print( String str )
    {
        System.out.print( str );
    }

    /**
     *  @brief Finds the smallest node in the tree
     *  @return The tree node with the smallest value.
     */
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

    /**
     *  @brief Swaps the keys between two nodes.
     *  @param key The key to swap into the node.
     *  @return The key that was replaced.
     */
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
    protected BSTlex smaller;
    protected BSTlex bigger;
    protected BSTlex parent;

    // ------------------- Members --------------------
    private String data;
}
