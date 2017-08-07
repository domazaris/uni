#pragma once
#include <stddef.h>

typedef struct Node {
  size_t pid; // Process ID
  size_t q; // Quanta
  size_t priority; // Num lottery tickets held
  size_t* tickets;

  void* next; // Next node in list
} Node_t;


int insert_node( Node_t** head, Node_t* node );
Node_t* delete_node( Node_t** head, Node_t* node );
void traverse( Node_t* head );
Node_t* find( Node_t* head );
