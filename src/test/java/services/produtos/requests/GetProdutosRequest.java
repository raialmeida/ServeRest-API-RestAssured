package services.produtos.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class GetProdutosRequest extends RequestBase {

    private GetProdutosRequest() {
    }

    @Step("GET /produtos")
    public static ValidatableResponse executar() {
        return RestAssured.given()
                .spec(reqSpec)
                .get("/produtos")
                .then();
    }
}
