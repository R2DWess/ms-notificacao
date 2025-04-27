package br.com.wzzy.mspedido.controller;

import br.com.wzzy.mspedido.model.dto.CompraResponseDTO;
import br.com.wzzy.mspedido.model.dto.CompraSolicitacaoDTO;
import br.com.wzzy.mspedido.service.CompraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompraControllerTest {

    @Mock
    private CompraService compraService;

    @InjectMocks
    private CompraController compraController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessarCompraReturnsOkForValidRequest() {
        CompraSolicitacaoDTO solicitacao = new CompraSolicitacaoDTO();
        CompraResponseDTO responseDTO = new CompraResponseDTO();
        when(compraService.processarCompra(solicitacao)).thenReturn(Mono.just(responseDTO));

        Mono<ResponseEntity<CompraResponseDTO>> result = compraController.processarCompra(solicitacao);

        StepVerifier.create(result)
                .assertNext(response -> {
                    assertEquals(200, response.getStatusCodeValue());
                    assertEquals(responseDTO, response.getBody());
                })
                .verifyComplete();
    }

    @Test
    void testProcessarCompraDelegatesToService() {
        CompraSolicitacaoDTO solicitacao = new CompraSolicitacaoDTO();
        CompraResponseDTO responseDTO = new CompraResponseDTO();
        when(compraService.processarCompra(solicitacao)).thenReturn(Mono.just(responseDTO));

        compraController.processarCompra(solicitacao).block();

        verify(compraService, times(1)).processarCompra(solicitacao);
    }

    @Test
    void testProcessarCompraReturnsMonoWithExpectedResponse() {
        CompraSolicitacaoDTO solicitacao = new CompraSolicitacaoDTO();
        CompraResponseDTO expectedResponse = new CompraResponseDTO();
        when(compraService.processarCompra(solicitacao)).thenReturn(Mono.just(expectedResponse));

        Mono<ResponseEntity<CompraResponseDTO>> result = compraController.processarCompra(solicitacao);

        StepVerifier.create(result)
                .assertNext(response -> assertEquals(expectedResponse, response.getBody()))
                .verifyComplete();
    }

    @Test
    void testProcessarCompraHandlesNullRequestBody() {
        when(compraService.processarCompra(null)).thenReturn(Mono.error(new IllegalArgumentException("Request body is null")));

        Mono<ResponseEntity<CompraResponseDTO>> result = compraController.processarCompra(null);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException &&
                        throwable.getMessage().equals("Request body is null"))
                .verify();
    }

    @Test
    void testProcessarCompraHandlesServiceException() {
        CompraSolicitacaoDTO solicitacao = new CompraSolicitacaoDTO();
        when(compraService.processarCompra(solicitacao)).thenReturn(Mono.error(new RuntimeException("Service failure")));

        Mono<ResponseEntity<CompraResponseDTO>> result = compraController.processarCompra(solicitacao);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Service failure"))
                .verify();
    }

    @Test
    void testProcessarCompraHandlesInvalidRequestBody() {
        CompraSolicitacaoDTO invalidSolicitacao = new CompraSolicitacaoDTO(); // Assume missing required fields
        when(compraService.processarCompra(invalidSolicitacao)).thenReturn(Mono.error(new IllegalArgumentException("Invalid request body")));

        Mono<ResponseEntity<CompraResponseDTO>> result = compraController.processarCompra(invalidSolicitacao);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException &&
                        throwable.getMessage().equals("Invalid request body"))
                .verify();
    }
}