#!/bin/bash

echo "*******************  BUILDING MODULE  *****************************************"
mvn clean install

echo "*******************  COLLECTING DEPENDENCIES  *********************************"
mvn dependency:copy-dependencies
export CLASSPATH="target/classes"
for file in $(ls target/dependency); do 
    export CLASSPATH=$CLASSPATH:target/dependency/$file; 
done

echo "*******************  EXECUTING PROGRAM  ****************************************"
java -cp $CLASSPATH -Dactivejdbc.log com.unrc.app.App 
