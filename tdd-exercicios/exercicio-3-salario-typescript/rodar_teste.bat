@echo off
REM Rode um teste especifico: rodar_teste.bat "salario >= 3000 deve receber 20%"
REM Rode todos os testes:     rodar_teste.bat
if "%1"=="" (
    npx jest --no-coverage
) else (
    npx jest --no-coverage -t "%1"
)
