public class WordList
{
    // Members
    private Node< String > head;

    public WordList()
    {
        head = null;
    }

    public int insert( String data )
    {
        if( head == null )
        {
            head = new Node< String >(data);
            return 0;
        }

        // Check if it is in the array
        int location = moveToFront( data );
        if( location >= 0 )
        {
            return location;
        }

        // If not, insert it to the head
        Node< String > node = new Node< String >( data );
        insertFront( node );
        return 0;
    }

    // indexing
    public String at( int index )
    {
        Node< String > current = head;
        int counter = 0;
        while( current != null )
        {
            if( counter++ == index )
                return current.getData();

            // Go to next node
            current = current.next;
        }
        return null;
    }

    // Moves the node with matching data to the front. Returns the previous location
    private int moveToFront( String data )
    {
        // Set up current as the front node
        Node< String > current = head;

        // Traverse the list
        int counter = 0;
        while( current != null )
        {
            // Go to next item
            if( current.getData().compareTo( data ) == 0 )
            {
                // MTF
                // Move connect the next and previous node
                if( current.previous != null )
                    current.previous.next = current.next;
                if( current.next != null )
                    current.next.previous = current.previous;

                // Insert into the front
                insertFront( current );

                // Return counter as it was found and moved
                return counter;
            }
            current = current.next;
            counter++;
        }

        // Return -1 as the item was not found
        return -1;
    }

    private void insertFront( Node< String > new_head ) 
    {
        // Point the current head to the new head
        if( head != null )
            head.previous = new_head;
        new_head.next = head;

        // Insert to the head
        head = new_head;
    }
}
