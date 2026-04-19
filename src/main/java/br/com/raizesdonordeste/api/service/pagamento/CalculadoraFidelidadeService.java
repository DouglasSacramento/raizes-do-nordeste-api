package br.com.raizesdonordeste.api.service.pagamento;

import br.com.raizesdonordeste.api.domain.cliente.Cliente;
import br.com.raizesdonordeste.api.domain.pedido.Pedido;
import br.com.raizesdonordeste.api.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CalculadoraFidelidadeService {

    private final ClienteRepository clienteRepository;

    public Cliente pontuarCliente(Pedido pedido) {
        Cliente cliente = clienteRepository.findById(pedido.getCliente().getId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não cadastrado no sistema."));

        if (!cliente.getAceiteLgpd()) {
            return cliente;
        }

        cliente.setPontos(cliente.getPontos() + pedido.getValorTotal().divideToIntegralValue(BigDecimal.valueOf(10)).intValue());

        return clienteRepository.save(cliente);
    }
}
