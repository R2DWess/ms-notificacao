package br.com.wzzy.msnotificacao.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ComprovanteWebClient {

    @Value("${ms.comprovante.url}")
    private String comprovanteUrl;

    @Bean
    public WebClient comprovanteWebClient() {
        return WebClient.builder()
                .baseUrl(comprovanteUrl)
                .build();
    }
}
