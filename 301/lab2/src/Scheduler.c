#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include "Scheduler.h"

const size_t QUANTA = 20 * 1e3; // 20ms

void addTask( Scheduler_t* s, size_t pid, size_t q, size_t priority )
{
    // Create Node
    Node_t* new_node = (Node_t*)malloc( sizeof( Node_t ) );
    memset( new_node, 0, sizeof( Node_t ) );

    // Set values
    new_node->pid = pid;
    new_node->q = q;
    new_node->priority = priority;
    
    // Assign it lottery tickets
    new_node->tickets = (size_t*)malloc( priority * sizeof( size_t ) );
    for( size_t i = 0; i < priority; i++ )
    {
        new_node->tickets[ i ] = s->lottery_count;
        s->lottery_count += 1;
    }

    // increment stat counts
    switch( new_node->priority )
    {
        case 20:
        {
            s->total_exe[0] += 1;
            break;
        }
        case 10:
        {
            s->total_exe[1] += 1;
            break;
        }
        case 5:        
        {
            s->total_exe[2] += 1;
            break;
        }
        case 1:
        {
            s->total_exe[3] += 1;
            break;
        }
        default:
        {
            break;
        }
    }

    // Add to list
    if( s->tasks == NULL )
    {
        s->tasks = (Node_t**)malloc( sizeof(Node_t*) );
    }

    insert_node( s->tasks, new_node );

    // Increment process count
    s->p_count += 1;
}

Node_t* findTicket( Scheduler_t* s, size_t ticket )
{
    // Iterate list
    Node_t* node = (Node_t*)*s->tasks;
    while( node != NULL )
    {
        // Check tickets
        for( size_t i = 0; i < node->priority; i++ )
        {
            if( node->tickets[ i ] == ticket )
            {
                // Return winning node
                return node;
            }
        }
        node = node->next;
    }

    // If no node is found
    return NULL;
}

size_t drawLottery( size_t max_value )
{
    return rand() % max_value;
}

void execute( Scheduler_t* s, Node_t* n )
{
    // Sleep for one quanta
    usleep( QUANTA );

    fprintf( stdout, "%zu %zums\n", n->pid, n->priority );

    // Increment scheduler counters
    switch( n->priority )
    {
        case 20:
        {
            s->total_exe_time[ 0 ] += QUANTA + 1;
            break;
        }
        case 10:
        {
            s->total_exe_time[ 1 ] += QUANTA + 1;
            break;
        }
        case 5:        
        {
            s->total_exe_time[ 2 ] += QUANTA + 1;
            break;
        }
        case 1:
        {
            s->total_exe_time[ 3 ] += QUANTA + 1;
            break;
        }
        default:
        {
            break;
        }
    }
}

void run( Scheduler_t* s )
{
    while( ! isEmpty( s ) && s != NULL )
    {
        // Run until winner is selected
        Node_t* node = NULL;
        while( node == NULL )
        {
            // Draw lottery
            size_t winner = drawLottery( s->lottery_count );
            
            // Find node from ticket
            node = findTicket( s, winner );
        }

        // Execute successful ticket
        execute( s, node );

        // Delete node from list
        node->q -= 1;
        if( node->q == 0 )
        {
            delete_node( s->tasks, node );
            s->p_count -= 1;
        }
    }
    fprintf(stdout, "\n");
}

int isEmpty( Scheduler_t* s )
{
    // return true if p_count is 0
    return ! s->p_count;
}
