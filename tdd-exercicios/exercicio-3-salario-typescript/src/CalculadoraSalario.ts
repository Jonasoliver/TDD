import { Cargo } from './Cargo';
import { Funcionario } from './Funcionario';

/**
 * Regras de calculo do salario liquido por cargo.
 *
 * - DESENVOLVEDOR: >= 3000 -> -20%, < 3000 -> -10%
 * - DBA          : >= 2000 -> -25%, < 2000 -> -15%
 * - TESTADOR     : >= 2000 -> -25%, < 2000 -> -15%
 * - GERENTE      : >= 5000 -> -30%, < 5000 -> -20%
 */
interface RegraCargo {
  limite: number;
  descontoAcimaOuIgual: number; // ex: 0.20 == 20%
  descontoAbaixo: number;
}

const REGRAS: Record<Cargo, RegraCargo> = {
  [Cargo.DESENVOLVEDOR]: { limite: 3000, descontoAcimaOuIgual: 0.20, descontoAbaixo: 0.10 },
  [Cargo.DBA]:           { limite: 2000, descontoAcimaOuIgual: 0.25, descontoAbaixo: 0.15 },
  [Cargo.TESTADOR]:      { limite: 2000, descontoAcimaOuIgual: 0.25, descontoAbaixo: 0.15 },
  [Cargo.GERENTE]:       { limite: 5000, descontoAcimaOuIgual: 0.30, descontoAbaixo: 0.20 },
};

export class CalculadoraSalario {
  /**
   * Calcula o salario liquido aplicando o desconto definido pelo cargo.
   *
   * @throws Error se o salario base for negativo
   * @throws Error se o cargo nao tiver regra definida
   */
  calcular(funcionario: Funcionario): number {
    if (funcionario.salarioBase < 0) {
      throw new Error('Salario base nao pode ser negativo');
    }

    const regra = REGRAS[funcionario.cargo];
    if (!regra) {
      throw new Error(`Cargo nao suportado: ${funcionario.cargo}`);
    }

    const desconto =
      funcionario.salarioBase >= regra.limite
        ? regra.descontoAcimaOuIgual
        : regra.descontoAbaixo;

    const liquido = funcionario.salarioBase * (1 - desconto);

    // Arredonda para 2 casas decimais (centavos) evitando ruido de float.
    return Math.round(liquido * 100) / 100;
  }
}
