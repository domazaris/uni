#pragma once
#include "LinkedList.h"

typedef struct Scheduler
{
    size_t p_count;
    size_t lottery_count;
    size_t* total_exe;
    size_t* total_exe_time;
    Node_t** tasks;
} Scheduler_t;

void addTask( Scheduler_t* s, size_t pid, size_t q, size_t priority );
void run( Scheduler_t* s );
int isEmpty( Scheduler_t* s );
