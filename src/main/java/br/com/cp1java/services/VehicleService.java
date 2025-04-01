package br.com.cp1java.services;

import br.com.cp1java.domain.model.Vehicle;
import br.com.cp1java.domain.model.VehicleType;
import br.com.cp1java.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
            vehicleToUpdate.setMarca(vehicle.getMarca());
            vehicleToUpdate.setAno(vehicle.getAno());
            vehicleToUpdate.setTipo(vehicle.getTipo());
            vehicleToUpdate.setEconomia(vehicle.getEconomia());
            vehicleToUpdate.setPotencia(vehicle.getPotencia());
            vehicleToUpdate.setPreco(vehicle.getPreco());
            vehicleToUpdate.setModelo(vehicle.getModelo());
            return vehicleRepository.save(vehicleToUpdate);
        }
        return null;
    }

    public void delete(UUID id) {
        vehicleRepository.deleteById(id);
    }

    public List<Vehicle>  getVehiclesWithHighestPotencia() {
        return vehicleRepository.findTop10ByOrderByPotenciaDesc();
    }

    public List<Vehicle> getVehiclesWithHighestEconomia() {
        return vehicleRepository.findTop10ByOrderByEconomiaDesc();
    }

    public List<Vehicle> findAllVehiclesEletricos() {
        return vehicleRepository.findAllByTipo(VehicleType.ELETRICO);
    }
}
