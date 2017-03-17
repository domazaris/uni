public class LinkedList< T >
{
    // Members
    private Node< T > head;

    // Construction
    public LinkedList() {}

    // add / remove
    public int insert( T data ) 
    {
        if( head == null )
        {
            head = new Node< T >(data);
            return -1;
        }

        // Check if it is in the array
        if( ! moveToFront( data ) )
        {
            //Create the node
            Node< T > node = new Node< T >( data );

            // Setup the list
            insertFront( node );
            return -1;
        }

        // If not, insert it to the head
        return 0;
    }


    public Boolean remove( int index ) 
    { 
        Node< T > current = head;
        int counter = 0;
        while( current != null )
        {
            if( counter++ == index )
            {
                return true;
            }
        }
        return false;
    };

    // indexing
    public T at( int index ) 
    {
        Node< T > current = head;
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

    // Sort
    private Boolean moveToFront( T data )
    {
        // Check if the item is at the front
        if( head.getData().compareTo( data ) )
            return true;;
        
        // Set up current as the front node
        Node< T > current = head;
        
        // Traverse the list
        while( current != null )
        {
            // Go to next item
            if( current.getData() == data )
            {
                // MTF
                // Move connect the next and previous node
                current.previous.next = current.next;
                current.next.previous = current.previous;

                // Insert into the front
                insertFront( current );

                // Return true as it was found and moved
                return true;
            }
            current = current.next;
        }

        // Return false as the item was not found
        return false;
    }

    private void insertFront( Node< T > new_head ) 
    {
        // Point the current head to the new head
        head.previous = new_head;
        new_head.next = head;

        // Insert to the head
        head = new_head;
    }

    // privates
    private void expandArray() 
    {

    }
}