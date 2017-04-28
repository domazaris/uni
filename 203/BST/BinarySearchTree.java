public class BinarySearchTree
{
    public BinarySearchTree( int key )
    {
        my_key = key;
        smaller = null;
        bigger = null;
        previous = null;
    }

    public void insert( int key )
    {
        // Return if the key is in the tree
        if( hasKey( key ) )
        {
            return;
        }

        // Else insert
        if( my_key < key )
        {
            // Bigger
            if( bigger == null )
            {
                // No bigger, create a bigger
                bigger = new BinarySearchTree( key );
                return;
            }
            bigger.insert( key );
        }
        else
        {
            // Smaller
            if( smaller == null )
            {
                // No smaller, create a smaller
                smaller = new BinarySearchTree( key );
                return;
            }
            smaller.insert( key );
        }
    }

    public void remove( int key )
    {
        // Remove this node
        if( my_key == key ) )
        {

            return;
        }

        // Find key
        if( my_key < key )
        {
            if( bigger != null )
            {
                bigger.remove(key);
            }
        }
        else
        {
            if( smaller != null )
            {
                smaller.remove(key);
            }
        }
    }

    public Boolean hasKey( int key )
    {
        if( my_key == key )
        {
            return true;
        }
        else if( my_key < key )
        {
            if( bigger == null )
            {
                return false;
            }
            return bigger.hasKey( key );
        }
        else
        {
            if( smaller == null )
            {
                return false;
            }
            return smaller.hasKey( key );
        }
    }

    // ----------------- Connections ------------------
    protected BinarySearchTree smaller;
    protected BinarySearchTree bigger;
    protected BinarySearchTree previous;

    // --------- Don't touch my privates mr bad man ---------------
    private int my_key;
}
