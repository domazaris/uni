#include <stddef.h>
#include <stdio.h>
#include "LinkedList.h"

int insert_node( Node_t** head, Node_t* node )
{
    // Check if list is empty
    size_t idx = 0;
    if( *head == NULL )
    {
        *head = node;
        return idx;
    }

    // Get to end of list
    Node_t* cur = *head;
    while( cur != NULL )
    {
        // Check if at end of list
        if( cur->next == NULL )
        {
            // End of list, add node
            cur->next = (void*)node;
            return idx;
        }

        // Goto next node
        cur = (Node_t*)cur->next;
        idx += 1;
    }

    // Error
    return -1;
}

Node_t* delete_node( Node_t** head, Node_t* node )
{
    if( head == NULL || *head == NULL || node == NULL )
    {
        return NULL;
    }

    Node_t* updated_head = *head;

    if( node == *head )
    {
        updated_head = (*head)->next;
        return updated_head;
    }
    
    // Find node
    Node_t* cur = (*head)->next;
    Node_t* pre = *head;
    while( cur != NULL )
    {
        if( node == cur )
        {
            // Delete cur
            pre->next = cur->next;
            return updated_head;
        }

        // Iterate
        pre = cur;
        cur = cur->next;
    }

    // Couldn't find node
    return updated_head;
}

void traverse( Node_t* head )
{
    Node_t* cur = head;
    while( cur != NULL )
    {
        printf( "Process ID:\t\t%zu\nQueue:\t\t\t%zu\nLottery Ticket:\t\t%zu\n\n", cur->pid, cur->q, cur->priority );
        cur = (Node_t*)cur->next;
    }
}

Node_t* find( Node_t* head )
{
  return NULL;
}
