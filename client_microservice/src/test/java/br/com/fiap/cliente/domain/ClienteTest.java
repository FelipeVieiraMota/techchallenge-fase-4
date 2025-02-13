package br.com.fiap.cliente.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    void configurar() {
        cliente = new Cliente("Teste", "teste@teste.com", "(11)99999-9999", "Av. Paulista", LocalDate.now());
    }

    @Nested
    class TestesEmail {

        @Test
        void deveAceitarEmailValido() {
            assertEquals("teste@teste.com", cliente.getEmail());
        }

        @Test
        void deveLancarExcecaoParaEmailInvalido() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Cliente("Teste", "email-invalido", "(11)99999-9999", "Av. Paulista", LocalDate.now()));
            assertEquals("Email inválido", exception.getMessage());
        }

        @Test
        void deveLancarExcecaoParaEmailNulo() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Cliente("Teste", null, "(11)99999-9999", "Av. Paulista", LocalDate.now()));
            assertEquals("Não foi informado o email", exception.getMessage());
        }
    }

    @Nested
    class TestesTelefone {

        @Test
        void deveAceitarTelefoneValido() {
            assertEquals("(11)99999-9999", cliente.getTelefone());
        }

        @Test
        void deveLancarExcecaoParaTelefoneInvalido() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Cliente("Teste", "teste@teste.com", "(11)99999-999", "Av. Paulista", LocalDate.now()));
            assertEquals("Telefone inválido", exception.getMessage());
        }

        @Test
        void deveLancarExcecaoParaTelefoneNulo() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Cliente("Teste", "teste@teste.com", null, "Av. Paulista", LocalDate.now()));
            assertEquals("Não foi informado o telefone", exception.getMessage());
        }
    }
}