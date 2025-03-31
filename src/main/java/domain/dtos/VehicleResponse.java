package domain.dtos;

import domain.model.VehicleType;

import java.math.BigDecimal;
import java.util.UUID;

public record VehicleResponse(
        UUID id,
        String marca,
        String modelo,
        Integer ano,
        Double potencia,
        Double economia,
        VehicleType tipo,
        BigDecimal preco
) {
}
