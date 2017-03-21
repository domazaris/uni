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
        int location = getIndex( data );

        // If the node doesn't exist
        if( location == 0 )
        {
            // Create a new one & insert it
            Node< String > node = new Node< String >( data );
            insertFront( node );
            return 0;
        }
        else
        {
            // Move to front
            Node< String > node = getNodeAt( location );
            insertFront( node );

            // Return the location
            return location;
        }
    }

    public void pop( Node< String > node )
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

    public String at( int index )
    {
        // Get the node at the index
        Node< String > node = getNodeAt( index );
        
        // Move the node to the front
        insertFront( node );

        // Return the data
        return node.getData();
    }

    // --------- Private --------- \\

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

    private int getIndex( String data )
    {
        // Set the node to the head
        Node< String > current = head;
        
        // Iterate through each node
        int counter = 1;
        while( current != null )
        {
            // Check if the data matches
            if( data.compareTo( current.getData() ) == 0 )
            {
                return counter;
            }

            // Go to next node
            current = current.next;
            counter++;
        }
        return 0;
    }

    private void insertFront( Node< String > new_head ) 
    {
        // Check if already at front
        if( new_head.getData().compareTo( head.getData() ) == 0 )
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
