#include <unistd.h>
#include <pthread.h>
#include "fork.h"
#define N 5 // Num philosophers

void take_fork( fork_t* forks, size_t fork )
{
    // Wait until fork is ready
    lockFork( forks + fork );
}

void put_fork( fork_t* forks, size_t fork )
{
    // Unlock
    unlockFork( forks + fork );
}

void think()
{
    //fprintf(stderr,"t");
}

void eat()
{
    //fprintf(stderr,"e");
}

typedef struct arg
{
    fork_t* forks;
    pthread_barrier_t* barrier;
    size_t id;
} arg_t;

void* run_philosopher( void* args )
{
    fork_t* forks = ((arg_t*)args)->forks;
    size_t id = ((arg_t*)args)->id;
    pthread_barrier_t barrier = *args->barrier;
    size_t i = 0;
    while( 1 )
    {
        think();
        take_fork( forks, id );

        // Wait for threads to hit point
        pthread_barrier_wait(&mybarrier);
        
        take_fork( forks, (id + 1) % N );
        eat();
        put_fork(forks, id );
        put_fork( forks, (id + 1) % N );
        
        // Allows to not kill my cpu
        if( i++ % 10000 == 0)
            fprintf(stderr, ".");
        usleep(0);
    }
    fprintf(stderr, ".");

    return NULL;
}

int main()
{
    pthread_t threads = malloc( sizeof(pthread_t) * N );
    pthread_barrier_t barrier;
    pthread_barrier_init(&barrier, NULL, N);
    fork_t* forks = malloc( sizeof(fork_t) * N );

    for( size_t i = 0; i < N; i++ )
    {
        arg_t* args = malloc( sizeof(arg_t) );
        args->forks = forks;
        args->id = i;
        args->barrier = &barrier;
        if( pthread_create( (pthread_t*)&threads[i], NULL, run_philosopher, (void*)args ) < 0 )
        {
            fprintf( stderr, "Pthread Error: %s\n", strerror( errno ) );
            abort();
        }
    }

    // Wait here forever;
    while(1) sleep(1);

    for( size_t i = 0; i < N; i++)
    {
        pthread_join( &threads[i], NULL);
    }

    return 0;
}
