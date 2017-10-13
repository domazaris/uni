#include <stdio.h>
#include <sys/inotify.h>
#include <unistd.h>
#include <string.h>
#include <mqueue.h>
#include <errno.h>
// #define BLEN 4096

int main( int argc, char** argv)
{
    if( argc != 2 )
    {
        printf("usage:\n\t./qread <queue path>\n");
        return 1;
    }
    
    // Get file names
    char* mqueue_location = argv[ 1 ];

    // Open Queue in read/write mode
    mqd_t mqd = mq_open( mqueue_location, O_RDONLY );
    if( mqd == (mqd_t) -1 )
    {
        printf( "Queue cannot be opened: %d %s!\n", errno, strerror(errno));
        return 1;
    }

    unsigned int p = 1;
    ssize_t r = 0;
    struct mq_attr attr;
    r = mq_getattr( mqd, &attr );
    printf("%lu message(s) found\n", attr.mq_curmsgs);

    
    while( 1 )
    {
        r = mq_getattr( mqd, &attr );
        if( r < 0 )
        {
            printf("error\n");
            return 1;
        }
        
        if( attr.mq_curmsgs <= 0 )
        {
            break;
        }
        
        char string[ attr.mq_msgsize ];
        r = mq_receive( mqd, string, attr.mq_msgsize, &p);
        if( r < 0 )
        {
            printf("error\n");
            return 1;
        }
        printf("%s", string );
        memset( string, 0, attr.mq_msgsize);
    }
    return 0;
}