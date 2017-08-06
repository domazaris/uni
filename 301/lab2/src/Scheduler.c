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

Node_t* findTicket( size_t ticket )
{
    fprintf(stderr, "finding\n");

    // Iterate list

    // Check tickets

    // Return winning node

    // Continue

    return NULL;

    fprintf(stderr, "found\n");
}

size_t drawLottery( size_t max_value )
{
    return rand() % max_value;
}

void execute( int pid )
{
    fprintf( stdout, "%d", pid );
    fflush( stdout );
}

void run( Scheduler_t* s )
{
    while( ! isEmpty( s ) && s != NULL )
    {
        fprintf(stdout, ".");
        fflush( stdout );

        // Draw lottery
        size_t winner = drawLottery( s->lottery_count );

        // Find node from ticket
        Node_t* node = findTicket( winner );

        // Execute successful ticket
        execute( node->pid );

        // Delete node from list
        delete_node( s->tasks, node );
    }
    fprintf( stderr, "Done\n");
}

int isEmpty( Scheduler_t* s )
{
    // return true if p_count is 0
    return ! s->p_count;
}