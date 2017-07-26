#include <stddef.h>
#include "Parser.h"

int main( int argc, char* argv[] )
{
    traverse( parseFile( argv[1] ) );
    return 0;
}
