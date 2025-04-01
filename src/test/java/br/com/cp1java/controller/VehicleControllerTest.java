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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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

    @Test
    void testGetVehicleById_shouldReturnVehicle_whenVehicleExists() {
        VehicleResponse vehicleResponse = saveVehicle();
        given()
                .when()
                .get("/carros/" + vehicleResponse.id())
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(vehicleResponse.id().toString()))
                .body("marca", equalTo(vehicleResponse.marca()))
                .body("modelo", equalTo(vehicleResponse.modelo()))
                .body("ano", equalTo(vehicleResponse.ano()))
                .body("potencia", equalTo(vehicleResponse.potencia().floatValue()))
                .body("economia", equalTo(vehicleResponse.economia().floatValue()))
                .body("tipo", equalTo(vehicleResponse.tipo().toString()))
                .body("preco", equalTo(vehicleResponse.preco().floatValue()));
    }

    @Test
    void testGetVehicleById_shouldReturnNotFound_whenVehicleDoesNotExist(){
        given()
                .when()
                .get("/carros/e83e5705-20cf-4004-969f-4895467b831f")
                .then()
                .statusCode(404);
    }

    @Test
    void testUpdateVehicle_shouldReturnUpdatedVehicle_whenValidData(){
        VehicleResponse vehicleResponse = saveVehicle();
        VehicleRequest vehicleRequest = new VehicleRequest("Toyota", "Corolla", 2023, 140.5, 12.5, "ELETRICO", new BigDecimal("120000.00"));
        given()
        .contentType(ContentType.JSON)
                .body(vehicleRequest)
                .when()
                .put("/carros/" + vehicleResponse.id())
                .then()
                .statusCode(200)
                .body("id", equalTo(vehicleResponse.id().toString()))
                .body("marca", equalTo(vehicleRequest.marca()))
                .body("modelo", equalTo(vehicleRequest.modelo()))
                .body("ano", equalTo(vehicleRequest.ano()))
                .body("potencia", equalTo(vehicleRequest.potencia().floatValue()))
                .body("economia", equalTo(vehicleRequest.economia().floatValue()))
                .body("tipo", equalTo(vehicleRequest.tipo()))
                .body("preco", equalTo(vehicleRequest.preco().floatValue()));
    }

    @Test
    void testUpdateVehicle_shouldReturnBadRequest_whenInvalidData(){
        VehicleResponse vehicleResponse = saveVehicle();
        VehicleRequest vehicleRequest = new VehicleRequest("Toyota", "", 2023, 140.5, 12.5, "ELETRICO", new BigDecimal("120000.00"));
        given()
                .contentType(ContentType.JSON)
                .body(vehicleRequest)
                .when()
                .put("/carros/" + vehicleResponse.id())
                .then()
                .statusCode(400);
    }

    @Test
    void testUpdateVehicle_shouldReturnNotFound_whenVehicleNotFound(){
        VehicleRequest vehicleRequest = new VehicleRequest("Toyota", "", 2023, 140.5, 12.5, "ELETRICO", new BigDecimal("120000.00"));
        given()
                .contentType(ContentType.JSON)
                .body(vehicleRequest)
                .when()
                .put("/carros/e83e5705-20cf-4004-969f-4895467b831f")
                .then()
                .statusCode(400);
    }

    @Test
    void testDeleteVehicle_shouldReturnNoContent(){
        VehicleResponse vehicleResponse = saveVehicle();
        given()
                .when()
                .delete("/carros/" + vehicleResponse.id())
                .then()
                .statusCode(204);

        given()
                .when()
                .get("/carros/" + vehicleResponse.id())
                .then()
                .statusCode(404);
    }

    @Test
    void testGetTopVehiclesByPotencia_shouldReturnTopVehicles(){
        List<VehicleResponse> vehicleResponses = new ArrayList<>();
        for(int i = 0; i < 15; i++){
            vehicleResponses.add(saveVehicleWithRandomInfo());
        }
        vehicleResponses.sort(Comparator.comparing(VehicleResponse::potencia).reversed());
        List<String> expected = vehicleResponses
                .stream()
                .limit(10)
                .map(v -> String.format("{id=%s, marca=%s, modelo=%s, ano=%d, potencia=%.2f, economia=%.2f, tipo=%s, preco=%.2f}",
                        v.id(), v.marca(), v.modelo(), v.ano(), v.potencia(), v.economia(), v.tipo(), v.preco()))
                .toList();

        given()
                .when()
                .get("/carros/potencia")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("", equalTo(expected));


    }

    private VehicleResponse saveVehicleWithRandomInfo() {
        VehicleRequest vehicleRequest = new VehicleRequest("Toyota", "Corolla", 2023, Math.random() * 901 + 100, Math.random() * 901 + 100, "COMBUSTAO", new BigDecimal("120000.00"));
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