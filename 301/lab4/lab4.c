#include  <stdlib.h>
#include <stdio.h>

#define NUM_PAGES 8
#define NUM_FRAMES 4
#define SEED 2017

typedef int bool;
enum {false, true};
struct pageEntry
{
    int frame;
    bool referenced;
    bool modified;
    bool present;
};

struct pageEntry pageTable[ NUM_PAGES ];
int count;
int nFaults;
int nMemRef;
int nDiskRef;
int page_frames[ NUM_FRAMES ];

void rnd( int page, int modified )
{
    nMemRef++;
    struct pageEntry* entry = &pageTable[ page ];

    int frame;
    entry->referenced = true;

    if( !entry->present )
    {
        nFaults++;
        if( count == NUM_FRAMES )
        {
            int pickFrame = NUM_FRAMES * (double)rand() / ( RAND_MAX + 1.0 );
            int evPage = page_frames[ pickFrame ];
            struct pageEntry* evEntry = &pageTable[ evPage ];
            evEntry->present = false;

            frame = pickFrame;
            if( evEntry->modified )
            {
                nDiskRef++;
            }
        }
        else
        {
            frame = count++;
        }

        nDiskRef++;

        entry->present = true;
        entry->modified = modified;
        entry->frame = frame;

        page_frames[ frame ] = page;
    }
    else
    {
        entry->modified = entry->modified || modified;
    }
}

void nru( int page, int modified )
{

}

void lru( int page, int modified )
{

}

void clk( int page, int modified )
{

}

void simulate( char* fRefPages, char* fReport )
{
    FILE* fpRefPages = fopen( fRefPages, "r" );
    FILE* fpReport = fopen( fReport, "w" );

    int page;
    int modified;

    // RND
    srand( SEED );
    while( fscanf( fpRefPages, "%d %d", &page, &modified ) != EOF )
    {
        rnd( page, modified );
    }
    rewind(fpRefPages);

    fprintf( fpReport, 
             "RND M %d F %d D %d L %d %d %d %d\n", 
             nMemRef, nFaults, nDiskRef, page_frames[0], page_frames[1], page_frames[2], page_frames[3] );

    // NRU
    while( fscanf( fpRefPages, "%d %d", &page, &modified ) != EOF )
    {
        nru( page, modified );
    }
    rewind(fpRefPages);
    fprintf( fpReport, 
        "NRU M %d F %d D %d L %d %d %d %d\n", 
        nMemRef, nFaults, nDiskRef, page_frames[0], page_frames[1], page_frames[2], page_frames[3] );

    // LRU
    while( fscanf( fpRefPages, "%d %d", &page, &modified ) != EOF )
    {
        lru( page, modified );
    }
    fprintf( fpReport, 
        "LRU M %d F %d D %d L %d %d %d %d\n", 
        nMemRef, nFaults, nDiskRef, page_frames[0], page_frames[1], page_frames[2], page_frames[3] );
    
    // CLK
    while( fscanf( fpRefPages, "%d %d", &page, &modified ) != EOF )
    {
        clk( page, modified );
    }
    fprintf( fpReport, 
        "CLK M %d F %d D %d L %d %d %d %d\n", 
        nMemRef, nFaults, nDiskRef, page_frames[0], page_frames[1], page_frames[2], page_frames[3] );

    fclose( fpRefPages );
    fclose( fpReport );
}

void usage()
{
    printf("./main <input> <output>\n");
}

int main( int argc, char* argv[])
{
    if( argc != 3 )
    {
        usage();
        return -1;
    }

    simulate( argv[1], argv[2] );
    return 0;
}
