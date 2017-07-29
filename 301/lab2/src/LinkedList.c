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
    // Check if list is empty
    if( *head == NULL )
        return NULL;

    // Find node in list
    Node_t* new_head = *head;
    Node_t* cur = *head;
    Node_t* prev = NULL;

    if( *head == node )
        new_head = (Node_t*)(*head)->next;

    while( cur != NULL )
    {
        // Check node
        if( cur = node )
        {
            // Check left
            if( prev != NULL )
                prev->next = (void*)node->next;

            // Check right
            if( cur->next != NULL && prev != NULL )
                cur->next = prev->next;

            // Check if only node in list
            if( prev == NULL &&  cur->next == NULL )
                *head = NULL;

            return new_head;
        }
        else
        {
            // Move along, move along
            prev = cur;
            cur = (Node_t*)cur->next;
        }
    }

    // Shouldn't get here
    return new_head;
}

void traverse( Node_t* head )
{
    Node_t* cur = head;
    while( cur != NULL )
    {
        printf( "Process ID:\t\t%d\nQueue:\t\t\t%d\nLottery Ticket:\t\t%d\n\n", cur->pid, cur->q, cur->lt );
        cur = (Node_t*)cur->next;
    }
}

Node_t* find( Node_t* head )
{
  return NULL;
}
