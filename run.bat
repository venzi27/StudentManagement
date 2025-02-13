@echo off
javac -cp "lib/mysql-connector-j-9.2.0.jar" -d bin src/JDBC/*.java
java -cp "bin;lib/mysql-connector-j-9.2.0.jar" JDBC.Main
pause
