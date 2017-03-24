public class WordList
{
    // --------- Members --------- \\
    
    private Node< String > head;

    // -------- Interface -------- \\

    public WordList()
    {
        head = null;
    }

    public int insert( String data )
    {
        if( head == null )
        {
            head = new Node< String >( data );
            return 0;
        }

        // Check if it is in the array
        return insertFront( data );
    }

    public String at( int index )
    {
        // Get the node at the index
        Node< String > node = getNodeAt( index );
        
        // Move the node to the front
        moveToFront( node );

        // Return the data
        return node.getData();
    }

    // --------- Private --------- \\

    private void pop( Node< String > node )
    {
        // Move connect the next and previous node
        if( node.previous != null )
        {
            node.previous.next = node.next;
        }
        if( node.next != null )
        {
            node.next.previous = node.previous;
        }

        // reset the node refs
        node.previous = null;
        node.next = null;
    }

    private Node< String > getNodeAt( int index )
    {
        // Set the node to the head
        Node< String > current = head;
        
        // Iterate the list
        for(int i = 1; i < index; i++ )
        {
            if( current == null )
                return null;
            current = current.next;
        }
        return current;
    }

    private int insertFront( String data ) 
    {
        // Search for the node
        Node< String > current = head;
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
            current = current.next;
            counter++;
        }

        // Not found - insert front
        Node< String > new_head = new Node< String >( data );
        moveToFront( new_head );
        return 0;
    }

    private void moveToFront( Node< String > new_head )
    {
        // Check if already at front
        if( new_head.getData().equals( head.getData() ) )
        {
            return;
        }

        // Take the node from the graph
        pop( new_head );

        // Point the current head to the new head
        new_head.next = head;
        if( head != null )
        {
            head.previous = new_head;
        }

        // Insert to the head
        head = new_head;
    }
}
