#include <iostream>
#include <vector>
using namespace std;

#define mod 100000007

int main() {

	// lenght of the balance beam
	// 2 <= m <= 10^3 and m even
	int m;

	// number of jumps
	// 0 <= n <= 10^4
	int n;

	cin >> m >> n;

	// vector of jump distancies
	// 1 <= jumps[i] <= 100
	vector<int> jump(n);
	for (int i = 0; i < n; i++) cin >> jump[i];

	// auxiliar memory
	vector< vector<long> > mem(n+1, vector<long>(m+1, -1));

	// initialize
	mem[0][m/2] = 1;

	// calc dynamicly
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < m+1; j++) {
			if (mem[i][j] != -1) {
				long aux = j-m/2;
				long back = aux - jump[i];
				long front = aux + jump[i];
				if (back >= -m/2) {
					if (mem[i+1][back+m/2] == -1) mem[i+1][back+m/2] = mem[i][j]%mod;
					else mem[i+1][back+m/2] += mem[i][j]%mod;
				}
				if (front <= m/2) {
					if (mem[i+1][front+m/2] == -1) mem[i+1][front+m/2] = mem[i][j]%mod;
					else mem[i+1][front+m/2] += mem[i][j]%mod;
				} 
			}
		}
	}

	// print results
	for (int i = 0; i < m+1; i++) {
		if (mem[n][i] != -1) cout << i-m/2 << " " << mem[n][i]%mod << endl; 
	}

}