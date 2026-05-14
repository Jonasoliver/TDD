import { Cargo } from './Cargo';

/**
 * Representa um funcionario.
 * - nome
 * - email
 * - salarioBase
 * - cargo
 */
export class Funcionario {
  constructor(
    public readonly nome: string,
    public readonly email: string,
    public readonly salarioBase: number,
    public readonly cargo: Cargo,
  ) {}
}
