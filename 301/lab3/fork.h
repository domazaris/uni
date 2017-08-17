#pragma once
#include <pthread.h>
#include <errno.h>
#include <unistd.h>
#include <pthread.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define N 10 // Num philosophers
#define MAX_TIME 10 // 10ms

typedef struct Fork {
    pthread_mutex_t mutex;
} fork_t;

void lockFork( fork_t* fork )
{
    int err = pthread_mutex_lock(&fork->mutex);
    if( err < 0 )
    {
        fprintf( stderr, "Lock Error%s\n", strerror( errno ) );
        abort();
    }
}

void unlockFork( fork_t* fork )
{
    int err = pthread_mutex_unlock(&fork->mutex);
    if( err < 0 )
    {
        fprintf( stderr, "Unlock Error: %s\n", strerror( errno ) );
        abort();
    }
}

typedef struct arg
{
    fork_t* forks;
    size_t id;
} arg_t;
