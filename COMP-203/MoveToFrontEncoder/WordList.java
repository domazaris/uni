/*
 * Name: Dominic Azaris
 * ID:   1297845
 */

public class WordList
{
    // ----------- Node ---------- \\

    class Node
    {
        private String data;
        private Node next;
        private Node previous;

        public Node( String word )
        {
            data = word;
        }

        public String getData()
        {
            return data;
        }

        public void setNext( Node n )
        {
            next = n;
        }

        public Node getNext()
        {
            return next;
        }

        public void setPrevious( Node p )
        {
            previous = p;
        }

        public Node getPrevious()
        {
            return previous;
        }
    }

    // --------- Members --------- \\
    
    private Node head;

    // -------- Interface -------- \\

    public WordList()
    {
        head = null;
    }

    public int insert( String data )
    {
        if( head == null )
        {
            head = new Node( data );
            return 0;
        }

        // Check if it is in the array
        return insertFront( data );
    }

    public String at( int index )
    {
        // Get the node at the index
        Node node = getNodeAt( index );
        
        // Move the node to the front
        moveToFront( node );

        // Return the data
        return node.getData();
    }

    // --------- Private --------- \\

    private void pop( Node node )
    {
        // Move connect the next and previous node
        if( node.previous != null )
        {
            node.getPrevious().setNext( node.getNext() );
        }
        if( node.next != null )
        {
            node.getNext().setPrevious( node.getPrevious() );
        }

        // reset the node refs
        node.setPrevious( null );
        node.setNext( null );
    }

    private Node getNodeAt( int index )
    {
        // Set the node to the head
        Node current = head;
        
        // Iterate the list
        for(int i = 1; i < index; i++ )
        {
            if( current == null )
                return null;
            current = current.getNext();
        }
        return current;
    }

    private int insertFront( String data ) 
    {
        // Search for the node
        Node current = head;
        int counter = 1;
        while( current != null )
        {
            // Check if the data matches
            if( data.equals( current.getData() ) )
            {
                // pop and mtf
                moveToFront( current );
                return counter;
            }

            // Go to next node
            current = current.getNext();
            counter++;
        }

        // Not found - insert front
        Node new_head = new Node( data );
        moveToFront( new_head );
        return 0;
    }

    private void moveToFront( Node new_head )
    {
        // Check if already at front
        if( new_head.getData().equals( head.getData() ) )
        {
            return;
        }

        // Take the node from the graph
        pop( new_head );

        // Point the current head to the new head
        new_head.setNext( head );
        if( head != null )
        {
            head.setPrevious( new_head );
        }

        // Insert to the head
        head = new_head;
    }
}
