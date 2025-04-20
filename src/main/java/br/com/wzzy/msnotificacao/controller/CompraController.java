package br.com.wzzy.msnotificacao.controller;

import br.com.wzzy.msnotificacao.model.dto.CompraResponseDTO;
import br.com.wzzy.msnotificacao.service.CompraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/v1/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public Mono<ResponseEntity<CompraResponseDTO>> processarCompra(@RequestBody List<Integer> idsProdutos) {
        return compraService.processarCompra(idsProdutos)
                .map(ResponseEntity::ok);
    }

}