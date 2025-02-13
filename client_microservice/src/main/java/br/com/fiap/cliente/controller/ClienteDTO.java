package br.com.fiap.cliente.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDate;

@Schema(example = """
        {
          "nome": "Teste",
          "email": "teste@teste.com",
          "telefone": "(99)99999-9999",
          "endereco": "Av. Teste, 123",
          "dataCadastro": "2025-02-06"
        }
        """)
public record ClienteDTO(@ReadOnlyProperty Long id,
                         String nome,
                         String email,
                         String telefone,
                         String endereco,
                         LocalDate dataCadastro) {

}
