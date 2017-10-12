#include <stdio.h>
#include <sys/inotify.h>
#include <unistd.h>

#define BLEN 4096

int main( int argc, char** argv )
{
    char* s_file, * ptr;
    char buffer[ BLEN ];
    int notify_id, watcher_id;
    struct inotify_event* event;
    ssize_t len;

    notify_id = inotify_init();
    s_file = argv[ 1 ];
    watcher_id = inotify_add_watch( notify_id, s_file, IN_ALL_EVENTS );
    (void)watcher_id;

    while( 1 )
    {
        len = read( notify_id, buffer, BLEN );
        if( len <= 0 )
        {
            printf("Error read {%ld}\n", len);
            return -1;
        }

        for( ptr = buffer; ptr < buffer + len; ptr += sizeof( struct inotify_event ) + event->len)
        {
            event = (struct inotify_event *) ptr;
            if( event-> mask & IN_OPEN )
            {
                printf("%s IN_OPEN.\n", s_file );
            }            if( event-> mask & IN_OPEN )
            {
                printf("%s IN_OPEN.\n", s_file );
            }
            if( event-> mask & IN_ATTRIB )
            {
                printf("%s IN_ATTRIB.\n", s_file );
            }
        }
    }
    return 0;
}
