package br.com.cp1java.controller;

import br.com.cp1java.domain.dtos.VehicleRequest;
import br.com.cp1java.domain.dtos.VehicleResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VehicleControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.basePath = "";
    }

    @Test
    void testSaveVehicle_shouldReturnCreatedVehicle_whenValidRequest(){
        saveVehicle();
    }

    @Test
    void testSaveVehicle_shouldReturnBadRequest_whenInvalidData(){
        VehicleRequest vehicleRequest = new VehicleRequest("Toyota", "", 2023, 140.5, 12.5, "COMBUSTAO", new BigDecimal("120000.00"));
        given()
                .contentType(ContentType.JSON)
                .body(vehicleRequest)
                .when()
                .post("/carros")
                .then()
                .statusCode(400);
    }

    private VehicleResponse saveVehicle() {
        VehicleRequest vehicleRequest = new VehicleRequest("Toyota", "Corolla", 2023, 140.5, 12.5, "COMBUSTAO", new BigDecimal("120000.00"));
        return given()
                .contentType(ContentType.JSON)
                .body(vehicleRequest)
                .when()
                .post("/carros")
                .then()
                .statusCode(201)
                .extract().body().as(VehicleResponse.class);
    }
}