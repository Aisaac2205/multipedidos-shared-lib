package com.multipedidos.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Logger;

/**
 * Clase de utilidad para cálculos relacionados con descuentos, IVA y totales.
 * Implementa la lógica de negocio común para ambos microservicios.
 */
public class CalculadoraDescuentos {

    private static final Logger log = Logger.getLogger(CalculadoraDescuentos.class.getName());

    private static final BigDecimal IVA = new BigDecimal("0.15"); // 15% IVA
    private static final BigDecimal DESCUENTO_BASICO = new BigDecimal("0.05"); // 5%
    private static final BigDecimal DESCUENTO_MEDIO = new BigDecimal("0.10"); // 10%
    private static final BigDecimal DESCUENTO_PREMIUM = new BigDecimal("0.15"); // 15%
    
    private static final BigDecimal UMBRAL_MEDIO = new BigDecimal("1000");
    private static final BigDecimal UMBRAL_PREMIUM = new BigDecimal("5000");

    /**
     * Calcula el total de un pedido aplicando IVA.
     * 
     * @param subtotal Subtotal antes de impuestos
     * @return Total con IVA incluido
     */
    public static BigDecimal calcularTotalConIVA(BigDecimal subtotal) {
        if (subtotal == null || subtotal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El subtotal no puede ser nulo o negativo");
        }
        
        BigDecimal iva = subtotal.multiply(IVA);
        BigDecimal total = subtotal.add(iva);
        
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Aplica un descuento automático basado en el monto del pedido.
     * - Menos de 1000: 5% de descuento
     * - Entre 1000 y 5000: 10% de descuento
     * - Más de 5000: 15% de descuento
     * 
     * @param monto Monto original
     * @return Monto con descuento aplicado
     */
    public static BigDecimal aplicarDescuentoPorMonto(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto no puede ser nulo o negativo");
        }

        BigDecimal descuento;
        
        if (monto.compareTo(UMBRAL_PREMIUM) >= 0) {
            descuento = DESCUENTO_PREMIUM;
            log.info("Aplicando descuento premium del 15%");
        } else if (monto.compareTo(UMBRAL_MEDIO) >= 0) {
            descuento = DESCUENTO_MEDIO;
            log.info("Aplicando descuento medio del 10%");
        } else {
            descuento = DESCUENTO_BASICO;
            log.info("Aplicando descuento básico del 5%");
        }

        BigDecimal montoDescuento = monto.multiply(descuento);
        BigDecimal total = monto.subtract(montoDescuento);
        
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula el total final aplicando descuentos e IVA.
     * 
     * @param subtotal Subtotal antes de descuentos e impuestos
     * @return Total final con descuentos e IVA aplicados
     */
    public static BigDecimal calcularTotalFinal(BigDecimal subtotal) {
        BigDecimal conDescuento = aplicarDescuentoPorMonto(subtotal);
        return calcularTotalConIVA(conDescuento);
    }

    /**
     * Aplica un descuento personalizado.
     * 
     * @param monto Monto original
     * @param porcentajeDescuento Porcentaje de descuento (0-100)
     * @return Monto con descuento aplicado
     */
    public static BigDecimal aplicarDescuentoPersonalizado(BigDecimal monto, double porcentajeDescuento) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto no puede ser nulo o negativo");
        }
        
        if (porcentajeDescuento < 0 || porcentajeDescuento > 100) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100");
        }

        BigDecimal descuento = new BigDecimal(porcentajeDescuento).divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
        BigDecimal montoDescuento = monto.multiply(descuento);
        BigDecimal total = monto.subtract(montoDescuento);
        
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula el porcentaje de IVA actual.
     * 
     * @return Porcentaje de IVA
     */
    public static BigDecimal obtenerPorcentajeIVA() {
        return IVA.multiply(new BigDecimal("100"));
    }
}

