@echo off
cd src
set classpath=../lib/*;../lib/hibernatejar/*;.;
javac -d . *.java
java bin.Login
pause