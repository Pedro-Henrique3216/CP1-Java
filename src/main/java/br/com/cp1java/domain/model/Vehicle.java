package br.com.cp1java.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "Vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String marca;
    @Column(nullable = false)
    private String modelo;
    @Column(nullable = false)
    private Integer ano;
    @Column(nullable = false)
    private Double potencia;
    @Column(nullable = false)
    private Double economia;
    @Column(nullable = false)
    private VehicleType tipo;
    @Column(nullable = false)
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

    public UUID getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public Double getPotencia() {
        return potencia;
    }

    public Double getEconomia() {
        return economia;
    }

    public VehicleType getTipo() {
        return tipo;
    }

    public BigDecimal getPreco() {
        return preco;
    }
}
