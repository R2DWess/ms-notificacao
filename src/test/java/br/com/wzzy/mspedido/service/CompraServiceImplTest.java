package br.com.wzzy.mspedido.service;

import br.com.wzzy.mspedido.model.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CompraServiceImplTest {

    private WebClient catalogoWebClient;
    private WebClient comprovanteWebClient;
    private CompraServiceImpl compraService;

    @BeforeEach
    void setUp() {
        catalogoWebClient    = mock(WebClient.class);
        comprovanteWebClient = mock(WebClient.class);
        compraService        = new CompraServiceImpl(catalogoWebClient, comprovanteWebClient);
    }

    @Test
    void testProcessarCompraWithValidProductsAndEmail() {

        var getUriSpec      = mock(WebClient.RequestHeadersUriSpec.class);
        var getHeadersSpec  = mock(WebClient.RequestHeadersSpec.class);
        var produtoRespSpec1 = mock(WebClient.ResponseSpec.class);
        var produtoRespSpec2 = mock(WebClient.ResponseSpec.class);

        when(catalogoWebClient.get()).thenReturn(getUriSpec);
        when(getUriSpec.uri(eq("/id/{id}"), eq(1))).thenReturn(getHeadersSpec);
        when(getUriSpec.uri(eq("/id/{id}"), eq(2))).thenReturn(getHeadersSpec);
        when(getHeadersSpec.retrieve()).thenReturn(produtoRespSpec1, produtoRespSpec2);
        when(produtoRespSpec1.bodyToMono(ProdutoDTO.class)).thenReturn(Mono.just(new ProdutoDTO()));
        when(produtoRespSpec2.bodyToMono(ProdutoDTO.class)).thenReturn(Mono.just(new ProdutoDTO()));

        var postSpec        = mock(WebClient.RequestBodyUriSpec.class);
        var postHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        var compRespSpec    = mock(WebClient.ResponseSpec.class);

        when(comprovanteWebClient.post()).thenReturn(postSpec);
        when(postSpec.bodyValue(any(CompraRequestDTO.class))).thenReturn(postHeadersSpec);
        when(postHeadersSpec.retrieve()).thenReturn(compRespSpec);
        when(compRespSpec.bodyToMono(String.class)).thenReturn(Mono.just("12345"));

        var solicitacao = new CompraSolicitacaoDTO();
        solicitacao.setIdsProdutos(Arrays.asList(1, 2));
        solicitacao.setEmailCliente("cliente@teste.com");

        var resposta = compraService.processarCompra(solicitacao).block();

        assertNotNull(resposta);
        assertEquals("Compra finalizada com comprovante: 12345", resposta.getMensagem());
    }

    @Test
    void testBuscarProdutoPorIdWithValidId() {
        int id = 10;
        var produto = new ProdutoDTO();
        produto.setId(id);
        produto.setTitle("Produto Teste");

        var getUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        var getHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        var responseSpec = mock(WebClient.ResponseSpec.class);

        when(catalogoWebClient.get()).thenReturn(getUriSpec);
        when(getUriSpec.uri(eq("/id/{id}"), eq(id))).thenReturn(getHeadersSpec);
        when(getHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ProdutoDTO.class)).thenReturn(Mono.just(produto));

        ProdutoDTO result = compraService.buscarProdutoPorId(id).block();

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Produto Teste", result.getTitle());
    }


    @Test
    void testProcessarCompraWithEmptyProductList() {
        var solicitacao = new CompraSolicitacaoDTO();
        solicitacao.setIdsProdutos(Collections.emptyList());
        solicitacao.setEmailCliente("cliente@teste.com");

        var postSpec = mock(WebClient.RequestBodyUriSpec.class);
        var postHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        var responseSpec = mock(WebClient.ResponseSpec.class);

        when(comprovanteWebClient.post()).thenReturn(postSpec);
        when(postSpec.bodyValue(any(CompraRequestDTO.class))).thenReturn(postHeadersSpec);
        when(postHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("empty"));

        CompraResponseDTO response = compraService.processarCompra(solicitacao).block();

        assertNotNull(response);
        assertTrue(response.getProdutos().isEmpty());
        assertEquals("Compra finalizada com comprovante: empty", response.getMensagem());
    }


    @Test
    void testProcessarCompraWhenComprovanteWebClientFails() {
        var solicitacao = new CompraSolicitacaoDTO();
        solicitacao.setIdsProdutos(Collections.singletonList(1));
        solicitacao.setEmailCliente("cliente@teste.com");

        var produto = new ProdutoDTO();
        produto.setId(1);
        produto.setTitle("Produto 1");

        var getUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        var getHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        var produtoResponseSpec = mock(WebClient.ResponseSpec.class);

        when(catalogoWebClient.get()).thenReturn(getUriSpec);
        when(getUriSpec.uri(eq("/id/{id}"), eq(1))).thenReturn(getHeadersSpec);
        when(getHeadersSpec.retrieve()).thenReturn(produtoResponseSpec);
        when(produtoResponseSpec.bodyToMono(ProdutoDTO.class)).thenReturn(Mono.just(produto));

        var postSpec = mock(WebClient.RequestBodyUriSpec.class);
        var postHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        var responseSpec = mock(WebClient.ResponseSpec.class);

        when(comprovanteWebClient.post()).thenReturn(postSpec);
        when(postSpec.bodyValue(any(CompraRequestDTO.class))).thenReturn(postHeadersSpec);
        when(postHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class))
                .thenReturn(Mono.error(new RuntimeException("Comprovante service down")));

        assertThrows(RuntimeException.class, () -> compraService.processarCompra(solicitacao).block());
    }

    @Test
    void deveProcessarCompra() {
        var getUriSpec      = mock(WebClient.RequestHeadersUriSpec.class);
        var getHeadersSpec  = mock(WebClient.RequestHeadersSpec.class);
        var produtoRespSpec = mock(WebClient.ResponseSpec.class);

        when(catalogoWebClient.get()).thenReturn(getUriSpec);
        when(getUriSpec.uri(eq("/id/{id}"), eq(1))).thenReturn(getHeadersSpec);
        when(getHeadersSpec.retrieve()).thenReturn(produtoRespSpec);
        when(produtoRespSpec.bodyToMono(ProdutoDTO.class))
                .thenReturn(Mono.just(new ProdutoDTO()));

        var postSpec        = mock(WebClient.RequestBodyUriSpec.class);
        var postHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        var compRespSpec    = mock(WebClient.ResponseSpec.class);

        when(comprovanteWebClient.post()).thenReturn(postSpec);
        when(postSpec.bodyValue(any(CompraRequestDTO.class))).thenReturn(postHeadersSpec);
        when(postHeadersSpec.retrieve()).thenReturn(compRespSpec);
        when(compRespSpec.bodyToMono(String.class)).thenReturn(Mono.just("12345"));

        var solicitacao = new CompraSolicitacaoDTO();
        var resposta = compraService.processarCompra(solicitacao).block();

        assertEquals("Compra finalizada com comprovante: 12345", resposta.getMensagem());
    }
}
