package br.com.cp1java.domain.dtos;

import br.com.cp1java.domain.model.VehicleType;

import java.math.BigDecimal;
import java.util.UUID;

public record VehicleResponse(
        UUID id,
        String marca,
        String modelo,
        Integer ano,
        Double potencia,
        String economia,
        VehicleType tipo,
        BigDecimal preco
) {
}
