package br.com.cp1java.controller;

import br.com.cp1java.domain.dtos.VehicleRequest;
import br.com.cp1java.domain.dtos.VehicleResponse;
import br.com.cp1java.domain.model.VehicleType;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
                .body("economia", equalTo(vehicleResponse.economia()))
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
                .body("economia", equalTo(vehicleRequest.economia() + " km/kWh"))
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
        VehicleRequest vehicleRequest = new VehicleRequest("Toyota", "Teste", 2023, 140.5, 12.5, "ELETRICO", new BigDecimal("120000.00"));
        given()
                .contentType(ContentType.JSON)
                .body(vehicleRequest)
                .when()
                .put("/carros/e83e5705-20cf-4004-969f-4895467b831f")
                .then()
                .statusCode(404);
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

        List<VehicleResponse> expected = vehicleResponses
                .stream()
                .limit(10)
                .toList();

        List<VehicleResponse> actual = given()
                .when()
                .get("/carros/potencia")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].marca", equalTo("Toyota"))
                .body("[0].modelo", equalTo("Corolla"))
                .body("[0].ano", equalTo(2023))
                .body("[0].potencia", equalTo(Float.valueOf(String.format(Locale.US, "%.2f",expected.get(0).potencia()))))
                .body("[9].marca", equalTo("Toyota"))
                .body("[9].modelo", equalTo("Corolla"))
                .body("[9].ano", equalTo(2023))
                .body("[9].potencia", equalTo(Float.valueOf(String.format(Locale.US, "%.2f",expected.get(9).potencia()))))
                .extract().body() .as(new TypeRef<>() {});

        assertTrue(expected.get(0).potencia() > expected.get(9).potencia());
        assertEquals(expected, actual);
    }

    @Test
    void testGetTopVehiclesByEconomia_shouldReturnTopVehicles(){
        List<VehicleResponse> vehicleResponses = new ArrayList<>();
        for(int i = 0; i < 15; i++){
            vehicleResponses.add(saveVehicleWithRandomInfo());
        }
        vehicleResponses.sort(Comparator.comparing(VehicleResponse::economia).reversed());

        List<VehicleResponse> expected = vehicleResponses
                .stream()
                .limit(10)
                .toList();

        List<VehicleResponse> actual = given()
                .when()
                .get("/carros/economia")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].marca", equalTo("Toyota"))
                .body("[0].modelo", equalTo("Corolla"))
                .body("[0].ano", equalTo(2023))
                .body("[0].economia", equalTo(expected.get(0).economia()))
                .body("[9].marca", equalTo("Toyota"))
                .body("[9].modelo", equalTo("Corolla"))
                .body("[9].ano", equalTo(2023))
                .body("[9].economia", equalTo(expected.get(9).economia()))
                .extract().body() .as(new TypeRef<>() {});

        assertEquals(expected, actual);
    }

    @Test
    void testGetAllVehiclesEletricos_shouldReturnElectricVehicles(){
        List<VehicleResponse> vehicleResponses = new ArrayList<>();
        for(int i = 0; i < 15; i++){
            vehicleResponses.add(saveVehicleWithRandomInfo());
        }
        vehicleResponses = vehicleResponses
                .stream()
                .filter(vehicle -> vehicle.tipo() == VehicleType.ELETRICO)
                .toList();

        int lastIndex = vehicleResponses.size() - 1;
        List<VehicleResponse> actual = given()
                .when()
                .get("/carros/eletricos")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].marca", equalTo("Toyota"))
                .body("[0].modelo", equalTo("Corolla"))
                .body("[0].ano", equalTo(2023))
                .body("[0].tipo", equalTo(vehicleResponses.get(0).tipo().toString()))
                .body("[" + lastIndex + "].marca", equalTo("Toyota"))
                .body("[" + lastIndex + "].modelo", equalTo("Corolla"))
                .body("[" + lastIndex + "].ano", equalTo(2023))
                .body("[" + lastIndex + "].tipo", equalTo(vehicleResponses.get(lastIndex).tipo().toString()))
                .extract().body() .as(new TypeRef<>() {});

        assertEquals(vehicleResponses, actual);

    }

    private VehicleResponse saveVehicleWithRandomInfo() {
        int randomNumberVehicleType = (int) (Math.random() * 2) + 1;
        VehicleRequest vehicleRequest = new VehicleRequest("Toyota", "Corolla", 2023, Double.parseDouble(String.format(Locale.US, "%.2f", Math.random() * 901 + 100)), Double.parseDouble(String.format(Locale.US, "%.2f", Math.random() * 901 + 100)), VehicleType.values()[randomNumberVehicleType].toString(), new BigDecimal("120000.00"));
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