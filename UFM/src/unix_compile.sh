#!/bin/bash
# -Xlint:unchecked
# -Xdiags:verbose
function main() {
    javac -Xdiags:verbose *.java
    rm ../bin/*.class
    mv *.class ../bin/
}
main;
