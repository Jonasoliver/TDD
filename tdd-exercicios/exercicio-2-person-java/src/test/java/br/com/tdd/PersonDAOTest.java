package br.com.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes do PersonDAO.isValidToInclude() escritos seguindo TDD.
 *
 * As regras testadas sao as 4 do enunciado:
 *  1. Nome composto por ao menos 2 partes e composto de letras
 *  2. Idade no intervalo [1, 200]
 *  3. Pelo menos um objeto Email associado
 *  4. Email name no formato "____@___.___" (cada parte com >= 1 caractere)
 */
class PersonDAOTest {

    private PersonDAO dao;

    @BeforeEach
    void setUp() {
        dao = new PersonDAO();
    }

    // ------------------------------------------------------------------
    // Helper: cria uma Person valida (caminho feliz) para depois mutar
    // ------------------------------------------------------------------
    private Person personValida() {
        Person p = new Person(1, "Joao Silva", 30);
        p.addEmail(new Email(1, "joao@email.com"));
        return p;
    }

    // ------------------------------------------------------------------
    // Caminho feliz
    // ------------------------------------------------------------------
    @Test
    @DisplayName("Person totalmente valida deve retornar lista vazia")
    void deveRetornarListaVaziaQuandoPersonEValida() {
        List<String> erros = dao.isValidToInclude(personValida());
        assertTrue(erros.isEmpty(), "Esperava lista de erros vazia, veio: " + erros);
    }

    @Test
    @DisplayName("Person com multiplos emails validos tambem e valida")
    void deveAceitarPersonComMultiplosEmailsValidos() {
        Person p = personValida();
        p.addEmail(new Email(2, "maria@dominio.org"));
        p.addEmail(new Email(3, "j@x.y"));
        assertTrue(dao.isValidToInclude(p).isEmpty());
    }

