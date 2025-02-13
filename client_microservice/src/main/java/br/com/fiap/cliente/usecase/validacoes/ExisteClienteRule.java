package br.com.fiap.cliente.usecase.validacoes;

import br.com.fiap.cliente.exception.NotFoundException;
import br.com.fiap.cliente.gateway.ClienteGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ExisteClienteRule implements ValidaClienteRule {
    private final ClienteGateway clienteGateway;

    @Override
    public boolean validarCliente(Long id) {
        if (!clienteGateway.existsById(id)) {
            throw new NotFoundException("Não existe cliente com o id informado.");
        }
        return true;
    }
}
