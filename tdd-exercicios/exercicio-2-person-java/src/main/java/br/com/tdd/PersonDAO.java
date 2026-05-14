package br.com.tdd;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * DAO da entidade {@link Person}.
 *
 * Implementa {@link #isValidToInclude(Person)} seguindo TDD: o metodo
 * retorna a lista de erros encontrados na validacao do objeto. Se a
 * lista vier vazia, o objeto e valido.
 *
 * Regras de validacao:
 *  1. Nome deve ser composto por ao menos 2 partes (separadas por espacos)
 *     e cada parte deve conter apenas letras.
 *  2. Idade deve estar no intervalo [1, 200].
 *  3. Person deve ter pelo menos um Email associado.
 *  4. Cada Email deve ter o campo "name" no formato "____@___.___",
 *     ou seja, parte_local@dominio.tld onde cada parte tem ao menos
 *     um caractere.
 */
public class PersonDAO {

    /**
     * Aceita letras Unicode (com acentos), inclusive ' e -, em cada parte do nome.
     * Cada parte deve ter pelo menos uma letra.
     */
    private static final Pattern PARTE_NOME =
            Pattern.compile("^[\\p{L}][\\p{L}'\\-]*$");

    /**
     * Formato do e-mail: pelo menos 1 caractere antes do @,
     * pelo menos 1 caractere entre @ e ., e pelo menos 1 caractere depois do .
     * (sem espacos em nenhuma das partes).
     */
    private static final Pattern EMAIL =
            Pattern.compile("^[^@.\\s]+@[^@.\\s]+\\.[^@.\\s]+$");

    /**
     * Persiste a pessoa. Para o exercicio basta um stub: o objetivo
     * pedagogico esta no metodo isValidToInclude.
     */
    public void save(Person p) {
        // Em um cenario real, persistiria em banco de dados.
        // Aqui apenas garantimos que a chamada nao falha.
    }

    /**
     * Valida o objeto e retorna a lista de erros.
     *
     * @param p Pessoa a ser validada (pode ser null)
     * @return Lista de mensagens de erro; vazia se p e valido.
     */
    public List<String> isValidToInclude(Person p) {
        List<String> erros = new ArrayList<>();

        if (p == null) {
            erros.add("Person nao pode ser null");
            return erros;
        }

        validarNome(p.getName(), erros);
        validarIdade(p.getAge(), erros);
        validarEmails(p.getEmails(), erros);

        return erros;
    }

    // ------------------------------------------------------------------
    // Validacoes internas
    // ------------------------------------------------------------------

    private void validarNome(String nome, List<String> erros) {
        if (nome == null || nome.trim().isEmpty()) {
            erros.add("Nome e obrigatorio");
            return;
        }

        String[] partes = nome.trim().split("\\s+");
        if (partes.length < 2) {
            erros.add("Nome deve ser composto por ao menos 2 partes");
            return;
        }

        for (String parte : partes) {
            if (!PARTE_NOME.matcher(parte).matches()) {
                erros.add("Nome deve ser composto apenas por letras");
                return;
            }
        }
    }

    private void validarIdade(int idade, List<String> erros) {
        if (idade < 1 || idade > 200) {
            erros.add("Idade deve estar no intervalo [1, 200]");
        }
    }

    private void validarEmails(List<Email> emails, List<String> erros) {
        if (emails == null || emails.isEmpty()) {
            erros.add("Person deve ter pelo menos um Email associado");
            return;
        }

        for (Email email : emails) {
            if (email == null || email.getName() == null || email.getName().isEmpty()) {
                erros.add("Email invalido: nome do email vazio");
                continue;
            }
            if (!EMAIL.matcher(email.getName()).matches()) {
                erros.add("Email invalido: " + email.getName()
                        + " (esperado formato ____@___.___)");
            }
        }
    }
}
