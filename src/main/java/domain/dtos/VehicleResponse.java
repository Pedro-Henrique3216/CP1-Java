package domain.dtos;

import domain.model.VehicleType;

import java.math.BigDecimal;

public record VehicleResponse(
        String marca,
        String modelo,
        Integer ano,
        Double potencia,
        Double economia,
        VehicleType tipo,
        BigDecimal preco
) {
}
