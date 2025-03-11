MOALIC Romain : n°22202218
DOGUET Thomas : n°22205490

Commande de compilation :
javac -cp "lib/*" -d build src/**/*.java src/**/**/*.java

Commande d'éxecution :
java -cp build blockWorld.modelling.BWmodellingExecutable
java -cp build:lib/blocksworld.jar blockWorld.planning.BWplanningExecutable
java -cp build:lib/blocksworld.jar blockWorld.planning.BWRegularityConstraintsExecutable
java -cp build:lib/blocksworld.jar blockWorld.planning.BWIncreasingConstraintsExecutable
java -cp build:lib/blocksworld.jar blockWorld.planning.BWRegularityAndIncreasingConstraintsExecutable
java -cp build:lib/bwgenerator.jar blockWorld.datamining.BWDataminingExecutable
