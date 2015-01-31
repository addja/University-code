#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <stdlib.h>
#include "graph.h"
#include "debug.h"
#include "edmondsKarp.h"
using namespace std;

int main(int argc, char *argv[]) {

    // Little help for the user
    if (argc != 2) {
        cout << "usage: ./airScheduling inputArchiveName.txt" << endl;
        exit(0);
    }
    
    // Input
    vector<vector<int> > data;
    
    // Read input from inputArchiveName
    string line;
    ifstream myfile (argv[1]);
    if (myfile.is_open()) {
        while (getline(myfile,line)) {
            vector<int> tmp;
            string stmp = "";
            for (int i = 0; i < (int)line.length()+1; ++i) {
                if (line[i] == ' ' || i ==(int)line.length()) {
                    tmp.push_back(atoi(stmp.c_str()));
                    stmp = "";
                }
                else stmp.push_back(line[i]);
            }
            data.push_back(tmp);
        }
        myfile.close();
    }
    else {
    	cout << "Unable to open file" << endl;
    	exit(0);
    }

    // Save the initial indexof each flight
    for (int i = 0; i < (int)data.size(); ++i) data[i].push_back(i);

    // Sort the flights
    for (int i = 0; i < (int)data.size(); ++i){
        int j = i;
        
        while (j > 0 && data[j][2] < data[j-1][2]){
              vector<int> temp = data[j];
              data[j] = data[j-1];
              data[j-1] = temp;
              --j;
        }
    }
    
    // Generate the graph & test if it has a max-flow with the algorithm
    int k = 10;
    int low = 0;
    while (true) {
    	FlowGraph G = Graph(data, k);
    	FlowGraph Gm = Graph(data, k-1);
        bool b1 = EdmondsKarp(G);
        bool b2 = EdmondsKarp(Gm);
        if (b1 && !b2) {
            cout << argv[1] << " " << k << endl;
            
            vector<vector<int> > sch = getSchedules(G,data);
            for (int i = 0; i < (int)sch.size(); ++i) {
                for (int j = 0; j < (int)sch[i].size(); ++j) {
                    cout << sch[i][j] << " ";
                }
                if (i != (int)sch.size() - 1) cout << endl;
            }

            break;
        }
        if (b1) {
        	k -= (k-low)/2;
        }
        else {
        	low = k;
        	k *= 2;
        }
    }
}