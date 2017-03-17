public class Node< T extends Comparable<T> >
{
    // Constructor
    public Node( T data )
    {
        node_data = data;
    }

    public T getData()
    {
        return node_data;
    }

    // The data
    private T node_data;

    // The next/previous node
    public Node<T> next = null;
    public Node<T> previous = null;
}