#!/bin/bash

qmake
make
cd viewer/bin
./viewer
cd ../..
