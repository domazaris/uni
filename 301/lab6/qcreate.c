#include <mqueue.h>
#include <stdio.h>
#include <fcntl.h>
#include <errno.h>
#include <string.h>

int main( int argc, char** argv )
{
    int flags;
    char* queue;
    mode_t perms;
    mqd_t mqd;
    struct mq_attr attr;
    queue = argv[ 1 ];
    flags = O_RDWR | O_CREAT;
    perms = S_IRUSR | S_IWUSR | S_IRGRP | S_IWGRP | S_IROTH | S_IWOTH;
    attr.mq_flags = 0; // blocking mode

    attr.mq_maxmsg = 10;
    attr.mq_msgsize = 8192;
    mqd = mq_open( queue, flags, perms, &attr );
    if( mqd == (mqd_t) -1 )
    {
        printf( "Queue cannot be created: %d %s!\n", errno, strerror(errno));
        return 1;
    }
    printf( "Message queue %s is ready.\n", argv[1] );
    return 0;
}
