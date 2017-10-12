#include <mqueue.h>
#include <stdio.h>

int main( int argc, char** argv )
{
    if( mq_unlink( argv[1] ) == -1 )
    {
        printf("Unlink err.\n");
        return 1;
    }
    printf("Deleted %s successfully.\n", argv[1]);
    return 0;
}
