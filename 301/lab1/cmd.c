#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <errno.h>
#include <stdlib.h>

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

int main() {
    // Iterate forever
    while( 1 ) 
    {
        // Prompt user
        prompt();

        // Get the command input
        char* cmd = malloc( 256 );
        char* params[ 256 ] = {};

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
            free( cmd );
        }
        else 
        {
            // Replace the child process with the given command
            execvp( cmd, params );

            // If we get here exec has failed
            fprintf( stderr, "Failed to execute: %s\n", cmd );
            fprintf( stderr, "ERROR: %s\n", strerror( errno ) );
        }
    }
    return 0;
}
