/*
 * Name: Dominic Azaris
 * ID:   1297845
 */

 /**
  * This class is a list data structure that takes strings as nodes.
  * It also contains different sorting methods.
  */
public class LineList
{
    /**
     * Constructor
     */
    public LineList()
    {
        // Set the head and tail to null
        head = null;
        tail = null;
    }

    /**
     * Pushes a new string onto the front of the list.
     *
     * @param line The item to push onto the list.
     */
    public void pushFront( String line )
    {
        // Create a node and push it into the list
        Node new_head = new Node( line );
        pushFront( new_head );
    }

    /**
     * Prints the entire list to stdout, with a newline character between elements.
     */
    public void printList()
    {
        // Set current to the start of the list
        Node current = head;
        while( current != null )
        {
            // Print the data from each node, then iterate
            System.out.println( current.getData() );
            current = current.next;
        }
    }

    /**
     * Sorts the list using the insertions sort algorithm. This has n^2 comparisons.
     *
     * @return A count of how many string comparisons were done.
     */
    public int isort()
    {
        // Create a new list
        LineList sorted_list = new LineList();

        // Insert everything into the new list in order of small to large
        int comparisons = 0;
        while( head != null )
        {
            Node old_head = head;
            head = head.next;
            comparisons += sorted_list.insertOrdered( old_head );
        }

        // Set the new head/tail
        head = sorted_list.getHead();
        tail = sorted_list.getTail();
        return comparisons;
    }

    /**
     * Sorts the list using the quick sort algorithm. This has n * log(n) comparisons.
     *
     * @return A count of how many string comparisons were done.
     */
    public int qsort()
    {
        int comparisons = 0;

        if( head == null )
        {
            // Cant sort list as it has nothing here
            return 0;
        }

        // Create Three new lists
        LineList pivot = new LineList();
        LineList small = new LineList();
        LineList big = new LineList();

        // Pop off the head and push it into the pivot list
        pivot.pushFront( popFront() );

        // Iterate and sort into the small/big lists
        Node current = null;
        while( ( current = popFront() ) != null )
        {
            // Compare
            int cmp = current.getData().compareTo( pivot.getHead().getData() );
            comparisons++;

            if( cmp < 0 )
            {
                // Push onto the smaller list
                small.pushFront( current );
            }
            else
            {
                big.pushFront( current );
            }
        }

        // Sort the lists
        comparisons += small.qsort();
        comparisons += big.qsort();

        // Concat the lists
        LineList sorted_list = new LineList();
        pushBackList( small, sorted_list );
        pushBackList( pivot, sorted_list );
        pushBackList( big, sorted_list );

        // Set the new head of the current list
        head = sorted_list.getHead();
        tail = sorted_list.getTail();

        return comparisons;
    }

    /**
     * Gets the head of the list.
     *
     * @return The head node.
     */
    protected Node getHead()
    {
        return head;
    }

    /**
     * Gets the tail of the list.
     *
     * @return The tail node.
     */
    protected Node getTail()
    {
        return tail;
    }

    /**
     * Inserts a node into the list in ascending order.
     *
     * @param node The node to insert
     * @return The amount of string comparisons that took place.
     */
    protected int insertOrdered( Node node )
    {
        // Reset node
        node.next = null;
        node.previous = null;

        // Check for a head
        if( head == null )
        {
            head = node;
            tail = head;
            return 0;
        }

        // Iterate the list to find the new nodes place
        int comparisons = 0;
        Node cur = head;
        while( cur != null )
        {
            // Do comparisons
            int cmp = cur.getData().compareTo( node.getData());
            ++comparisons;
            if( cmp > 0 )
            {
                // Larger, insert
                insert( cur.previous, node, cur );
                break;
            }
            else if( cur.next == null )
            {
                // Reached end of list, place at end
                insert( cur, node, null );
                break;
            }
            else
            {
                // Bigger or equal, continue
                cur = cur.next;
            }
        }
        return comparisons;
    }

    /**
     * Pops the front of the list off.
     *
     * @return The old head of the list.
     */
    private Node popFront()
    {
        // Check if the list is empty
        if( head == null )
        {
            return null;
        }

        // Save the old head
        Node old_head = head;
        Node new_head = head.next;

        // Set up the new heads connections
        if( new_head != null )
        {
            new_head.previous = null;
        }

        // Set head to the new head
        head = new_head;

        // Set the old heads connections
        if( old_head != null)
        {
            old_head.next = null;
        }

        // Return the original head
        return old_head;
    }


    /**
     * Pushes a node onto the front of the list
     *
     * @param new_head The node to add to the list
     */
    private void pushFront( Node new_head )
    {
        if( head == null )
        {
            head = new_head;
            tail = new_head;
        }
        else
        {
            new_head.next = head;
            head.previous = new_head;
            head = new_head;
        }
    }

    /**
     * Pushes a node onto the back of the list
     *
     * @param new_tail The node to add to the list
     */
    private void pushBack( Node new_tail )
    {
        if( tail == null )
        {
            // List is empty
            head = new_tail;
            tail = head;
        }
        else
        {
            // Add new tail to list
            new_tail.previous = tail;
            tail.next = new_tail;
            tail = new_tail;
        }
    }

    /**
     * Pushes an entire list onto the back of a list.
     *
     * @param src The list that will be added.
     * @param dest The list that will be extended
     */
    private void pushBackList( LineList src, LineList dest )
    {
        // Push every element onto the back of the list
        Node current = null;
        while( ( current = src.popFront() ) != null )
        {
            dest.pushBack( current );
        }
    }

    /**
     * Inserts a new in between two other nodes.
     *
     * @param previous The new nodes previous
     * @param new_node The new node
     * @param next The new nodes next
     */
    private void insert( Node previous, Node new_node, Node next )
    {
        if( next == null )
        {
            // End of list
            previous.next = new_node;
            new_node.previous = previous;
            new_node.next = null;
            tail = new_node;
        }
        else
        {
            // New head
            if( previous == null )
            {
                head = new_node;
            }

            // Middle of list
            // Set the new middle nodes pointers
            new_node.next = next;
            new_node.previous = next.previous;

            // Set next and previous
            new_node.next.previous = new_node;
            if( new_node.previous != null )
            {
                new_node.previous.next = new_node;
            }
        }
    }

    /**
     * The head of the list
     */
    private Node head;

    /**
     * The tail of the list
     */
    private Node tail;

    /**
     * This class wraps some data with next and previous pointers.
     */
    private class Node
    {
        /**
         * Constructs a node
         *
         * @param d The data that the node will hold
         */
        public Node( String d )
        {
            // Set the data to d
            data = d;

            // Set next and prev to null
            next = null;
            previous = null;
        }

        /**
         * Gets the data associated with the node.
         *
         * @return The data as a String
         */
        public String getData()
        {
            return data;
        }

        public Node next;
        public Node previous;
        private String data;
    }
}
