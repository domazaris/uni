#include <stddef.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <stdlib.h>
#include <time.h>
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

    // Seed rand
    srand( time( NULL ) );

    // Create a scheduler
    Scheduler_t scheduler;
    memset( &scheduler, 0, sizeof( Scheduler_t ) );

    // Create the scheduler priorities (20, 10, 5, 1)
    scheduler.total_exe = (size_t*)malloc( 4 * sizeof( size_t ) );
    scheduler.total_exe_time = (size_t*)malloc( 4 * sizeof( size_t ) );
    
    // Open file and read into scheduler
    parseFile( &scheduler, argv[ 1 ] );

    // Execute scheduler
    run( &scheduler );

    // Calc averages - checks for 0 denominators first
    float ave_rt = scheduler.total_exe[0] == 0 ? 0 : scheduler.total_exe_time[0] / scheduler.total_exe[0];
    float ave_sys = scheduler.total_exe[1] == 0 ? 0 :scheduler.total_exe_time[1] / scheduler.total_exe[1];
    float ave_use = scheduler.total_exe[2] == 0 ? 0 :scheduler.total_exe_time[2] / scheduler.total_exe[2];
    float ave_bg = scheduler.total_exe[3] == 0 ? 0 :scheduler.total_exe_time[3] / scheduler.total_exe[3];

    // Print averages
    fprintf(stderr, "Average execution times:\n" );
    fprintf(stderr, "  Realtime:   %0.1f\n", ave_rt );
    fprintf(stderr, "  System:     %0.1f\n", ave_sys );
    fprintf(stderr, "  User:       %0.1f\n", ave_use );
    fprintf(stderr, "  Background: %0.1f\n", ave_bg );

    // Clean up
    free( scheduler.total_exe );
    free( scheduler.total_exe_time );

    return 0;
}
