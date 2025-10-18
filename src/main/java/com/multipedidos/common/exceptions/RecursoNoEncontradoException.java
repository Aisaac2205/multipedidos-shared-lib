package com.multipedidos.common.exceptions;

/**
 * Excepción personalizada para recursos no encontrados (404).
 */
public class RecursoNoEncontradoException extends RuntimeException {
    
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
    
    public RecursoNoEncontradoException(String recurso, Long id) {
        super(String.format("%s con ID %d no encontrado", recurso, id));
    }
}

