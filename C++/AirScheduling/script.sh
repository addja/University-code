#!/bin/bash

FILES=Benchmark/*.air

echo "output:" > output.txt
echo "working..."


size=$(ls -lR Benchmark/*.air | wc -l)
it=0
for f in $FILES
do
    echo "$it/$size"
    /usr/bin/time -f " time: %E" ./airScheduling $f >> output.txt
    it=$(($it+1))
done

echo "done!"
