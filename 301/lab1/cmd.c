/**
 * Author:   Dominic Azaris
 * ID:       1297845
 * Username: djmha1
 */

#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <errno.h>
#include <stdlib.h>

volatile int running = 1;
void intHandler( int d ) 
{
    if( running )
    {
        fprintf( stderr, "\nExiting...\n" );
        running = 0;
    }
}

void prompt() 
{
    fprintf( stdout, "CMD $ " );
    fflush( stdout );
}

int get_cmd( char* cmd, char* params[] ) 
{
    // Read a line from stdin
    size_t max_len = 1024;
    char* input = (char*)malloc( max_len );
    memset( input, 0, max_len );

    size_t len;
    fflush(stdin);
    int err = getline( &input, &len, stdin);
    if( err <= 1 ) 
    {
        //printf("no line\n");
        free( input );
        return 1;
    }

    // Get the cmd by splitting at each space
    int i = 0;
    char* word;
    word = strtok( input, " \n" );

    // Copy first arg into cmd
    memcpy( cmd, word, strlen( word ) );

    // Iterate the rest of the string for any parameters
    while( word != NULL ) 
    {
        params[ i++ ] = word;
        word = strtok( NULL, " \n");
    }

    // Clean buffer
    free( input );
    return 0;
}

int main() 
{
    signal(SIGINT, intHandler);

    // Iterate forever
    char* cmd = malloc( 256 );
    while( running ) 
    {
        memset( cmd, 0, 256 );

        // Prompt user
        prompt();

        // Get the command input
        char* params[ 256 ] = { NULL };

        if( get_cmd( cmd, params ) == 1 ) 
        {
            // No cmd given
            continue;
        }
    
        // Create a child and execute the given command
        if( fork() != 0 ) 
        {
            // Wait for child to finish
            waitpid( -1, NULL, 0 );
        }
        else 
        {
            if( ! running )
            {
                break;
            }

            // Replace the child process with the given command
            execvp( cmd, params );

            // If we get here exec has failed
            fprintf( stderr, "Failed to execute: %s\n", cmd );
            fprintf( stderr, "ERROR: %s\n", strerror( errno ) );
        }
    }
    free( cmd );
    return 0;
}
