package br.com.cp1java.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
