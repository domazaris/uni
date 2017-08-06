#pragma once
#include <stddef.h>

typedef struct Node {
  int pid; // Process ID
  int q; // Quanta
  int lt; // Num lottery tickets held
  size_t* tickets;

  void* next; // Next node in list
} Node_t;


int insert_node( Node_t** head, Node_t* node );
Node_t* delete_node( Node_t** head, Node_t* node );
void traverse( Node_t* head );
Node_t* find( Node_t* head );
