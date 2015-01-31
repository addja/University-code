#include "graph.h"
using namespace std;

FlowGraph Graph(vector<vector<int> > data, int k) {
    // graph[0] -> s'   graph[1] -> t'
    // graph[2] -> s    graph[3] -> t
    // graph[i+4] -> origin node
    //graph[i+5] -> destination node
    
    int size = 4+ 2*(int)data.size();
    Arc aux;
    aux.cap = 0;
    aux.flow = 0;
    FlowGraph graph(size, vector<Arc>(size, aux));
    
    // flight connections
    for (int i = 0; i < (int)data.size(); ++i) {
        
        int u = 2*i+4;
        int v = 2*i+5;

        // connect the flight
        graph[u][v].cap = 0;       // 0 in this version
        
        // connect origin with t'
        graph[u][1].cap = 1;
        
        // connect destination with other feasible origins
        int destiny = data[i][1];
        int arrival = data[i][3];
        for (int j = 0; j < (int)data.size(); ++j) {
            if (j == i) continue;
            int norig = data[j][0];
            int ndeparture = data[j][2];
            if (norig == destiny && arrival + 15 <= ndeparture) {
                int ndest = 2*j + 4;
                graph[v][ndest].cap = 1;
            }
        }

        // connect destination with t
        graph[v][3].cap = 1;
        
    }

    // s' connection with destinations
    for (int i = 0; i < (int)data.size(); ++i) {
        int dest = 2*i + 5;
        graph[0][dest].cap = 1;
    }

    // s' connection with s
    graph[0][2].cap = k;
    
    // t connection with t'
    graph[3][1].cap = k;
    
    // s connections with origins
    for (int i = 0; i < (int)data.size(); ++i) {
        int dest = 2*i + 4;
        graph[2][dest].cap = 1;
    }

    // connect s with t
    graph[2][3].cap = k;
    
    return graph;
}

vector<vector<int> > getSchedules(FlowGraph G, vector<vector<int> > flights) {
    vector<vector<int> > schedules;
    bool fin = false;

    while (!fin) {
        int u;
        for (int i = 0; i < (int)flights.size(); ++i) {
            if (!flights[i].empty()) {
                u = i*2+4;
                break;
            }
        }
        if (u == 3) fin = true;
        vector<int> sch;
        while (u != 3) {
            sch.push_back(flights[(u - 4)/2][4] + 1);
            flights[(u - 4)/2].clear();
            for (int i = 0; i < (int)G[u+1].size(); ++i) {
                if (G[u+1][i].flow == 1) {
                    u = i;
                    break;
                }
            }
        }
        schedules.push_back(sch);
    }

    return schedules;
}