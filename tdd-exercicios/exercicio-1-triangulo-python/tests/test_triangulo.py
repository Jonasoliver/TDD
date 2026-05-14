"""
Casos de teste do Exercicio 1 - Triangulo.

Cobre os 11 cenarios pedidos no enunciado:

 1. Triangulo escaleno valido
 2. Triangulo isosceles valido
 3. Triangulo equilatero valido
 4. >= 3 CTs para isosceles valido (permutacoes dos mesmos valores)
 5. Um valor zero
 6. Um valor negativo
 7. Soma de 2 lados igual ao terceiro
 8. (7) com CT para cada permutacao dos valores
 9. Soma de 2 lados menor que o terceiro
10. (9) com CT para cada permutacao dos valores
11. Tres valores iguais a zero
"""

import pytest

from src.triangulo import Triangulo, classificar_triangulo


# ---------------------------------------------------------------------------
# 1. Triangulo escaleno valido
# ---------------------------------------------------------------------------
def test_1_triangulo_escaleno_valido():
    assert classificar_triangulo(3, 4, 5) == Triangulo.ESCALENO


# ---------------------------------------------------------------------------
# 2. Triangulo isosceles valido
# ---------------------------------------------------------------------------
def test_2_triangulo_isosceles_valido():
    assert classificar_triangulo(5, 5, 8) == Triangulo.ISOSCELES


# ---------------------------------------------------------------------------
# 3. Triangulo equilatero valido
# ---------------------------------------------------------------------------
def test_3_triangulo_equilatero_valido():
    assert classificar_triangulo(7, 7, 7) == Triangulo.EQUILATERO


# ---------------------------------------------------------------------------
# 4. Pelo menos 3 CTs para isosceles valido (permutacoes)
# ---------------------------------------------------------------------------
@pytest.mark.parametrize(
    "a, b, c",
    [
        (5, 5, 8),  # par no inicio
        (5, 8, 5),  # par nas extremidades
        (8, 5, 5),  # par no final
    ],
)
def test_4_isosceles_permutacoes(a, b, c):
    assert classificar_triangulo(a, b, c) == Triangulo.ISOSCELES


# ---------------------------------------------------------------------------
# 5. Um valor zero
# ---------------------------------------------------------------------------
@pytest.mark.parametrize(
    "a, b, c",
    [
        (0, 4, 5),
        (3, 0, 5),
        (3, 4, 0),
    ],
)
def test_5_um_valor_zero(a, b, c):
    with pytest.raises(ValueError, match="positivos"):
        classificar_triangulo(a, b, c)


# ---------------------------------------------------------------------------
# 6. Um valor negativo
# ---------------------------------------------------------------------------
@pytest.mark.parametrize(
    "a, b, c",
    [
        (-1, 4, 5),
        (3, -2, 5),
        (3, 4, -7),
    ],
)
def test_6_um_valor_negativo(a, b, c):
    with pytest.raises(ValueError, match="positivos"):
        classificar_triangulo(a, b, c)


# ---------------------------------------------------------------------------
# 7. Soma de 2 lados igual ao terceiro
# ---------------------------------------------------------------------------
def test_7_soma_dois_lados_igual_ao_terceiro():
    # 3 + 4 == 7  -> nao forma triangulo
    with pytest.raises(ValueError, match="nao formam um triangulo"):
        classificar_triangulo(3, 4, 7)


# ---------------------------------------------------------------------------
# 8. (7) com CT para cada permutacao dos valores {3, 4, 7}
# ---------------------------------------------------------------------------
@pytest.mark.parametrize(
    "a, b, c",
    [
        (3, 4, 7),
        (3, 7, 4),
        (4, 3, 7),
        (4, 7, 3),
        (7, 3, 4),
        (7, 4, 3),
    ],
)
def test_8_soma_igual_todas_permutacoes(a, b, c):
    with pytest.raises(ValueError, match="nao formam um triangulo"):
        classificar_triangulo(a, b, c)


# ---------------------------------------------------------------------------
# 9. Soma de 2 lados menor que o terceiro
# ---------------------------------------------------------------------------
def test_9_soma_dois_lados_menor_que_o_terceiro():
    # 1 + 2 < 10  -> nao forma triangulo
    with pytest.raises(ValueError, match="nao formam um triangulo"):
        classificar_triangulo(1, 2, 10)


# ---------------------------------------------------------------------------
# 10. (9) com CT para cada permutacao dos valores {1, 2, 10}
# ---------------------------------------------------------------------------
@pytest.mark.parametrize(
    "a, b, c",
    [
        (1, 2, 10),
        (1, 10, 2),
        (2, 1, 10),
        (2, 10, 1),
        (10, 1, 2),
        (10, 2, 1),
    ],
)
def test_10_soma_menor_todas_permutacoes(a, b, c):
    with pytest.raises(ValueError, match="nao formam um triangulo"):
        classificar_triangulo(a, b, c)


# ---------------------------------------------------------------------------
# 11. Tres valores iguais a zero
# ---------------------------------------------------------------------------
def test_11_tres_valores_iguais_a_zero():
    with pytest.raises(ValueError, match="positivos"):
        classificar_triangulo(0, 0, 0)


# ---------------------------------------------------------------------------
# Teste extra: instanciacao direta da classe (nao apenas via funcao)
# ---------------------------------------------------------------------------
def test_classe_triangulo_atributos_preservados():
    t = Triangulo(3, 4, 5)
    assert (t.lado_a, t.lado_b, t.lado_c) == (3, 4, 5)
    assert t.classificar() == Triangulo.ESCALENO
