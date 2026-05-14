"""
Exercicio 1 - Triangulo

Classe que recebe os tres lados de um triangulo e classifica em:
- Equilatero  -> tres lados iguais
- Isosceles   -> exatamente dois lados iguais
- Escaleno    -> tres lados diferentes

Condicao de existencia: a soma de quaisquer dois lados deve ser
estritamente maior que o terceiro lado. Alem disso, todos os
lados precisam ser inteiros positivos (> 0).

Implementado seguindo o ciclo Red -> Green -> Refactor.
"""


class Triangulo:
    """Representa um triangulo definido pelos tres lados."""

    EQUILATERO = "Equilatero"
    ISOSCELES = "Isosceles"
    ESCALENO = "Escaleno"

    def __init__(self, lado_a: int, lado_b: int, lado_c: int) -> None:
        self._validar_lados(lado_a, lado_b, lado_c)
        self.lado_a = lado_a
        self.lado_b = lado_b
        self.lado_c = lado_c

    @staticmethod
    def _validar_lados(a: int, b: int, c: int) -> None:
        # Lados precisam ser inteiros positivos (cobre zero e negativos)
        for nome, valor in (("a", a), ("b", b), ("c", c)):
            if not isinstance(valor, int) or isinstance(valor, bool):
                raise ValueError(f"Lado {nome} deve ser inteiro: {valor!r}")
            if valor <= 0:
                raise ValueError(
                    f"Os lados devem ser inteiros positivos. Lado {nome} = {valor}"
                )

        # Desigualdade triangular estrita: soma de dois lados > terceiro
        if (a + b <= c) or (a + c <= b) or (b + c <= a):
            raise ValueError(
                f"Os lados ({a}, {b}, {c}) nao formam um triangulo: "
                "a soma de dois lados deve ser maior que o terceiro."
            )

    def classificar(self) -> str:
        """Retorna a classificacao do triangulo."""
        a, b, c = self.lado_a, self.lado_b, self.lado_c
        if a == b == c:
            return self.EQUILATERO
        if a == b or a == c or b == c:
            return self.ISOSCELES
        return self.ESCALENO


def classificar_triangulo(a: int, b: int, c: int) -> str:
    """Funcao utilitaria: instancia o triangulo e devolve sua classificacao."""
    return Triangulo(a, b, c).classificar()
