# Exercício 2 — Person/PersonDAO (Java + JUnit 5)

## Enunciado
Implementar o método **`isValidToInclude()`** usando TDD. O método retorna uma
lista de erros baseada no objeto `Person`, validando:

1. Nome composto por ao menos 2 partes, todas formadas apenas por letras
2. Idade no intervalo `[1, 200]`
3. `Person` deve ter pelo menos um objeto `Email` associado
4. `Email.name` no formato `"____@___.___"` (cada parte com ≥ 1 caractere)

## Diagrama de classes implementado
```
Person                       PersonDAO
- id: int                    + save(p: Person): void
- name: String               + isValidToInclude(p: Person): List<String>
- age: int
- emails: List<Email>  (1..*)

Email
- id: int
- name: String
```

## Estrutura
```
exercicio-2-person-java/
├── pom.xml
└── src/
    ├── main/java/br/com/tdd/
    │   ├── Email.java
    │   ├── Person.java
    │   └── PersonDAO.java
    └── test/java/br/com/tdd/
        └── PersonDAOTest.java
```

## Como executar

### Pré-requisitos
- JDK 17+
- Maven 3.8+

### Compilar e rodar os testes
```bash
mvn clean test
```

### Cobertura de código com JaCoCo
```bash
mvn clean test
```
O relatório HTML é gerado em `target/site/jacoco/index.html`.

Para ver o resumo no terminal:
```bash
mvn clean test jacoco:report
```

## Decisões de design (TDD)
- O método retorna `List<String>` em vez de lançar exceção, pois o enunciado pede explicitamente "lista de erros".
- Validações são acumuladas — uma `Person` totalmente inválida retorna múltiplos erros num único `isValidToInclude`.
- Regex `^[\p{L}][\p{L}'\-]*$` para cada parte do nome, suportando acentos, hífen e apóstrofo (ex: `Ana-Clara`, `D'Avila`).
- Regex `^[^@.\s]+@[^@.\s]+\.[^@.\s]+$` para email — interpretação **estrita** do formato literal `____@___.___`: exatamente 1 `@` e exatamente 1 `.`, com 3 partes (local, domínio, TLD), cada uma com pelo menos 1 caractere e sem `@`, `.` ou espaços. Emails reais com múltiplos pontos como `joao.silva@empresa.com.br` são considerados inválidos sob essa regra.
