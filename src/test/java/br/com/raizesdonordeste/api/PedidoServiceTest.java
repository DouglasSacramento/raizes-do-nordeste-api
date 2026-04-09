package br.com.raizesdonordeste.api;

import br.com.raizesdonordeste.api.application.dto.ItemCarrinhoRequestDTO;
import br.com.raizesdonordeste.api.application.dto.PedidoRequestDTO;
import br.com.raizesdonordeste.api.application.service.PedidoService;
import br.com.raizesdonordeste.api.domain.cliente.Cliente;
import br.com.raizesdonordeste.api.domain.pagamento.enums.MetodoPagamento;
import br.com.raizesdonordeste.api.domain.pedido.Pedido;
import br.com.raizesdonordeste.api.domain.pedido.enums.CanalPedido;
import br.com.raizesdonordeste.api.domain.produto.Produto;
import br.com.raizesdonordeste.api.domain.unidade.Unidade;
import br.com.raizesdonordeste.api.infrastructure.repository.ClienteRepository;
import br.com.raizesdonordeste.api.infrastructure.repository.PedidoRepository;
import br.com.raizesdonordeste.api.infrastructure.repository.ProdutoRepository;
import br.com.raizesdonordeste.api.infrastructure.repository.UnidadeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private UnidadeRepository unidadeRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ProdutoRepository produtoRepository;
    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void deveCriarPedidoComSucesso() {
        Unidade unidadeFalsa = new Unidade();
        unidadeFalsa.setId(2L);

        Cliente clienteFalso = new Cliente();
        clienteFalso.setId(10L);

        Produto produtoFalso = new Produto();
        produtoFalso.setId(101L);
        produtoFalso.setPrecoAtual(new BigDecimal("30.00"));

        List<ItemCarrinhoRequestDTO> itens = List.of(
                new ItemCarrinhoRequestDTO(101L, 2)
        );
        PedidoRequestDTO dto = new PedidoRequestDTO(
                2L,
                10L,
                true,
                CanalPedido.APP,
                MetodoPagamento.DINHEIRO,
                itens
        );

        when(unidadeRepository.findById(2L)).thenReturn(Optional.of(unidadeFalsa));
        when(clienteRepository.findById(10L)).thenReturn(Optional.of(clienteFalso));
        when(produtoRepository.findById(101L)).thenReturn(Optional.of(produtoFalso));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocacao -> invocacao.getArguments()[0]);

        Pedido pedidoSalvo = pedidoService.criarPedido(dto);

        assertNotNull(pedidoSalvo);
        assertEquals(new BigDecimal("60.00"), pedidoSalvo.getValorTotal());
        assertFalse(pedidoSalvo.getItens().isEmpty());
    }

    @Test
    void seExigeTrocoEMetodoDiferenteDeDinheiroNaoCriaPedido() {
        List<ItemCarrinhoRequestDTO> itens = List.of(
                new ItemCarrinhoRequestDTO(101L, 2)
        );
        PedidoRequestDTO dto = new PedidoRequestDTO(
                2L,
                10L,
                true,
                CanalPedido.APP,
                MetodoPagamento.PIX,
                itens
        );

        assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.criarPedido(dto);
        });

        verify(pedidoRepository, never()).save(any());
    }
}