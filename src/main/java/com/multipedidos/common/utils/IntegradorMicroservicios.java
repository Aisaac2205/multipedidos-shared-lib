package com.multipedidos.common.utils;

import com.multipedidos.common.exceptions.IntegracionMicroserviciosException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

/**
 * Utilidad ligera (sin Spring) para invocar endpoints REST de los microservicios
 * desde la librería común y así compartir la lógica de comunicación.
 */
public final class IntegradorMicroservicios {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(5);
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(DEFAULT_TIMEOUT)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    private IntegradorMicroservicios() {
        throw new IllegalStateException("No se puede instanciar la clase de utilería");
    }

    /**
     * Consulta el microservicio de Clientes/Pedidos para obtener la representación JSON
     * de un pedido específico. El método no depende de Spring y puede reutilizarse desde
     * los componentes A y B.
     *
     * @param baseUrl  URL base del microservicio (por ejemplo, http://localhost:8080)
     * @param pedidoId identificador del pedido a consultar
     * @return JSON crudo del pedido cuando la respuesta es 2xx; {@link Optional#empty()} si la respuesta es 404
     * @throws IntegracionMicroserviciosException cuando ocurre un error de red o la URL es inválida
     */
    public static Optional<String> obtenerPedidoJson(String baseUrl, Long pedidoId) {
        validarParametros(baseUrl, pedidoId);

        String endpoint = construirEndpoint(baseUrl, "/api/pedidos/" + pedidoId);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .timeout(DEFAULT_TIMEOUT)
                .GET()
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) {
                return Optional.empty();
            }

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return Optional.ofNullable(response.body());
            }

            throw new IntegracionMicroserviciosException(
                    String.format("Respuesta inesperada %d al consultar %s", response.statusCode(), endpoint)
            );

        } catch (IOException e) {
            throw new IntegracionMicroserviciosException(
                    "Error de E/S al invocar microservicio de pedidos: " + e.getMessage(), e
            );
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IntegracionMicroserviciosException(
                    "La petición al microservicio de pedidos fue interrumpida", e
            );
        } catch (IllegalArgumentException e) {
            throw new IntegracionMicroserviciosException(
                    "URI inválida al construir la petición: " + e.getMessage(), e
            );
        }
    }

    private static void validarParametros(String baseUrl, Long pedidoId) {
        Objects.requireNonNull(baseUrl, "La URL base no puede ser nula");
        Objects.requireNonNull(pedidoId, "El ID del pedido no puede ser nulo");

        if (baseUrl.isBlank()) {
            throw new IllegalArgumentException("La URL base no puede estar vacía");
        }

        if (pedidoId <= 0) {
            throw new IllegalArgumentException("El ID del pedido debe ser mayor a cero");
        }
    }

    private static String construirEndpoint(String baseUrl, String path) {
        if (baseUrl.endsWith("/")) {
            return baseUrl.substring(0, baseUrl.length() - 1) + path;
        }
        return baseUrl + path;
    }
}
