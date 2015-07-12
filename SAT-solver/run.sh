#!/bin/sh
 
# Small script to gather data from picosat and your own SAT solver
# Useful for LI prac 01
#
# Example usage:
#   ./li-results.sh > out.txt
 
# Your solver
BIN=./mySAT
 
info() {
	echo "$@" >&2
}
 
write_results() {
	local name="$1"
	local raw="$2"
 
	satisfiable=$(echo "$raw" | sed -n '/^s /s/s \([^ ]\+\).*/\1/p')
	totaltime=$(echo "$raw" | sed -n 's/^c \(.*\) total run time$/\1/p')
	numdecisions=$(echo "$raw" | sed -n 's/^c \(.*\) decisions$/\1/p')
	propspersec=$(echo "$raw" | sed -n 's/^c \(.*props.*\)$/\1/p')
 
	echo "## $name"
	echo "Satisfiability: $satisfiable"
	echo "Total time: $totaltime"
	echo "Total number of decisions: $numdecisions"
	echo "Propagations per second: $propspersec"
	echo 
}
 
 FILES=random3SAT/*.cnf
for f in $FILES; do
	echo "$f"
	echo ========================'\n'
 
	info "picosat -v < $f"
	write_results "Picosat" "$(picosat -v < $f)"
 
	info "$BIN < $f"
	write_results "Own SAT solver" "$($BIN < $f)"

	echo -+-+-+-+-+-+-+-+-+-+-++-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-'\n'
done