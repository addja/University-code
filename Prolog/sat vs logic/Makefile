file = viajeSAT

$(file): $(file).pl
	swipl -O -g main --stand_alone=true -o $(file) -c $(file).pl

run:
	./$(file)

clean:
	rm $(file) clauses model infile.cnf header
