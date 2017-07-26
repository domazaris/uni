#pragma once

typedef struct Node {
  int pid; // Process ID
  int q; // Quanta
  int lt; // Num lottery tickets held

  void* next; // Next node in list
} Node_t;


int insert_node( Node_t** head, Node_t* node );
void delete_node( Node_t** head, Node_t* node );
void traverse( Node_t* head );
Node_t* find( Node_t* head );
