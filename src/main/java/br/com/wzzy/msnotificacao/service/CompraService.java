package br.com.wzzy.msnotificacao.service;

import br.com.wzzy.msnotificacao.model.dto.CompraResponseDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CompraService {
    Mono<CompraResponseDTO> processarCompra(List<Integer> idsProdutos);
}
