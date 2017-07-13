#include <stdio.h>
#include <sys/mman.h>
#include <errno.h>
#include <string.h>
#include <unistd.h>

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

int file_size( int fd )
{
    struct stat sb;
    if ( fstat( fd, &sb ) == -1 )
    {
        fprintf( stderr, "Failed to stat file: %s\n", strerror( errno ) );
        return -1;
    }
    return sb.st_size;
}

void reverse( char* array, size_t length )
{
    // Reverse array inplace
    size_t i = length - 1;
    size_t j = 0;
    while( i > j )
    {
        int tmp = array[ i ];
        array[ i ] = array[ j ];
        array[ j ] = tmp;
        i--;
        j++;
    }
}

int main( int argc, char* argv[] ) 
{
    // Get the filename to reverse
    if( argc != 2 )
    {
        fprintf( stderr, "Usage:\n\t%s filename\n", argv[0] );
        return 2;
    }
    char* filename = argv[1];

    // Open file
    int fd = open( filename, O_EXCL | O_RDWR );
    if( fd < 0 )
    {
        fprintf( stderr, "Failed to open file: %s\n", strerror( errno ) );
        return errno;
    }

    // Get file size 
    int length = file_size( fd ); 
    if( length < 0 )
    {
        return errno;
    }

    // Memmap
    void* data = mmap( NULL, length, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0 );

    // Reverse
    reverse( (char*)data, length );

    // Clean up
    munmap( data, length );
    close( fd );
    return 0;
}
