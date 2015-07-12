#include <iostream>
#include <stdlib.h>
#include <algorithm>
#include <vector>
#include <ctime>
using namespace std;

#define UNDEF -1
#define TRUE 1
#define FALSE 0

uint numVars;
uint numClauses;
vector<vector<int> > clauses;
vector<vector<int> > reference;
vector<int> model;
vector<int> modelStack;
uint indexOfNextLitToPropagate;
uint decisionLevel;
clock_t t0, t1;
uint ndes, npro;
vector<int> vars;

void readClauses( ){
  // Skip comments
  char c = cin.get();
  while (c == 'c') {
    while (c != '\n') c = cin.get();
    c = cin.get();
  }  
  // Read "cnf numVars numClauses"
  string aux;
  cin >> aux >> numVars >> numClauses;
  clauses.resize(numClauses);
  reference.resize(2*numVars+2);
  vars.resize(numVars+1,0); 
  // Read clauses
  for (uint i = 0; i < numClauses; ++i) {
    int lit;
    while (cin >> lit and lit != 0) {
      clauses[i].push_back(lit);
      if (lit > 0) {
        reference[lit].push_back(i);
        ++vars[lit];
      }
      else {
        reference[-lit+numVars+1].push_back(i);
        ++vars[-lit];
      }
    }
  }    
}



int currentValueInModel(int lit){
  if (lit >= 0) return model[lit];
  else {
    if (model[-lit] == UNDEF) return UNDEF;
    else return 1 - model[-lit];
  }
}


void setLiteralToTrue(int lit){
  modelStack.push_back(lit);
  if (lit > 0) model[lit] = TRUE;
  else model[-lit] = FALSE;		
}


bool propagateGivesConflict ( ) {
  while ( indexOfNextLitToPropagate < modelStack.size() ) {
    int x = modelStack[indexOfNextLitToPropagate];
    if (x > 0) x += numVars+1;
    else x = -x;
    ++indexOfNextLitToPropagate;
    for (uint i = 0; i < reference[x].size(); ++i) {
      int y = reference[x][i];
      bool someLitTrue = false;
      int numUndefs = 0;
      int lastLitUndef = 0;
      for (uint k = 0; not someLitTrue and k < clauses[y].size(); ++k){
      	int val = currentValueInModel(clauses[y][k]);
      	if (val == TRUE) someLitTrue = true;
      	else if (val == UNDEF){ ++numUndefs; lastLitUndef = clauses[y][k]; }
      }
      if (not someLitTrue and numUndefs == 0) {
        for (uint k = 0; k < clauses[y].size(); ++k) {
          vars[abs(clauses[y][k])] += 3;
        }
        return true; // conflict! all lits false
      }
      else if (not someLitTrue and numUndefs == 1) {
        setLiteralToTrue(lastLitUndef);
        ++npro;
      }
    }    
  }
  return false;
}


void backtrack(){
  uint i = modelStack.size() -1;
  int lit = 0;
  while (modelStack[i] != 0){ // 0 is the DL mark
    lit = modelStack[i];
    model[abs(lit)] = UNDEF;
    modelStack.pop_back();
    --i;
  }
  // at this point, lit is the last decision
  modelStack.pop_back(); // remove the DL mark
  --decisionLevel;
  indexOfNextLitToPropagate = modelStack.size();
  setLiteralToTrue(-lit);  // reverse last decision
}


// Heuristic for finding the next decision literal:
int getNextDecisionLiteral(){
  ++ndes;
  int acc, pos;
  acc = -1;
  pos = 0;
  for (uint i = 1; i <= vars.size(); ++i) // order of aparition heuristic:
    if (acc < vars[i]) {
      if (model[i] == UNDEF) {
        acc = vars[i];
        pos = i;
      }
    }
  if (ndes % 920 == 0) for (uint i = 1; i < vars.size(); ++i) vars[i] /= 2;
  return pos; // reurns 0 when all literals are defined
}

void checkmodel(){
  for (int i = 0; i < numClauses; ++i){
    bool someTrue = false;
    for (int j = 0; not someTrue and j < clauses[i].size(); ++j)
      someTrue = (currentValueInModel(clauses[i][j]) == TRUE);
    if (not someTrue) {
      cout << "Error in model, clause is not satisfied:";
      for (int j = 0; j < clauses[i].size(); ++j) cout << clauses[i][j] << " ";
      cout << endl;
      exit(1);
    }
  }  
}

void printer() { // TODO: check for double casts
  t1 = clock();
  double t = (double)(t1-t0)/CLOCKS_PER_SEC;
  cout << "c " << t << " seconds total run time" << endl;
  cout << "c " << ndes << " decisions" << endl;
  cout << "c " << (double)npro/(t*1000000) << " megaprops/second" << endl;
}

int main(){
  t0 = clock();
  ndes = npro = 0;
  readClauses(); // reads numVars, numClauses and clauses
  model.resize(numVars+1,UNDEF);
  indexOfNextLitToPropagate = 0;  
  decisionLevel = 0;
  
  // Take care of initial unit clauses, if any
  for (uint i = 0; i < numClauses; ++i)
    if (clauses[i].size() == 1) {
      int lit = clauses[i][0];
      int val = currentValueInModel(lit);
      if (val == FALSE) {
        cout << "s UNSATISFIABLE" << endl;
        printer();
        return 0;
      }
      else if (val == UNDEF) setLiteralToTrue(lit);
    }
  
  // DPLL algorithm
  while (true) {
    while ( propagateGivesConflict() ) {
      if ( decisionLevel == 0) { 
        cout << "s UNSATISFIABLE" << endl;
        printer();  
        return 0; 
      }
      backtrack();
    }
    int decisionLit = getNextDecisionLiteral();
    if (decisionLit == 0) { 
      checkmodel(); 
      cout << "s SATISFIABLE" << endl;
      printer(); 
      return 0;
    }
    // start new decision level:
    modelStack.push_back(0);  // push mark indicating new DL
    ++indexOfNextLitToPropagate;
    ++decisionLevel;
    setLiteralToTrue(decisionLit);    // now push decisionLit on top of the mark
  }
}  
