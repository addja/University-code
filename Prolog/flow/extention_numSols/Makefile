file = flow_v2

$(file): $(file).pl
	swipl -O -g main --stand_alone=true -o $(file) -c $(file).pl

run:
	./$(file)

clean:
	rm $(file) model infile.cnf header graph.ps clauses
