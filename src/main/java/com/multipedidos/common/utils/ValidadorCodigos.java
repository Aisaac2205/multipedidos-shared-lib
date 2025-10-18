package com.multipedidos.common.utils;

import java.util.regex.Pattern;

/**
 * Clase de utilidad para validar códigos y formatos comunes.
 */
public class ValidadorCodigos {

    private static final Pattern PATTERN_EMAIL = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private static final Pattern PATTERN_CODIGO_PEDIDO = Pattern.compile(
        "^PED-[0-9]{6}$"
    );

    private static final Pattern PATTERN_CODIGO_FACTURA = Pattern.compile(
        "^FAC-[0-9]{6}$"
    );

    /**
     * Valida si un email tiene un formato válido.
     * 
     * @param email Email a validar
     * @return true si es válido, false en caso contrario
     */
    public static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return PATTERN_EMAIL.matcher(email.trim()).matches();
    }

    /**
     * Valida el formato de un código de pedido (PED-XXXXXX).
     * 
     * @param codigo Código a validar
     * @return true si es válido, false en caso contrario
     */
    public static boolean validarCodigoPedido(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return false;
        }
        return PATTERN_CODIGO_PEDIDO.matcher(codigo.trim()).matches();
    }

    /**
     * Valida el formato de un código de factura (FAC-XXXXXX).
     * 
     * @param codigo Código a validar
     * @return true si es válido, false en caso contrario
     */
    public static boolean validarCodigoFactura(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return false;
        }
        return PATTERN_CODIGO_FACTURA.matcher(codigo.trim()).matches();
    }

    /**
     * Genera un código de pedido basado en un ID numérico.
     * 
     * @param id ID del pedido
     * @return Código formateado (PED-XXXXXX)
     */
    public static String generarCodigoPedido(Long id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo");
        }
        return String.format("PED-%06d", id);
    }

    /**
     * Genera un código de factura basado en un ID numérico.
     * 
     * @param id ID de la factura
     * @return Código formateado (FAC-XXXXXX)
     */
    public static String generarCodigoFactura(Long id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo");
        }
        return String.format("FAC-%06d", id);
    }

    /**
     * Valida que una cadena no sea nula o vacía.
     * 
     * @param valor Valor a validar
     * @param nombreCampo Nombre del campo para el mensaje de error
     * @throws IllegalArgumentException si el valor es nulo o vacío
     */
    public static void validarNoVacio(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " no puede ser nulo o vacío");
        }
    }
}

