package br.com.wzzy.mspedido.controller;

import br.com.wzzy.mspedido.model.dto.CompraResponseDTO;
import br.com.wzzy.mspedido.model.dto.CompraSolicitacaoDTO;
import br.com.wzzy.mspedido.service.CompraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public Mono<ResponseEntity<CompraResponseDTO>> processarCompra(@RequestBody CompraSolicitacaoDTO solicitacao) {
        return compraService.processarCompra(solicitacao)
                .map(ResponseEntity::ok);
    }

}