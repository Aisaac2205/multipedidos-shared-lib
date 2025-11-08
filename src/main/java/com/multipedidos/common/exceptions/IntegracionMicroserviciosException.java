package com.multipedidos.common.exceptions;

/**
 * Excepción base para errores durante la comunicación entre microservicios
 * a través de la librería común.
 */
public class IntegracionMicroserviciosException extends RuntimeException {

    public IntegracionMicroserviciosException(String message) {
        super(message);
    }

    public IntegracionMicroserviciosException(String message, Throwable cause) {
        super(message, cause);
    }
}
