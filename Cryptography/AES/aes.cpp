/*
 * AES encryption algorithm
 */

#include <iostream>
using namespace std;

typedef unsigned char byte;

byte sumaBytes(byte a, byte b) {
	return a^b;
}

int main() {

	byte a = 0b01010111;
	byte b = 0b10000011;
	cout << b << endl;
	cout << sumaBytes(a,b) << endl;
}