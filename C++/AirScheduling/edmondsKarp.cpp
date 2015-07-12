#include "edmondsKarp.h"
using namespace std;

// BFS mod to search the shortest path
bool BFS(const FlowGraph & Residual, vector<int> & path) {
    queue<int> Q;
    vector<int> parents(Residual.size(), -1);
    Q.push(0);
    parents[0] = -2;
    while (!Q.empty()) {
        int front = Q.front();
        Q.pop();
        for (int i = 0; i < (int)Residual[front].size(); ++i) {
            if (parents[i] == -1 && Residual[front][i].cap > 0) {
                parents[i] = front;
                if (i == 1) {
                    Q = queue<int>();
                    break;
                }
                else Q.push(i);
            }
        }
    }
    path = vector<int>();
    int v = 1;
    while (v != 0) {
        path.push_back(v);
        v = parents[v];
    }
    path.push_back(v);
    reverse(path.begin(), path.end());
    
    return parents[1] != -1;
}

// Search the min capacity of a given flow path
int searchMin(const FlowGraph & Residual, const vector<int> & path) {
    int min = 69696969;
    for (int i = 0; i < (int)path.size() - 1; ++i) {
        int tmp = Residual[path[i]][path[i+1]].cap - 
        Residual[path[i]][path[i+1]].flow;
        if (tmp < min) min = tmp;   
    }
    return min;
} 

// Updates a flow graph given a chosen path
void update(FlowGraph & G, FlowGraph & Residual, const vector<int> & path) {
    int minCap = searchMin(Residual,path);
    for (int i = 0; i < (int)path.size() - 1; ++i) {
        G[path[i]][path[i+1]].flow += minCap;
        G[path[i+1]][path[i]].flow -= minCap;
        Residual[path[i+1]][path[i]].cap += minCap;
        Residual[path[i]][path[i+1]].cap = Residual[path[i]][path[i+1]].cap - minCap;
    }
}

// Edmonds-Karp algoritgm for a FlowGraph
bool EdmondsKarp(FlowGraph & G) {
    // s -> G[0]     t -> G[1]

    FlowGraph Residual = G;

    vector<int> path;
    while (BFS(Residual, path)) {
        update(G, Residual, path);
    }
    
    int flow = 0;
    int cap = 0;
    for (int i = 0; i < (int)G[0].size(); ++i) {
        flow += G[0][i].flow;
        cap += G[0][i].cap;
    }

    return cap == flow; 
}