#include <stddef.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <stdlib.h>
#include "Scheduler.h"

void parseFile( Scheduler_t* s, const char* filename )
{
    // Open file
    FILE* fd = fopen( filename, "r" );
    if( fd == NULL )
    {
        fprintf( stderr, "Failed to open file: %s", strerror( errno ) );
        abort();
    }

    // Ignore first line
    char* string = (char*)malloc( 256 );
    fgets( string, 100, fd );

    // Read a line at a time
    while( fgets( string, 100, fd ) != NULL )
    {
        int pid = atoi( strtok( string, "," ) );
        int q = atoi( strtok( NULL, "," ) );
        int priority = atoi( strtok( NULL, "," ) );

        // Add to scheduler
        addTask( s, pid, q, priority );
    }

    free( string );

    // Close file
    fclose( fd );
}

int main( int argc, char* argv[] )
{
    if( argc != 2 )
    {
        fprintf( stderr, "Usage:\n\t./Task2 <filename>\n");
        return 1;
    }

    // Create a scheduler
    Scheduler_t scheduler;
    memset( &scheduler, 0, sizeof( Scheduler_t ) );
    
    // Open file and read into scheduler
    parseFile( &scheduler, argv[ 1 ] );

    // Execute scheduler
    run( &scheduler );
    return 0;
}
