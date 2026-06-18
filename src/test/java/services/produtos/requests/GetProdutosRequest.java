package services.produtos.requests;

import static io.restassured.RestAssured.given;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class GetProdutosRequest {

    private GetProdutosRequest() {
    }

    @Step("GET /produtos")
    public static ValidatableResponse enviar() {
        return given()
                .spec(RequestBase.spec())
                .when()
                .get("/produtos")
                .then();
    }
}
