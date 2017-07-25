#pragma once

typedef struct Node {
  int pid; // Process ID
  int q; // Quanta
  int lt; // Num lottery tickets held

  struct Node_t* next; // Next node in list
} Node_t;

typedef Node_t** LinkedList;

void insert_node( LinkedList* list, Node_t* node );
void delete_node( LinkedList* list, Node_t* node );
Node_t* traverse( LinkedList* list, int idx );
Node_t* find( LinkedList* list );
