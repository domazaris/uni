public class BinarySearchTree 
{
    private Node root;
    public void insert(int key)
    {

    }

    public Node remove(int key)
    {
        // Find the node in the tree
        Node n = find( key );

        // If found
        if( n != null )
        {
            if( n == root )
            {
                // Root node
                

            }
            else if( n.small == null && n.big == null)
            {
                // Leaf node
            }
            else if( n.small != null && n.big == null)
            {
                // Just small child

            }
            else if( n.small == null && n.big != null)
            {
                // Just big child
            }
            else
            {
                // Two children 
                Node successor = findSuccessor( n ); // left most in right subtree

                // Swap nodes
                swapNodes( n, successor );

                // Remove n
            }
            return n;
        }

        return n;        
    }

    public Node find(int key)
    {
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

    private Node findSuccessor( Node n )
    {
        // left most in right subtree  
        Node cur = n.big;
        while( cur.small != null )
        {
            cur = cur.small;
        }
        return cur;
    } 

    private Node swapNodes( Node n, Node successor )
    {
    
    }
    
    // TODO: change from node to BST - self referential
    private class Node
    {
        Node(int k)
        {
            key = k;
            parent = null;
            small = null;
            big = null;
        }
        public int key;
        public Node parent; // TODO:  change from node to BST
        public Node small; // TODO:  change from node to BST
        public Node big; // TODO:  change from node to BST
    }
}