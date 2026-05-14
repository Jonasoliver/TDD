import { CalculadoraSalario } from '../src/CalculadoraSalario';
import { Funcionario } from '../src/Funcionario';
import { Cargo } from '../src/Cargo';

describe('CalculadoraSalario', () => {
  let calc: CalculadoraSalario;

  beforeEach(() => {
    calc = new CalculadoraSalario();
  });

  // Helper para criar Funcionario rapidamente
  const fn = (salario: number, cargo: Cargo) =>
    new Funcionario('Fulano', 'fulano@empresa.com', salario, cargo);

  // -------------------------------------------------------------------
  // DESENVOLVEDOR
  // -------------------------------------------------------------------
  describe('DESENVOLVEDOR', () => {
    it('salario >= 3000 deve receber 20% de desconto', () => {
      // 3000 - 20% = 2400
      expect(calc.calcular(fn(3000, Cargo.DESENVOLVEDOR))).toBe(2400);
      // 5000 - 20% = 4000
      expect(calc.calcular(fn(5000, Cargo.DESENVOLVEDOR))).toBe(4000);
    });

    it('salario < 3000 deve receber 10% de desconto', () => {
      // 2999.99 - 10% = 2699.99
      expect(calc.calcular(fn(2999.99, Cargo.DESENVOLVEDOR))).toBe(2699.99);
      // 1500 - 10% = 1350
      expect(calc.calcular(fn(1500, Cargo.DESENVOLVEDOR))).toBe(1350);
    });

    it('limite exato (3000) usa o desconto maior (20%)', () => {
      expect(calc.calcular(fn(3000, Cargo.DESENVOLVEDOR))).toBe(2400);
    });
  });

  // -------------------------------------------------------------------
  // DBA
  // -------------------------------------------------------------------
  describe('DBA', () => {
    it('salario >= 2000 deve receber 25% de desconto', () => {
      // 2000 - 25% = 1500
      expect(calc.calcular(fn(2000, Cargo.DBA))).toBe(1500);
      // 4000 - 25% = 3000
      expect(calc.calcular(fn(4000, Cargo.DBA))).toBe(3000);
    });

    it('salario < 2000 deve receber 15% de desconto', () => {
      // 1999.99 - 15% = 1699.99 (arredondado)
      expect(calc.calcular(fn(1999.99, Cargo.DBA))).toBe(1699.99);
      // 1000 - 15% = 850
      expect(calc.calcular(fn(1000, Cargo.DBA))).toBe(850);
    });
  });

  // -------------------------------------------------------------------
  // TESTADOR
  // -------------------------------------------------------------------
  describe('TESTADOR', () => {
    it('salario >= 2000 deve receber 25% de desconto', () => {
      // 2000 - 25% = 1500
      expect(calc.calcular(fn(2000, Cargo.TESTADOR))).toBe(1500);
      // 3500 - 25% = 2625
      expect(calc.calcular(fn(3500, Cargo.TESTADOR))).toBe(2625);
    });

    it('salario < 2000 deve receber 15% de desconto', () => {
      // 1500 - 15% = 1275
      expect(calc.calcular(fn(1500, Cargo.TESTADOR))).toBe(1275);
    });
  });

  // -------------------------------------------------------------------
  // GERENTE
  // -------------------------------------------------------------------
  describe('GERENTE', () => {
    it('salario >= 5000 deve receber 30% de desconto', () => {
      // 5000 - 30% = 3500
      expect(calc.calcular(fn(5000, Cargo.GERENTE))).toBe(3500);
      // 8000 - 30% = 5600
      expect(calc.calcular(fn(8000, Cargo.GERENTE))).toBe(5600);
    });

    it('salario < 5000 deve receber 20% de desconto', () => {
      // 4999.99 - 20% = 3999.99
      expect(calc.calcular(fn(4999.99, Cargo.GERENTE))).toBe(3999.99);
      // 3000 - 20% = 2400
      expect(calc.calcular(fn(3000, Cargo.GERENTE))).toBe(2400);
    });
  });

  // -------------------------------------------------------------------
  // Casos de borda
  // -------------------------------------------------------------------
  describe('Casos de borda', () => {
    it('salario zero retorna zero', () => {
      expect(calc.calcular(fn(0, Cargo.DESENVOLVEDOR))).toBe(0);
      expect(calc.calcular(fn(0, Cargo.GERENTE))).toBe(0);
    });

    it('salario negativo deve lancar erro', () => {
      expect(() => calc.calcular(fn(-100, Cargo.DBA))).toThrow(
        'Salario base nao pode ser negativo',
      );
    });

    it('cargo nao mapeado deve lancar erro', () => {
      const fakeFn = new Funcionario('X', 'x@x.x', 1000, 'OUTRO' as unknown as Cargo);
      expect(() => calc.calcular(fakeFn)).toThrow('Cargo nao suportado');
    });
  });

  // -------------------------------------------------------------------
  // Tabela parametrizada cobrindo todas as combinacoes
  // -------------------------------------------------------------------
  describe.each<{
    cargo: Cargo;
    salario: number;
    esperado: number;
    cenario: string;
  }>([
    { cargo: Cargo.DESENVOLVEDOR, salario: 3000, esperado: 2400, cenario: 'DEV >= 3000 -> -20%' },
    { cargo: Cargo.DESENVOLVEDOR, salario: 2000, esperado: 1800, cenario: 'DEV <  3000 -> -10%' },
    { cargo: Cargo.DBA,           salario: 2000, esperado: 1500, cenario: 'DBA >= 2000 -> -25%' },
    { cargo: Cargo.DBA,           salario: 1500, esperado: 1275, cenario: 'DBA <  2000 -> -15%' },
    { cargo: Cargo.TESTADOR,      salario: 2500, esperado: 1875, cenario: 'TST >= 2000 -> -25%' },
    { cargo: Cargo.TESTADOR,      salario: 1000, esperado:  850, cenario: 'TST <  2000 -> -15%' },
    { cargo: Cargo.GERENTE,       salario: 6000, esperado: 4200, cenario: 'GER >= 5000 -> -30%' },
    { cargo: Cargo.GERENTE,       salario: 4000, esperado: 3200, cenario: 'GER <  5000 -> -20%' },
  ])('Tabela: $cenario', ({ cargo, salario, esperado }) => {
    it(`salario ${salario} para ${cargo} = ${esperado}`, () => {
      expect(calc.calcular(fn(salario, cargo))).toBe(esperado);
    });
  });
});

// -------------------------------------------------------------------
// Testes da entidade Funcionario (para garantir cobertura)
// -------------------------------------------------------------------
describe('Funcionario', () => {
  it('preserva os atributos passados no construtor', () => {
    const f = new Funcionario('Maria', 'maria@x.com', 4500, Cargo.DBA);
    expect(f.nome).toBe('Maria');
    expect(f.email).toBe('maria@x.com');
    expect(f.salarioBase).toBe(4500);
    expect(f.cargo).toBe(Cargo.DBA);
  });
});
