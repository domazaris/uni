#pragma once
#include <stddef.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include "LinkedList.h"

/**
 * Parses a csv file and outputs a LinkedList with each line
 * as a node.
 * @param filename The input csv file.
 * @returns The head of a linked list
 */
Node_t* parseFile( const char* filename )
{
    // Open file
    FILE* fd = fopen( filename, "r" );
    if( fd == NULL )
    {
        fprintf( stderr, "File read error: %s", strerror( errno ) );
        abort();
    }

    // Ignore first line
    char* string = (char*)malloc( 256 );
    fgets( string, 100, fd );

    // Start list
    Node_t* head = NULL;

    // Read a line at a time
    while( fgets( string, 100, fd ) != NULL )
    {
        int i = atoi( strtok( string, "," ) );
        int q = atoi( strtok( NULL, "," ) );
        int l = atoi( strtok( NULL, "," ) );

        // Create node
        Node_t* node = (Node_t*)malloc( sizeof( Node_t ) );
        memset( node, 0, sizeof( Node_t ) );

        // Set values
        node->pid = i;
        node->q = q;
        node->lt = l;

        // Insert
        insert_node( &head, node );
    }

    free( string );

    // Close file
    fclose( fd );
    return head;
}
