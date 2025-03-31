package br.com.cp1java.controller;

import br.com.cp1java.services.VehicleService;

public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }
}
