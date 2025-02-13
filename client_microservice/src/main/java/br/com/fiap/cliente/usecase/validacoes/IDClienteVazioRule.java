package br.com.fiap.cliente.usecase.validacoes;

import br.com.fiap.cliente.exception.IDNullException;
import br.com.fiap.cliente.gateway.ClienteGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class IDClienteVazioRule implements ValidaClienteRule {
    private final ClienteGateway clienteGateway;

    @Override
    public boolean validarCliente(Long id) {
        if (id == null) {
            throw new IDNullException("ID do cliente não pode ser vazio.");
        }
        return true;
    }
}
