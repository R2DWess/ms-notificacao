package br.com.wzzy.mspedido.service;

import br.com.wzzy.mspedido.model.dto.CompraRequestDTO;
import br.com.wzzy.mspedido.model.dto.CompraResponseDTO;
import br.com.wzzy.mspedido.model.dto.CompraSolicitacaoDTO;
import br.com.wzzy.mspedido.model.dto.ProdutoDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CompraServiceImpl implements CompraService {

    private final WebClient catalogoWebClient;
    private final WebClient comprovanteWebClient;

    public CompraServiceImpl(WebClient catalogoWebClient,
                             WebClient comprovanteWebClient) {
        this.catalogoWebClient = catalogoWebClient;
        this.comprovanteWebClient = comprovanteWebClient;
    }

    @Override
    public Mono<CompraResponseDTO> processarCompra(CompraSolicitacaoDTO solicitacao) {
        return Flux.fromIterable(solicitacao.getIdsProdutos())
                .flatMap(this::buscarProdutoPorId)
                .collectList()
                .flatMap(produtos -> {
                    CompraRequestDTO comprovante = new CompraRequestDTO();
                    comprovante.setEmailCliente(solicitacao.getEmailCliente());
                    comprovante.setProdutos(produtos);

                    return comprovanteWebClient.post()
                            .bodyValue(comprovante)
                            .retrieve()
                            .bodyToMono(String.class)
                            .map(msg -> new CompraResponseDTO("Compra finalizada com comprovante: " + msg, produtos));
                });
    }

    public Mono<ProdutoDTO> buscarProdutoPorId(int id) {
        return catalogoWebClient.get()
                .uri("/id/{id}", id)
                .retrieve()
                .bodyToMono(ProdutoDTO.class);
    }
}
