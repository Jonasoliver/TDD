# Exercício 1 — Triângulo (Python + pytest)

## Enunciado
> O programa lê três valores inteiros que representam os lados de um triângulo
> e informa se os lados formam um triângulo isósceles, escaleno ou equilátero.
>
> **Condição:** a soma de dois lados tem que ser maior que o terceiro lado.

## Estrutura
```
exercicio-1-triangulo-python/
├── src/
│   ├── __init__.py
│   └── triangulo.py        # Classe Triangulo
├── tests/
│   └── test_triangulo.py   # 11 cenários do enunciado
├── pytest.ini
├── requirements.txt
└── README.md
```

## Como executar

### 1) Instalar dependências
```bash
python -m venv .venv
# Windows
.venv\Scripts\activate
# Linux/Mac
source .venv/bin/activate

pip install -r requirements.txt
```

### 2) Rodar os testes
```bash
pytest
```

### 3) Rodar com cobertura
```bash
pytest --cov=src --cov-report=term-missing --cov-report=html
```

O relatório HTML é gerado em `htmlcov/index.html`.

## Casos de Teste implementados
| # | Cenário                                          | Teste                                    |
|---|--------------------------------------------------|------------------------------------------|
| 1 | Triângulo escaleno válido                        | `test_1_triangulo_escaleno_valido`       |
| 2 | Triângulo isósceles válido                       | `test_2_triangulo_isosceles_valido`      |
| 3 | Triângulo equilátero válido                      | `test_3_triangulo_equilatero_valido`     |
| 4 | 3 CTs para isósceles válido com permutações      | `test_4_isosceles_permutacoes` (×3)      |
| 5 | Um valor zero                                    | `test_5_um_valor_zero` (×3)              |
| 6 | Um valor negativo                                | `test_6_um_valor_negativo` (×3)          |
| 7 | Soma de 2 lados igual ao terceiro                | `test_7_soma_dois_lados_igual_ao_terceiro` |
| 8 | Item 7 — uma CT por permutação                   | `test_8_soma_igual_todas_permutacoes` (×6) |
| 9 | Soma de 2 lados menor que o terceiro             | `test_9_soma_dois_lados_menor_que_o_terceiro` |
|10 | Item 9 — uma CT por permutação                   | `test_10_soma_menor_todas_permutacoes` (×6) |
|11 | Três valores iguais a zero                       | `test_11_tres_valores_iguais_a_zero`     |

Para os cenários inválidos, a classe lança `ValueError` com mensagem descritiva.
