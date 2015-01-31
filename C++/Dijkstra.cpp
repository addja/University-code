#include <iostream>
#include <vector>
#include <queue>
#include <limits>
using namespace std;

// Arc from the graph (weight,node)
typedef pair<double,int> arc;

const double infinity = numeric_limits<double>::infinity();

// Graph
typedef vector< vector<arc> > graph;

// Dijkstra algorithm a little bit modified
void dijks(graph& g, int x, int y) {
	vector<double> dist (g.size(),infinity);  //vector de distancies
	vector<int> len (g.size(),-1); // vector de passos
	vector<bool> vis (g.size(),false); // vector de visitats
	priority_queue<arc, vector<arc>, greater<arc> > pq; // cua de prioritat dels arcs segons el pes
	arc tmp;
	tmp.first = 0;
	tmp.second = x;
	dist[x] = 0;
	len[x] = 0;
	pq.push(tmp);
	while(!pq.empty()) {
		int i = pq.top().second;
		pq.pop();
		if (!vis[i]) {
			vis[i] = true;
			for (int j = 0; j < g[i].size(); ++j) {
				int v = g[i][j].second;
				int w = g[i][j].first;
				if (dist[v] > dist[i] + w || (dist[v] == dist[i] + w && len[v] > len[i] + 1)) {
					dist[v] = dist[i] + w;
					len[v] = len[i]+1;
					tmp.first = dist[v];
					tmp.second = v;
					pq.push(tmp);
				}
			}
		}
	}
	if (len[y] == -1 || y >= g.size() || x >= g.size()) cout << "no path from " << x << " to " << y << endl; 
	else cout << "cost " << dist[y] << ", " << len[y] << " step(s)" << endl;
}

int main() {
	int v, e;
	while (cin >> v >> e) {
		graph g(v,vector<arc>());
		for (int i = 0; i < e; ++i) {
			arc c;
			int j;
			cin >> j >> c.second >> c.first;
			g[j].push_back(c);
		}
		int x, y;
		cin >> x >> y;
		dijks(g,x,y);
	}
}