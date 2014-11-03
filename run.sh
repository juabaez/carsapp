#!/bin/bash

export CLASSPATH="target/classes"
for file in $(ls target/dependency); do 
    export CLASSPATH=$CLASSPATH:target/dependency/$file; 
done

echo "*******************  EXECUTING PROGRAM******************************************"
java -cp $CLASSPATH -Dactivejdbc.log com.unrc.app.App 
