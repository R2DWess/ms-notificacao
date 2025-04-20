package br.com.wzzy.msnotificacao.service;

import br.com.wzzy.msnotificacao.model.dto.CompraRequestDTO;
import br.com.wzzy.msnotificacao.model.dto.CompraResponseDTO;
import br.com.wzzy.msnotificacao.model.dto.CompraSolicitacaoDTO;
import br.com.wzzy.msnotificacao.model.dto.ProdutoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CompraServiceImpl implements CompraService {

    private final WebClient catalogoWebClient;

    public CompraServiceImpl(WebClient catalogoWebClient) {
        this.catalogoWebClient = catalogoWebClient;
    }

    @Autowired
    private WebClient comprovanteWebClient;

    @Override
    public Mono<CompraResponseDTO> processarCompra(CompraSolicitacaoDTO solicitacao) {
        return Flux.fromIterable(solicitacao.getIdsProdutos())
                .flatMap(this::buscarProdutoPorId)
                .collectList()
                .flatMap(produtos -> {
                    CompraRequestDTO comprovante = new CompraRequestDTO();
                    comprovante.setEmailCliente(solicitacao.getEmailCliente());
                    comprovante.setProdutoDTO(produtos);

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
