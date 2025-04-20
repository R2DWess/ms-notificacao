package br.com.wzzy.msnotificacao.service;

import br.com.wzzy.msnotificacao.model.dto.CompraResponseDTO;
import br.com.wzzy.msnotificacao.model.dto.ProdutoDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CompraServiceImpl implements CompraService {

    private final WebClient catalogoWebClient;

    public CompraServiceImpl(WebClient catalogoWebClient) {
        this.catalogoWebClient = catalogoWebClient;
    }

    @Override
    public Mono<CompraResponseDTO> processarCompra(List<Integer> idsProdutos) {
        return Flux.fromIterable(idsProdutos)
                .flatMap(this::buscarProdutoPorId)
                .collectList()
                .map(produtos -> new CompraResponseDTO("Compra processada com sucesso!", produtos));
    }


    public Mono<ProdutoDTO> buscarProdutoPorId(int id) {
        return catalogoWebClient.get()
                .uri("/id/{id}", id)
                .retrieve()
                .bodyToMono(ProdutoDTO.class);
    }
}
