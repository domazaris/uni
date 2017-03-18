public class Node< T >
{
    // Constructor
    public Node( T data )
    {
        node_data = data;
        next = null;
        previous = null;
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
