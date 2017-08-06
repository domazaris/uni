#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Scheduler.h"

void addTask( Scheduler_t* s, int pid, int q, int priority )
{
    // Create Node
    Node_t* new_node = (Node_t*)malloc( sizeof( Node_t ) );
    memset( new_node, 0, sizeof( Node_t ) );

    // Set values
    new_node->pid = pid;
    new_node->q = q;
    new_node->lt = priority;
    
    // Assign it lottery tickets
    new_node->tickets = (size_t*)malloc( priority * sizeof( size_t ) );
    for( size_t i = 0; i < priority; i++ )
    {
        new_node->tickets[ i ] = s->lottery_count;
        s->lottery_count += 1;
    }

    // Add to list
    insert_node( s->tasks, new_node );
}

size_t drawLottery( size_t max_value )
{
    return rand() % max_value;
}

void execute( Scheduler_t* s )
{
    while( ! isEmpty( s ) )
    {
        // Draw lottery

        // Find node from ticket

        // Execute successful ticket

        // Delete node from list
    }
    fprintf( stderr, "Done\n");
}

int isEmpty( Scheduler_t* s )
{
    // return true if p_count is 0
    return ! s->p_count;
}