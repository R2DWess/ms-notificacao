package br.com.wzzy.mspedido.service;

import br.com.wzzy.mspedido.model.dto.CompraResponseDTO;
import br.com.wzzy.mspedido.model.dto.CompraSolicitacaoDTO;
import reactor.core.publisher.Mono;

public interface CompraService {
    Mono<CompraResponseDTO> processarCompra(CompraSolicitacaoDTO solicitacao);
}
