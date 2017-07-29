#include <stddef.h>
#include "Parser.h"

int main( int argc, char* argv[] )
{
    Node_t* head = parseFile( argv[1] );
    traverse( head );

    // Delete it all
    Node_t* cur = NULL;
    while( head != NULL )
    {
        cur = head;
        head = delete_node( &head, head );
        free(cur);
    }

    printf("Traversing what should be an empty list\n");
    traverse( head );
    printf("Done!\n");
    return 0;
}
