cd ..
cd "Source Code"
cd "src"
javac -cp "../org.json.jar:./graphs/jcommon-1.0.23.jar:./graphs/jfreechart-1.0.19.jar:./" *.java
cd ..
cd ..
java -cp "Source Code/org.json.jar:Source Code/src/graphs/jcommon-1.0.23.jar:Source Code/src/graphs/jfreechart-1.0.19.jar:Source Code/src:Source Code/mysql-connector-java-8.0.17.jar" UserView