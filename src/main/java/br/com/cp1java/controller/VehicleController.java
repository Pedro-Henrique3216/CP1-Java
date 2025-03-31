package br.com.cp1java.controller;

import br.com.cp1java.domain.model.VehicleType;
import br.com.cp1java.services.VehicleService;
import br.com.cp1java.domain.dtos.VehicleRequest;
import br.com.cp1java.domain.dtos.VehicleResponse;
import br.com.cp1java.domain.model.Vehicle;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable UUID id){
        Vehicle vehicle = vehicleService.getById(id);
        if(vehicle != null){
            return ResponseEntity.ok(transformToResponse(vehicle));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> updateVehicle(@PathVariable UUID id, @RequestBody @Valid VehicleRequest data){
        Vehicle vehicle = transformToVehicle(data);
        Vehicle vehicleUpdated = vehicleService.update(id, vehicle);
        if(vehicleUpdated != null){
            return ResponseEntity.ok(transformToResponse(vehicleUpdated));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VehicleResponse> deleteVehicle(@PathVariable UUID id){
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }


    private Vehicle transformToVehicle(VehicleRequest data){
        return new Vehicle(data.marca(), data.modelo(), data.ano(), data.potencia(), data.economia(),
                VehicleType.valueOf(data.tipo().toUpperCase()), data.preco());
    }

    private VehicleResponse transformToResponse(Vehicle vehicle){
        return new VehicleResponse(vehicle.getId(), vehicle.getMarca(), vehicle.getModelo(), vehicle.getAno(), vehicle.getPotencia(), vehicle.getEconomia(), vehicle.getTipo(),
                vehicle.getPreco());
    }
}
