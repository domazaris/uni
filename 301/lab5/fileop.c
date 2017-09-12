#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <sys/stat.h> 
#include <fcntl.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>

void usage( char* prog )
{
    printf( "Help:\n\t%s [ file ] [ offset ] [ operation ] [ data ]\n", prog );
}

int main( int argc, char* argv[] )
{
    if( argc != 5 ) {
        usage( argv[0] );
        return 1;
    }

    int fd;
    int offset = 0;
    int length;
    enum operation{ READ, WRITE } op;
    char* data;

    // File
    fd = open( argv[ 1 ], O_RDWR );
    if( fd < 0 ) {
        printf("Error opening file: %s\n", strerror( errno ) );
        return errno;
    }

    // Offset
    offset = atoi( argv[2] );

    // Operation
    switch( argv[ 3 ][ 0 ] )
    {
        case 'r':
            op = READ;
            break;
        case 'w':
            op = WRITE;
            break;
        default:
            usage( argv[0] );
            close( fd );
            return 1;
    }

    // Seek to offset
    lseek( fd, offset, SEEK_SET );

    // Data
    if( op == READ )
    {
        // Get args
        length = atoi( argv[ 4 ] );
        data = malloc( length );

        // Read
        read( fd, data, length );

        // Print read
        printf( "%s\n", data );

        // Free mem
        free(data);
    }
    else if( op == WRITE )
    {
        // Get args
        length = strlen( argv[ 4 ] );
        data = argv[ 4 ];

        // Write
        write( fd, data, length );
    }

    close( fd );
    return 0;
}
