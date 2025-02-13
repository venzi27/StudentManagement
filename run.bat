@echo off
<<<<<<< HEAD
cls
echo Starting Java Program...
java -cp ".;lib/mysql-connector-j-9.2.0.jar;src" JDBC.Main
pause
=======
echo Compiling Java files...
javac -cp ".;lib/mysql-connector-j-9.2.0.jar" -d bin src\JDBC\Main.java

echo Running Java program...
java -cp ".;lib/mysql-connector-j-9.2.0.jar;bin" JDBC.Main
>>>>>>> ff87f3d1dd00e20551213f4a16d98eff3a481884
