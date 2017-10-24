#!/bin/bash

function main() {
    javac *.java
    rm ../bin/*.class
    mv *.class ../bin/
}
main;
