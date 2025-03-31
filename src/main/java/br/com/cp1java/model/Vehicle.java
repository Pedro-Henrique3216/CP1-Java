package br.com.cp1java.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Vehicle {

    private UUID id;
    private String marca;
    private String modelo;
    private Integer ano;
    private Double potencia;
    private Double economia;
    private VehicleType tipo;
    private BigDecimal preco;

}
