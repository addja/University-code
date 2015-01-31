#ifndef DEBUG_H
#define DEBUG_H
#include <vector>
#include <iostream>
#include "graph.h"
using namespace std;

// Printing of a data structure
void print(vector<vector<int> > data);

// Printing of a FlowGraph structure
void printGraph(FlowGraph G);

#endif // DEBUG_H