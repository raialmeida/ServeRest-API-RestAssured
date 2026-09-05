package br.com.serverest.services.carrinhos.requests;

import static io.restassured.RestAssured.given;

import br.com.serverest.http.RequestSpec;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class GetCarrinhosByIdRequest {

    private GetCarrinhosByIdRequest() {
    }

    @Step("GET /carrinhos/{idCarrinho}")
    public static ValidatableResponse enviar(String idCarrinho) {
        return given()
                .spec(RequestSpec.spec())
                .pathParam("_id", idCarrinho)
                .when()
                .get("/carrinhos/{_id}")
                .then();
    }
}
