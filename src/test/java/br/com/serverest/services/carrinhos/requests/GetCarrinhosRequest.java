package br.com.serverest.services.carrinhos.requests;

import static io.restassured.RestAssured.given;

import br.com.serverest.http.RequestSpec;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class GetCarrinhosRequest {

    private GetCarrinhosRequest() {
    }

    @Step("GET /carrinhos")
    public static ValidatableResponse enviar() {
        return given()
                .spec(RequestSpec.spec())
                .when()
                .get("/carrinhos")
                .then();
    }
}
