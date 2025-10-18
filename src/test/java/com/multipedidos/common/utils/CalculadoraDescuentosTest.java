package com.multipedidos.common.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

/**
 * Tests unitarios para CalculadoraDescuentos.
 */
class CalculadoraDescuentosTest {

    @Test
    void testCalcularTotalConIVA() {
        BigDecimal subtotal = new BigDecimal("100.00");
        BigDecimal total = CalculadoraDescuentos.calcularTotalConIVA(subtotal);
        
        // 100 + 15% IVA = 115.00
        assertEquals(new BigDecimal("115.00"), total);
    }

    @Test
    void testAplicarDescuentoBasico() {
        BigDecimal monto = new BigDecimal("500.00");
        BigDecimal resultado = CalculadoraDescuentos.aplicarDescuentoPorMonto(monto);
        
        // 500 - 5% = 475.00
        assertEquals(new BigDecimal("475.00"), resultado);
    }

    @Test
    void testAplicarDescuentoMedio() {
        BigDecimal monto = new BigDecimal("2000.00");
        BigDecimal resultado = CalculadoraDescuentos.aplicarDescuentoPorMonto(monto);
        
        // 2000 - 10% = 1800.00
        assertEquals(new BigDecimal("1800.00"), resultado);
    }

    @Test
    void testAplicarDescuentoPremium() {
        BigDecimal monto = new BigDecimal("6000.00");
        BigDecimal resultado = CalculadoraDescuentos.aplicarDescuentoPorMonto(monto);
        
        // 6000 - 15% = 5100.00
        assertEquals(new BigDecimal("5100.00"), resultado);
    }

    @Test
    void testCalcularTotalFinal() {
        BigDecimal subtotal = new BigDecimal("1000.00");
        BigDecimal total = CalculadoraDescuentos.calcularTotalFinal(subtotal);
        
        // 1000 - 10% = 900, 900 + 15% IVA = 1035.00
        assertEquals(new BigDecimal("1035.00"), total);
    }

    @Test
    void testAplicarDescuentoPersonalizado() {
        BigDecimal monto = new BigDecimal("200.00");
        BigDecimal resultado = CalculadoraDescuentos.aplicarDescuentoPersonalizado(monto, 20.0);
        
        // 200 - 20% = 160.00
        assertEquals(new BigDecimal("160.00"), resultado);
    }

    @Test
    void testMontoNegativoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            CalculadoraDescuentos.calcularTotalConIVA(new BigDecimal("-100"));
        });
    }

    @Test
    void testPorcentajeInvalidoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            CalculadoraDescuentos.aplicarDescuentoPersonalizado(new BigDecimal("100"), 150.0);
        });
    }
}

