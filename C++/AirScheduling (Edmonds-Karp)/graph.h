#ifndef GRAPH_H
#define GRAPH_H
#include <vector>
#include <iostream>
using namespace std;

// Structure that represents a flow graph node
struct Arc {
    int flow;
    int cap;
};

// Structure that represents the flow graph
typedef vector<vector<Arc> > FlowGraph;

// Constructor given the flights data and the number of pilots
FlowGraph Graph(vector<vector<int> > data, int k);

// Function that returns the schedules of the pilots
vector<vector<int> > getSchedules(FlowGraph G, vector<vector<int> > flights);

#endif // GRAPH_H