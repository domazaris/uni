public class BinarySearchTree 
{
    private Node root;
    public void insert(int key);
    public void remove(int key);

    public Node find(int key)
    {
        // Check if the tree exists
        if( root==null )
        {
            return null;
        }

        // Set current to top of the tree
        Node cur = root;

        // Iterate through tree
        while( cur != null )
        {
            if(cur.key > key)
            {
                // Larger
                cur = cur.big;
            }
            else if(cur.key < key)
            {
                // Smaller
                cur = cur.small;
            }
            else if(cur.key == key)
            {
                // Same
                return cur;
            }
        }

        // key is not in tree 
        return null;
    }
    
    private class Node
    {
        Node(int k)
        {
            key = k;
        }
        public int key;
        public Node small;
        public Node big;
    }
}