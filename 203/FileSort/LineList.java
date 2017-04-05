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
        LineList sorted_list = new LineList();
        int comp_count = 0;

        // Insert every node in the list into a sorted list
        Node current = head;
        while( current != null )
        {
            Node next = current.next;
            comp_count += sorted_list.insertOrdered( current );
            current = next;
        }

        // Point the head to the other lists head
        head = sorted_list.getHead();

        return comp_count;
    }

    public int qsort()
    {
        int comparisons = 0;

        // Create Three new lists
        List pivot = new List();
        List small = new List();
        List big = new List();

        // Select head as the pivot ( insert it )
        Node old_head = head;
        pivot.push( old_head );

        // Set current to head and loop through
        while( head != null )
        {
            int cmp = pivot.getHead().compareTo( cur.getData() );
            ++comparisons;
            if( cmp >= 0 )
            {
                // Push all larger into big bin
                Node node = head;
                big.push( node );
            }
            else
            {
                // Push all smaller into small bin
                Node node = head;
                small.push( old_head );
            }
        }

        // Sort the small and big queues
        small.qsort();
        big.qsort();

        // Concat the lists
        List new_list = new List();


        return 0;
    }

    public void printList()
    {
        Node current = head;
        while( current != null )
        {
//             System.out.println( current.getData() );
            current = current.next;
        }
    }

    protected Node getHead()
    {
        return head;
    }

    protected int insertOrdered( Node node )
    {
        int comparisons = 0;
//         if( head == null )
//         {
//             head = node;
//         }
//         else
//         {
//             Node current = head;
//             while( current != null )
//             {
//                 // node lower than current - place here
//                 int cmp = current.getData().compareTo( node.getData() );
//                 ++comparisons;
// //                 System.out.println( cmp + " " + current.getData().charAt(0) + " " + node.getData().charAt(0) );
//
//                 current = current.next;
//             }
//         }
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
