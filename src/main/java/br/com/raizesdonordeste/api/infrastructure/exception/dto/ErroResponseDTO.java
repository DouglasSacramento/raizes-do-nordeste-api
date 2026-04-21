package br.com.raizesdonordeste.api.infrastructure.exception.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponseDTO(
        String error,
        String message,
        List<?> details,
        LocalDateTime timestamp,
        String path
) {
}
