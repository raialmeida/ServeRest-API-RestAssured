package services.produtos.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class GetProdutosRequest {

    private GetProdutosRequest() {
    }

    @Step("GET /produtos")
    public static ValidatableResponse executar() {
        return RestAssured.given()
                .spec(RequestBase.spec())
                .get("/produtos")
                .then();
    }
}
