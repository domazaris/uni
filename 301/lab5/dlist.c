#include <stdio.h>
#include <sys/types.h>
#include <dirent.h>
#include <errno.h>
#include <string.h>
#include <sys/stat.h>
#include <unistd.h>

void usage( char* prog )
{
    printf("Help:\n\t%s <dir name>\n", prog);
}

int list_directory( char* dir_name )
{
    // Open directory
    DIR* dir = opendir( dir_name );

    // Read whole directory
    char full_path[ 512 ];
    struct stat f_stat[ 1 ];
    struct dirent* d_data = readdir( dir );
    while( d_data != NULL )
    {
        sprintf( full_path, "%s/%s", dir_name, d_data->d_name );
        // int err = lstat( full_path, f_stat ); // used for when we dont want to follw symlinks
        int err = stat( full_path, f_stat );
        if( err < 0 )
        {
            printf("Could not stat file: %s\n", strerror( errno ) );
        }
        else
        {
            printf("%s", d_data->d_name );
            printf(" %lu", f_stat->st_ino);
            printf(" %d", d_data->d_type);
            printf(" %u", f_stat->st_mode);
            printf(" %lu", f_stat->st_nlink);
            printf(" %u-%u", f_stat->st_uid, f_stat->st_gid);
            printf(" %lu", f_stat->st_size);
            printf(" %lu\n", f_stat->st_mtime);
        }
        d_data = readdir( dir );
    }

    // Close directory
    int err = closedir( dir );
    if( err )
    {
        printf("ERROR: Failed to close dir: %s\n", strerror( errno ) );
        return errno;
    }

    return 0;
}

int main( int argc, char* argv[] )
{
    if( argc != 2 )
    {
         usage( argv[ 0 ] );
         return 1;
    }

    // list directory files
    return list_directory( argv[ 1 ] );
}
