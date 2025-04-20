package br.com.wzzy.msnotificacao.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ConfiguracaoWebClient {

    @Value("${ms.catalogo.url}")

    private String catalgoUrl;

    @Bean
    public WebClient catalogoWebClient() {
        return WebClient.builder()
                .baseUrl(catalgoUrl)
                .build();
    }
}
