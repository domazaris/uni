#include <time.h>
#include <stdio.h>
#include <sys/types.h>
#include <dirent.h>
#include <errno.h>
#include <string.h>
#include <sys/stat.h>
#include <unistd.h>
#include <stdlib.h>

void usage( char* prog )
{
    printf("Help:\n\t%s <dir name>\n", prog);
}

char* get_type( int type_id )
{
    switch( type_id )
    {
        case DT_BLK:
            return "BLK";
        case DT_CHR:
            return "CHR";
        case DT_DIR:
            return "DIR";
        case DT_FIFO:
            return "FIFO";
        case DT_LNK:
            return "LINK";
        case DT_REG:
            return "REG";
        case DT_SOCK:
            return "SOCK";
        case DT_UNKNOWN:
        default:
            return "UNKNOWN";
    }
    return "UNKNOWN";
}

char* get_mode( unsigned int mode )
{
    char* mode_string = malloc( 9 );
    mode_string[ 0 ] = mode & S_IRUSR ? 'r' : '-';
    mode_string[ 1 ] = mode & S_IWUSR ? 'w' : '-';
    mode_string[ 2 ] = mode & S_IXUSR ? 'x' : '-';
    mode_string[ 3 ] = mode & S_IRGRP ? 'r' : '-';
    mode_string[ 4 ] = mode & S_IWGRP ? 'w' : '-';
    mode_string[ 5 ] = mode & S_IXGRP ? 'x' : '-';
    mode_string[ 6 ] = mode & S_IROTH ? 'r' : '-';
    mode_string[ 7 ] = mode & S_IWOTH ? 'w' : '-';
    mode_string[ 8 ] = mode & S_IXOTH ? 'x' : '-';
    return mode_string;
}

// char* get_time( unsigned int time )
// {
//     char* time_string = malloc( 256 );
//     strftime( time_string, 255, "" );
//     return "";
// }

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
        // int err = lstat( full_path, f_stat ); // used for when we dont want to follow symlinks
        int err = stat( full_path, f_stat );
        if( err < 0 )
        {
            printf("Could not stat file: %s\n", strerror( errno ) );
        }
        else
        {
            printf("%s", d_data->d_name );
            printf(" %lu", f_stat->st_ino);
            printf(" %s", get_type( d_data->d_type ) );
            char* mode_string = get_mode( f_stat->st_mode );
            printf(" %s", mode_string );
            printf(" %lu", f_stat->st_nlink);
            printf(" %u-%u", f_stat->st_uid, f_stat->st_gid);
            printf(" %lu", f_stat->st_size);
            printf(" %s", ctime( &f_stat->st_mtime ) );
            free( mode_string );
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
