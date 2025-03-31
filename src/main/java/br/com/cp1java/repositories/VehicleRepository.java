package br.com.cp1java.repositories;

import br.com.cp1java.domain.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

    List<Vehicle> findTop10ByOrderByPotenciaDesc();

    List<Vehicle> findTop10ByOrderByEconomiaDesc();
}
