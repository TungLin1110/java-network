#include<iostream>
#include<cmath>
using namespace std;
int main() {
	int a, b;

	while (cin >> a >> b) {
		if ((a == 0) && (b == 0))
			break;
		int count = 0;
		int i = sqrt(a);
		if (i*i < a)
			i = i + 1;
		while (i*i <= b) {
			count++, i++;
		}
		cout << count << endl;
	}
}