    // ------------------------------------------------------------------
    // 1) Validacao de nome
    // ------------------------------------------------------------------
    @Test
    @DisplayName("Nome com apenas 1 parte deve gerar erro")
    void deveAcusarErroQuandoNomeTemApenasUmaParte() {
        Person p = personValida();
        p.setName("Joao");

        List<String> erros = dao.isValidToInclude(p);

        assertEquals(1, erros.size());
        assertTrue(erros.get(0).contains("2 partes"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Joao Silva3", "1234 5678", "Joao Silva!", "Jo@o Silva"})
    @DisplayName("Nome com caracteres nao-letra deve gerar erro")
    void deveAcusarErroQuandoNomeTemCaracteresInvalidos(String nomeInvalido) {
        Person p = personValida();
        p.setName(nomeInvalido);

        List<String> erros = dao.isValidToInclude(p);

        assertEquals(1, erros.size());
        assertTrue(erros.get(0).toLowerCase().contains("letras"));
    }

    @Test
    @DisplayName("Nome null deve gerar erro")
    void deveAcusarErroQuandoNomeENull() {
        Person p = personValida();
        p.setName(null);

        List<String> erros = dao.isValidToInclude(p);

        assertFalse(erros.isEmpty());
        assertTrue(erros.stream().anyMatch(e -> e.toLowerCase().contains("nome")));
    }

    @Test
    @DisplayName("Nome vazio deve gerar erro")
    void deveAcusarErroQuandoNomeEVazio() {
        Person p = personValida();
        p.setName("   ");

        List<String> erros = dao.isValidToInclude(p);

        assertFalse(erros.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Joao Silva", "Maria das Dores", "Ana-Clara Souza", "D'Avila Costa"})
    @DisplayName("Nomes validos com 2+ partes nao devem gerar erro de nome")
    void deveAceitarNomesValidos(String nomeValido) {
        Person p = personValida();
        p.setName(nomeValido);
        assertTrue(dao.isValidToInclude(p).isEmpty());
    }

    // ------------------------------------------------------------------
    // 2) Validacao de idade
    // ------------------------------------------------------------------
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 201, 500, Integer.MIN_VALUE, Integer.MAX_VALUE})
    @DisplayName("Idade fora do intervalo [1, 200] deve gerar erro")
    void deveAcusarErroQuandoIdadeForaDoIntervalo(int idadeInvalida) {
        Person p = personValida();
        p.setAge(idadeInvalida);

        List<String> erros = dao.isValidToInclude(p);

        assertEquals(1, erros.size());
        assertTrue(erros.get(0).contains("[1, 200]"));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 30, 100, 200})
    @DisplayName("Idade dentro do intervalo [1, 200] deve ser aceita")
    void deveAceitarIdadesDentroDoIntervalo(int idadeValida) {
        Person p = personValida();
        p.setAge(idadeValida);
        assertTrue(dao.isValidToInclude(p).isEmpty());
    }

    // ------------------------------------------------------------------
    // 3) Validacao da existencia de pelo menos 1 Email
    // ------------------------------------------------------------------
    @Test
    @DisplayName("Person sem nenhum email deve gerar erro")
    void deveAcusarErroQuandoPersonNaoTemEmail() {
        Person p = personValida();
        p.setEmails(new ArrayList<>());

        List<String> erros = dao.isValidToInclude(p);

        assertEquals(1, erros.size());
        assertTrue(erros.get(0).contains("pelo menos um Email"));
    }

    @Test
    @DisplayName("Person com lista de emails null deve gerar erro")
    void deveAcusarErroQuandoListaDeEmailsENull() {
        Person p = personValida();
        p.setEmails(null);

        List<String> erros = dao.isValidToInclude(p);

        assertFalse(erros.isEmpty());
    }

    // ------------------------------------------------------------------
    // 4) Validacao do formato de cada email
    // ------------------------------------------------------------------
    @ParameterizedTest
    @ValueSource(strings = {
            "semarroba.com",      // sem @
            "@dominio.com",       // parte local vazia
            "joao@.com",          // dominio vazio
            "joao@dominio",       // sem ponto
            "joao@dominio.",      // tld vazio
            "jo ao@dominio.com",  // espaco
            "joao@@dominio.com",  // duplo @
            ""                    // string vazia
    })
    @DisplayName("Emails fora do formato ____@___.___ devem gerar erro")
    void deveAcusarErroQuandoEmailEInvalido(String emailInvalido) {
        Person p = new Person(1, "Joao Silva", 30);
        p.addEmail(new Email(1, emailInvalido));

        List<String> erros = dao.isValidToInclude(p);

        assertFalse(erros.isEmpty(), "Esperava erro para o email: " + emailInvalido);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "a@b.c",                  // minimo: 1 char em cada parte
            "joao@email.com",
            "maria@dominio.org",
            "x@y.z"
    })
    @DisplayName("Emails dentro do formato ____@___.___ devem ser aceitos")
    void deveAceitarEmailsValidos(String emailValido) {
        Person p = new Person(1, "Joao Silva", 30);
        p.addEmail(new Email(1, emailValido));
        assertTrue(dao.isValidToInclude(p).isEmpty(),
                "Esperava aceitar o email: " + emailValido);
    }

    // ------------------------------------------------------------------
    // Casos combinados
    // ------------------------------------------------------------------
    @Test
    @DisplayName("Person totalmente invalida deve acumular varios erros")
    void deveAcumularVariosErrosParaPersonTotalmenteInvalida() {
        Person p = new Person(1, "X", -5, Collections.emptyList());

        List<String> erros = dao.isValidToInclude(p);

        // nome (1 parte), idade fora do range, sem email -> 3 erros
        assertEquals(3, erros.size(), "Erros encontrados: " + erros);
    }

    @Test
    @DisplayName("Person null deve gerar erro especifico")
    void deveAcusarErroQuandoPersonENull() {
        List<String> erros = dao.isValidToInclude(null);
        assertEquals(1, erros.size());
        assertTrue(erros.get(0).contains("null"));
    }

    @Test
    @DisplayName("Lista com email null tambem deve ser sinalizada")
    void deveAcusarErroQuandoListaContemEmailNull() {
        Person p = new Person(1, "Joao Silva", 30);
        List<Email> emails = new ArrayList<>();
        emails.add(null);
        p.setEmails(emails);

        List<String> erros = dao.isValidToInclude(p);

        assertFalse(erros.isEmpty());
    }
}
