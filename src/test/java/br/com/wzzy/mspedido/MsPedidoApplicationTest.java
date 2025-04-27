package br.com.wzzy.mspedido;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MsPedidoApplicationTest {

    @Test
    void applicationStartsSuccessfully() {
        assertDoesNotThrow(() -> MsPedidoApplication.main(new String[]{}));
    }

    @Test
    void applicationFailsToStartWithInvalidArgs() {
        String[] invalidArgs = {"--invalid.property=value"};
        assertThrows(Exception.class, () -> MsPedidoApplication.main(invalidArgs));
    }
}
