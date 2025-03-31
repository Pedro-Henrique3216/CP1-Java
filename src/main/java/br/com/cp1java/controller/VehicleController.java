package br.com.cp1java.controller;

import br.com.cp1java.services.VehicleService;
import domain.dtos.VehicleRequest;
import domain.dtos.VehicleResponse;
import domain.model.Vehicle;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/carros")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<VehicleResponse> saveVehicle(@RequestBody @Valid VehicleRequest data, UriComponentsBuilder uriBuilder){
        Vehicle vehicle = transformToVehicle(data);
        Vehicle vehicleSaved = vehicleService.save(vehicle);
        URI uri = uriBuilder.path("/carros/{id}").buildAndExpand(vehicleSaved.getId()).toUri();
        return ResponseEntity.created(uri).body(transformToResponse(vehicleSaved));
    }


    private Vehicle transformToVehicle(VehicleRequest data){
        return new Vehicle(data.marca(), data.modelo(), data.ano(), data.potencia(), data.economia(),
                data.tipo(), data.preco());
    }

    private VehicleResponse transformToResponse(Vehicle vehicle){
        return new VehicleResponse(vehicle.getId(), vehicle.getMarca(), vehicle.getModelo(), vehicle.getAno(), vehicle.getPotencia(), vehicle.getEconomia(), vehicle.getTipo(),
                vehicle.getPreco());
    }
}
