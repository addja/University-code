#!/bin/bash

FILES=random3SAT/*.cnf

echo "working..."

if [ output.txt ];
then
   rm output.txt
fi

size=$(ls -lR random3SAT/*.cnf | wc -l)
it=0
for f in $FILES
do
    echo "$it/$size"
    echo $f >> output.txt
    echo "======== My SAT =========" >> output.txt
    { /usr/bin/time -f "Time: %E\n" ./mySAT < $f >> output.txt; } 2>> output.txt
    echo "-+-+-+-+-+-+-+-+-+-+-++-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-"$'\n' >> output.txt
    it=$(($it+1))
done

echo "done!"
