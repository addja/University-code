file = flow

$(file): $(file).pl
	swipl -O -g main --stand_alone=true -o $(file) -c $(file).pl

run:
	./$(file)

display:
	evince graph.ps

clean:
	rm $(file) model infile.cnf header graph.ps clauses
