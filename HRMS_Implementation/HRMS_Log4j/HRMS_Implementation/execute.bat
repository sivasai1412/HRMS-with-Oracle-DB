@echo off
cd src
set classpath=../lib/*;.;
javac -d . *.java
java bin.Login
pause