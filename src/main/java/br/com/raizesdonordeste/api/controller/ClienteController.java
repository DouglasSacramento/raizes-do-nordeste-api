package br.com.raizesdonordeste.api.controller;

import br.com.raizesdonordeste.api.service.cliente.ClienteService;
import br.com.raizesdonordeste.api.service.cliente.dto.ClienteRequestDTO;
import br.com.raizesdonordeste.api.service.cliente.dto.ClienteResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/registrar")
    public ResponseEntity<ClienteResponseDTO> registrar(@Valid @RequestBody ClienteRequestDTO dados) {
        var clienteNovo = clienteService.cadastrar(dados);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/api/v1/clientes/{id}")
                .buildAndExpand(clienteNovo.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new ClienteResponseDTO(clienteNovo));
    }
}
