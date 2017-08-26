#include <unistd.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>

#define N 10 // Num philosophers
#define MAX_TIME 10 // 10ms

enum state
{
    thinking = 0, // Thinking
    hungry = 1, // Trying to get forks
    eating = 2 // Eating
};

typedef enum state state_t;

state_t STATE[ N ];
typedef int semaphore;
semaphore s[ N ];

void down( int* i )
{
    // Block until lock can be acquired
    while( *i < 1 )
    {
        usleep(1);
    }
    *i -= 1;
}

void up( int* i )
{
    // Release lock
    *i += 1;
}

typedef struct arg
{
    size_t id;
} arg_t;

void think(int i)
{
    usleep( rand() % MAX_TIME );
}

void eat(int i)
{
    STATE[ i ] = eating;
    usleep( rand() % MAX_TIME );
}

#define LEFT  ( i + N - 1 ) % N
#define RIGHT ( i + 1 ) % N

void test(int i)
{
    if( STATE[i] == hungry && STATE[ LEFT ] != eating && STATE[ RIGHT ] != eating )
    {
        STATE[i] = eating;
        up(&s[i]);
    }
}

void take_forks(int i)
{
    STATE[ i ] = hungry;
    test( i );
    down( &s[ i ] );
}

void put_forks(int i)
{
    STATE[ i ] = thinking;
    test(LEFT);
    test(RIGHT);
}

void* run_philosopher( void* args )
{
    size_t id = ((arg_t*)args)->id;

    // Forever
    while( 1 )
    {
        think( id );
        take_forks( id );
        eat( id );
        put_forks( id);

        // Perform a context switch to not eat the entire CPU
        usleep(0);
    }

    return NULL;
}


int main()
{
    // seed rand
    srand( time(NULL) );

    pthread_t* threads = malloc( sizeof(pthread_t) * N );

    for( size_t i = 0; i < N; i++ )
    {
        arg_t* args = malloc( sizeof(arg_t) );
        args->id = i;
        if( pthread_create( (pthread_t*)&threads[i], NULL, run_philosopher, (void*)args ) < 0 )
        {
            fprintf( stderr, "Pthread Error: %s\n", strerror( errno ) );
            abort();
        }
    }

    // Watch the stats
    for( size_t i = 0; i < N; i++ )
    {
        fprintf(stderr, "ph%zu  ", i);
    }
    fprintf(stderr, "\n");

    while(1)
    {
        // Return carriage
        fprintf(stderr, "\r");
        char output_state = ' ';
        for( size_t i = 0; i < N; i++ )
        {
            switch(STATE[i])
            {
                case thinking:
                    output_state  = 'T';
                    break;
                case eating:
                    output_state = 'E';
                    break;
                case hungry:
                    output_state = 'H';
                    break;
                default:
                    output_state = ' ';
                    break;
            }

            fprintf(stderr, " %c   ", output_state);
        }
        usleep(1e4);
    }

    for( size_t i = 0; i < N; i++)
    {
        pthread_join( threads[i], NULL);
    }

    return 0;
}
