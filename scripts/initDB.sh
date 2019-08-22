cd ..
javac -cp . Source\ Code/src/InitDB.java
mkdir -p Source\ Code/out/production
mv Source\ Code/src/*.class Source\ Code/out/production/
java -cp "Source Code/out/production:./Source Code/mysql-connector-java-8.0.17.jar" InitDB