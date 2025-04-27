package br.com.wzzy.mspedido.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class WebClientConfigTest {

    @Configuration
    @Import(WebClientConfig.class)
    static class TestConfig {
    }

    @Autowired
    ApplicationContext context;

    @Test
    void testCatalogoWebClientBaseUrl() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        TestPropertyValues.of(
                "ms.catalogo.url=http://catalogo-service",
                "ms.comprovante.url=http://comprovante-service"
        ).applyTo(ctx);
        ctx.register(WebClientConfig.class);
        ctx.refresh();

        WebClient catalogoWebClient = ctx.getBean("catalogoWebClient", WebClient.class);
        assertNotNull(catalogoWebClient);
        ctx.close();
    }

    @Test
    void testComprovanteWebClientBaseUrl() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        TestPropertyValues.of(
                "ms.catalogo.url=http://catalogo-service",
                "ms.comprovante.url=http://comprovante-service"
        ).applyTo(ctx);
        ctx.register(WebClientConfig.class);
        ctx.refresh();

        WebClient comprovanteWebClient = ctx.getBean("comprovanteWebClient", WebClient.class);
        assertNotNull(comprovanteWebClient);
        ctx.close();
    }

    @Test
    void testWebClientBeansAreAutowired() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        TestPropertyValues.of(
                "ms.catalogo.url=http://catalogo-service",
                "ms.comprovante.url=http://comprovante-service"
        ).applyTo(ctx);
        ctx.register(WebClientConfig.class);
        ctx.refresh();

        assertTrue(ctx.containsBean("catalogoWebClient"));
        assertTrue(ctx.containsBean("comprovanteWebClient"));
        ctx.close();
    }

    @Test
    void testInvalidUrlConfiguration() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        TestPropertyValues.of(
                "ms.catalogo.url=ht!tp://invalid-url",
                "ms.comprovante.url=not-a-url"
        ).applyTo(ctx);
        ctx.register(WebClientConfig.class);
        ctx.refresh();

        WebClient catalogoWebClient = ctx.getBean("catalogoWebClient", WebClient.class);
        WebClient comprovanteWebClient = ctx.getBean("comprovanteWebClient", WebClient.class);

        assertNotNull(catalogoWebClient);
        assertNotNull(comprovanteWebClient);
        ctx.close();
    }

}