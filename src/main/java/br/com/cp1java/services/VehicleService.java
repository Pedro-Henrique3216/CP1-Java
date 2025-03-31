package br.com.cp1java.services;

import br.com.cp1java.repositories.VehicleRepository;
import domain.model.Vehicle;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle getById(UUID id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    public Vehicle update(UUID id, Vehicle vehicle) {
        Vehicle vehicleToUpdate = getById(id);
        if (vehicleToUpdate != null) {
            return vehicleRepository.save(vehicleToUpdate);
        }
        return null;
    }
}
