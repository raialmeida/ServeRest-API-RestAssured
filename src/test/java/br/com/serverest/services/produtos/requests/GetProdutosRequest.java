package br.com.serverest.services.produtos.requests;

import static io.restassured.RestAssured.given;

import br.com.serverest.http.RequestSpec;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class GetProdutosRequest {

    private GetProdutosRequest() {
    }

    @Step("GET /produtos")
    public static ValidatableResponse enviar() {
        return given()
                .spec(RequestSpec.spec())
                .when()
                .get("/produtos")
                .then();
    }
}
