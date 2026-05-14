@echo off
REM Rode um teste especifico: rodar_teste.bat testNomeDoMetodo
REM Rode todos os testes:     rodar_teste.bat
set PATH=%USERPROFILE%\AppData\Local\Programs\apache-maven-3.9.6\bin;%PATH%
set MAVEN_OPTS=-Djavax.net.ssl.trustStoreType=Windows-ROOT
if "%1"=="" (
    mvn test
) else (
    mvn test -Dtest=%1
)
