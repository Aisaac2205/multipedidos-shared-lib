package com.multipedidos.common.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para ValidadorCodigos.
 */
class ValidadorCodigosTest {

    @Test
    void testValidarEmailValido() {
        assertTrue(ValidadorCodigos.validarEmail("usuario@ejemplo.com"));
        assertTrue(ValidadorCodigos.validarEmail("test.user@domain.co.uk"));
    }

    @Test
    void testValidarEmailInvalido() {
        assertFalse(ValidadorCodigos.validarEmail("email-invalido"));
        assertFalse(ValidadorCodigos.validarEmail("@ejemplo.com"));
        assertFalse(ValidadorCodigos.validarEmail("usuario@"));
        assertFalse(ValidadorCodigos.validarEmail(null));
        assertFalse(ValidadorCodigos.validarEmail(""));
    }

    @Test
    void testValidarCodigoPedidoValido() {
        assertTrue(ValidadorCodigos.validarCodigoPedido("PED-000001"));
        assertTrue(ValidadorCodigos.validarCodigoPedido("PED-123456"));
    }

    @Test
    void testValidarCodigoPedidoInvalido() {
        assertFalse(ValidadorCodigos.validarCodigoPedido("PED-123"));
        assertFalse(ValidadorCodigos.validarCodigoPedido("PEDIDO-123456"));
        assertFalse(ValidadorCodigos.validarCodigoPedido(null));
    }

    @Test
    void testGenerarCodigoPedido() {
        assertEquals("PED-000001", ValidadorCodigos.generarCodigoPedido(1L));
        assertEquals("PED-012345", ValidadorCodigos.generarCodigoPedido(12345L));
    }

    @Test
    void testGenerarCodigoFactura() {
        assertEquals("FAC-000001", ValidadorCodigos.generarCodigoFactura(1L));
        assertEquals("FAC-098765", ValidadorCodigos.generarCodigoFactura(98765L));
    }

    @Test
    void testValidarNoVacio() {
        assertDoesNotThrow(() -> ValidadorCodigos.validarNoVacio("valor", "campo"));
        
        assertThrows(IllegalArgumentException.class, () -> 
            ValidadorCodigos.validarNoVacio(null, "campo"));
        
        assertThrows(IllegalArgumentException.class, () -> 
            ValidadorCodigos.validarNoVacio("", "campo"));
    }
}

