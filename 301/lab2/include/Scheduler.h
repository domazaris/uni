#pragma once
#include "LinkedList.h"

typedef struct Scheduler
{
    size_t p_count;
    size_t lottery_count;
    Node_t** tasks;
} Scheduler_t;

void addTask( Scheduler_t* s, int pid, int q, int priority );
void execute( Scheduler_t* s );
int isEmpty( Scheduler_t* s );
