/**
 * Enum dos cargos suportados pela calculadora de salario.
 *
 * Mantemos os valores em string para que ferramentas de log/serializacao
 * mostrem o nome do cargo, e nao um numero opaco.
 */
export enum Cargo {
  DESENVOLVEDOR = 'DESENVOLVEDOR',
  DBA = 'DBA',
  TESTADOR = 'TESTADOR',
  GERENTE = 'GERENTE',
}
