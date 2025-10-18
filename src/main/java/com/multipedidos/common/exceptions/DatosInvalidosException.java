package com.multipedidos.common.exceptions;

/**
 * Excepción personalizada para datos inválidos (400).
 */
public class DatosInvalidosException extends RuntimeException {
    
    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }
}

