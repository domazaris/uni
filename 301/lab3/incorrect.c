#include "fork.h"

#define N 5 // Num philosophers

void take_fork( fork_t* forks, size_t fork )
{
    // Wait until fork is ready
    
    // acquire fork
}

void put_fork( fork_t* forks, size_t fork )
{
    // return fork
}

void think()
{
    fprintf(stderr,"t");
}

void eat()
{
    fprintf(stderr,"e");
}

void run_philosopher( fork_t* forks, size_t id )
{
    while( true )
    {
        think();
        take_fork( forks, id );
        take_fork( forks, (id + 1) % N )
        eat();
        put_fork(forks, id );
        put_fork( forks, (id + 1) % N )
    }
}

int main()
{
    return 0;
}