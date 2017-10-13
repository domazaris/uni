#include <stdio.h>
#include <sys/inotify.h>
#include <unistd.h>
#include <string.h>
#include <mqueue.h>
#include <errno.h>
#include <stdlib.h>
#include <time.h>
#define BLEN 4096

int main( int argc, char** argv )
{
    if( argc != 3 )
    {
        printf("usage:\n\t./fwatcher <queue path> <file path>\n");
        return 1;
    }
    
    // Get file names
    char* mqueue_location = argv[ 1 ];
    char* input_file = argv[ 2 ];

    // Open Queue in read/write mode
    mqd_t mqd = mq_open( mqueue_location, O_RDWR );
    if( mqd == (mqd_t) -1 )
    {
        printf( "Queue cannot be opened: %d %s!\n", errno, strerror(errno));
        return 1;
    }

    // Create inotify
    int notify_id = 0;
    int watcher_id = 0;
    notify_id = inotify_init();
    watcher_id = inotify_add_watch( notify_id, input_file, IN_ALL_EVENTS );
    (void)watcher_id;

    // Vars
    char* ptr;
    char buffer[ BLEN ];
    struct inotify_event* event;
    ssize_t len;
    char string[ BLEN ];
    
    // Daemon
    while( 1 )
    {
        len = read( notify_id, buffer, BLEN );
        time_t now = time(0);
        if( len <= 0 )
        {
            printf("Error read {%ld}\n", len);
            return -1;
        }

        for( ptr = buffer; ptr < buffer + len; ptr += sizeof( struct inotify_event ) + event->len)
        { // IN_ACCESS
            event = (struct inotify_event *) ptr;
            if( event-> mask & IN_ACCESS )
            {
                sprintf(string, "%s IN_ACCESS : %s", input_file, ctime( &now ) );
                mq_send( mqd, string, strlen(string), 1 );
                memset( string, 0, BLEN );
            }            
            if( event-> mask & IN_ATTRIB )
            {
                sprintf(string, "%s IN_ATTRIB : %s", input_file, ctime( &now ) );
                mq_send( mqd, string, strlen(string), 1 );
                memset( string, 0, BLEN );
            }
            if( event-> mask & IN_OPEN )
            {
                sprintf(string, "%s IN_OPEN : %s", input_file, ctime( &now ) );
                mq_send( mqd, string, strlen(string), 1 );
                memset( string, 0, BLEN );
            }            
            if( event-> mask & IN_CLOSE_WRITE )
            {
                sprintf(string, "%s IN_CLOSE_WRITE : %s", input_file, ctime( &now ) );
                mq_send( mqd, string, strlen(string), 1 );
                memset( string, 0, BLEN );
            }
            if( event-> mask & IN_CLOSE_NOWRITE )
            {
                sprintf(string, "%s IN_CLOSE_NOWRITE : %s", input_file, ctime( &now ) );
                mq_send( mqd, string, strlen(string), 1 );
                memset( string, 0, BLEN );
            }            
            if( event-> mask & IN_MODIFY )
            {
                sprintf(string, "%s IN_MODIFY : %s", input_file, ctime( &now ) );
                mq_send( mqd, string, strlen(string), 1 );
                memset( string, 0, BLEN );
            }
        }
    }
    return 0;
}
