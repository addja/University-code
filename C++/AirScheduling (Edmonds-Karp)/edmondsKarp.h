#ifndef EDMONDSKARP_H
#define EDMONDSKARP_H
#include "graph.h"
#include <vector>
#include <queue>
#include <algorithm>
using namespace std;

// Edmonds-Karp algoritgm for a FlowGraph
bool EdmondsKarp(FlowGraph & G);

#endif // EDMONDSKARP_H