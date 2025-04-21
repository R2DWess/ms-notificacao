package br.com.wzzy.mspedido.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${ms.catalogo.url}")
    private String catalogoUrl;

    @Value("${ms.comprovante.url}")
    private String comprovanteUrl;

    @Bean
    public WebClient catalogoWebClient() {
        return WebClient.builder().baseUrl(catalogoUrl).build();
    }

    @Bean
    public WebClient comprovanteWebClient() {
        return WebClient.builder().baseUrl(comprovanteUrl).build();
    }
}
