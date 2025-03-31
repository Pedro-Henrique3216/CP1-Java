package domain.model;

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

    public Vehicle(String marca, String modelo, Integer ano, Double potencia, Double economia, VehicleType tipo, BigDecimal preco) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.potencia = potencia;
        this.economia = economia;
        this.tipo = tipo;
        this.preco = preco;
    }
}
