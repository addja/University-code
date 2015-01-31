#include <iostream>
#include <vector>
using namespace std;

const int mod = 100000007;
const int MAXN = 100000;

int main() {
	int n;
	vector<int> fib(MAXN + 1,0);
	fib[0] = 0;
	fib[1] = 1;

	for (int i = 2; i <= MAXN; ++i) {
		fib[i] = (fib[i-1]+fib[i-2])%mod;
	}

	cout << "enter the position on the fibonnaci sequence: ";
	while(cin >> n) {
		if (n > MAXN) cout << "The number is too big" << endl;
		else cout << fib[n] << endl;
		cout << "more numbers (0 -> yes) (1 -> no): ";
		cin >> n;
		if (n == 1) break;
		cout << "enter the position on the fibonnaci sequence: ";
	}
}