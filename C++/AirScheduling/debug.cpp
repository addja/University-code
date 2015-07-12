#include "debug.h"
using namespace std;

// Printing of a data structure
void print(vector<vector<int> > data) {
    for (int i=0; i < (int)data.size(); ++i) {
        for (int j=0; j < (int)data[i].size(); ++j) cout << data[i][j] << " ";
        cout << endl;
    }
}

// Printing of a FlowGraph structure
void printGraph(FlowGraph G) {
    for (int i=0; i < (int)G.size(); ++i) {
        cout << i << ":";
        for (int j=0; j < (int)G[i].size(); ++j) {
            if (G[i][j].cap == 0) continue;
            cout << j << "(cap:" << G[i][j].cap << ") ";
        }
        cout << endl;
    }
}
