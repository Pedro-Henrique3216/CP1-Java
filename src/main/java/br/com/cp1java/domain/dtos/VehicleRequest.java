package br.com.cp1java.domain.dtos;

import br.com.cp1java.domain.model.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record VehicleRequest(

        @NotBlank String marca,
        @NotBlank String modelo,
        @NotNull Integer ano,
        @NotNull Double potencia,
        @NotNull Double economia,
        @NotBlank VehicleType tipo,
        @NotNull BigDecimal preco
) {
}
