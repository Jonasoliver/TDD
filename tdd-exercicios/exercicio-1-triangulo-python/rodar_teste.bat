@echo off
REM Rode um teste especifico: rodar_teste.bat test_1_triangulo_escaleno_valido
REM Rode todos os testes:     rodar_teste.bat
if "%1"=="" (
    python -m pytest tests/ -v
) else (
    python -m pytest tests/test_triangulo.py::%1 -v
)
