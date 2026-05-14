# Exercício 3 — Calculadora de Salário (TypeScript + Jest)

## Enunciado
Implementar uma calculadora de salário líquido. Cada funcionário tem
nome, email, salário-base e cargo. As regras de desconto por cargo são:

| Cargo          | Salário-base ≥ limite | Salário-base < limite |
|----------------|-----------------------|-----------------------|
| DESENVOLVEDOR  | ≥ 3.000 → −20%        | < 3.000 → −10%        |
| DBA            | ≥ 2.000 → −25%        | < 2.000 → −15%        |
| TESTADOR       | ≥ 2.000 → −25%        | < 2.000 → −15%        |
| GERENTE        | ≥ 5.000 → −30%        | < 5.000 → −20%        |

## Estrutura
```
exercicio-3-salario-typescript/
├── package.json
├── tsconfig.json
├── jest.config.js
├── src/
│   ├── Cargo.ts                # enum dos cargos
│   ├── Funcionario.ts          # entidade
│   ├── CalculadoraSalario.ts   # logica de calculo
│   └── index.ts
└── tests/
    └── CalculadoraSalario.test.ts
```

## Como executar

### Pré-requisitos
- Node.js 18+
- npm 9+

### Instalar dependências
```bash
npm install
```

### Rodar os testes
```bash
npm test
```

### Cobertura de código
```bash
npm run test:coverage
```
Relatório HTML em `coverage/lcov-report/index.html`.

## Decisões de design (TDD)
- Cargos modelados como `enum` com valores em string para logs legíveis.
- Regras de cargo concentradas em um mapa `Record<Cargo, RegraCargo>` — adicionar um cargo novo é incluir uma linha no mapa, sem `if/else` espalhados.
- O método `calcular()` lança erro para `salarioBase < 0` (estado inconsistente que merece falhar alto).
- Resultado arredondado para 2 casas decimais para evitar ruído de ponto flutuante.
