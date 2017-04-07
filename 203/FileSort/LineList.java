/*
 * Name: Dominic Azaris
 * ID:   1297845
 */

public class LineList
{
    public LineList()
    {
        head = null;
    }

    public void push( String line )
    {
        Node new_head = new Node( line );
        if( head == null )
        {
            head = new_head;
        }
        else
        {
            new_head.next = head;
            head.previous = new_head;
            head = new_head;
        }
    }

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

        // Set the new head
        head = sorted_list.getHead();
        return comparisons;
    }

    public int qsort()
    {
        int comparisons = 0;

//         // Create Three new lists
//         LineList pivot = new LineList();
//         LineList small = new LineList();
//         LineList big = new LineList();
//
//         // Select head as the pivot ( insert it )
//         Node old_head = head;
//         pivot.push( old_head );

//         // Set current to head and loop through
//         while( head != null )
//         {
//             int cmp = pivot.getHead().compareTo( cur.getData() );
//             ++comparisons;
//             if( cmp >= 0 )
//             {
//                 // Push all larger into big bin
//                 Node node = head;
//                 big.push( node );
//             }
//             else
//             {
//                 // Push all smaller into small bin
//                 Node node = head;
//                 small.push( old_head );
//             }
//         }

//         // Sort the small and big queues
//         small.qsort();
//         big.qsort();
//
//         // Concat the lists
//         LineList new_list = new LineList();
//

        return 0;
    }

    public void printList()
    {
        Node current = head;
        while( current != null )
        {
            System.out.println( current.getData() );
            current = current.next;
        }
    }

    protected Node getHead()
    {
        return head;
    }

    protected int insertOrdered( Node node )
    {
        // Reset node
        node.next = null;
        node.previous = null;

        // Check for a head
        if( head == null )
        {
            head = node;
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
            if( cmp < 0 )
            {
                // Smaller, insert
                insertMid( cur.previous, node, cur );
                break;
            }
            else if( cur.next == null )
            {
                // Reached end of list, place at end
                insertMid( cur, node, null );
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

    private Node pop()
    {
        Node old_head = head;
        Node new_head = head.next;

        if( new_head != null )
        {
            new_head.previous = null;
        }
        head = new_head;

        if( old_head != null)
        {
            old_head.next = null;
        }

        return old_head;
    }

    private void insertMid( Node previous, Node new_node, Node next )
    {
        if( next == null )
        {
            // End of list
            previous.next = new_node;
            new_node.previous = previous;
            new_node.next = null;
        }
        else
        {
            // Middle of list
            if( previous == null )
            {
                // New head
                head = new_node;
            }

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

    private Node head;
    
    /**
     *
     */
    private class Node
    {
        public Node( String d )
        {
            data = d;
            next = null;
            previous = null;
        }
        public String getData()
        {
            return data;
        }
        public Node next;
        public Node previous;
        private String data;
    }
}
