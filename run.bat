@echo off

Set JARNAME=PaintTester

title %JARNAME%

SET JAR=%JARNAME%.jar

ECHO Looking for JDK

SET KEY_NAME=HKLM\SOFTWARE\JavaSoft\Java Development Kit
FOR /F "tokens=3" %%A IN ('REG QUERY "%KEY_NAME%" /v CurrentVersion 2^>NUL') DO SET jdkv=%%A
SET jdk=

IF DEFINED jdkv (
	FOR /F "skip=2 tokens=3,4" %%A IN ('REG QUERY "%KEY_NAME%\%jdkv%" /v JavaHome 2^>NUL') DO SET jdk=%%A %%B
) ELSE (
	FOR /F "tokens=*" %%G IN ('DIR /B "%ProgramFiles%\Java\jdk*"') DO SET jdk=%%G
)

SET jdk=%jdk%\bin


echo Using %jdk%\java.exe
"%jdk%\java.exe" -jar -Xmx1024m %JAR%
PAUSE
GOTO :eof

:exit
EXIT
