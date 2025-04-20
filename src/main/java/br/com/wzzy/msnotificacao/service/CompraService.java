package br.com.wzzy.msnotificacao.service;

import br.com.wzzy.msnotificacao.model.dto.CompraResponseDTO;
import br.com.wzzy.msnotificacao.model.dto.CompraSolicitacaoDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CompraService {
    Mono<CompraResponseDTO> processarCompra(CompraSolicitacaoDTO solicitacao);
}
