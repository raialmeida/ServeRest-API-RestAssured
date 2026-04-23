package services.carrinhos.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class GetCarrinhosByIdRequest {

    private GetCarrinhosByIdRequest() {
    }

    @Step("GET /carrinhos/{idCarrinho}")
    public static ValidatableResponse executar(String idCarrinho) {
        return RestAssured.given()
                .spec(RequestBase.spec())
                .pathParam("_id", idCarrinho)
                .get("/carrinhos/{_id}")
                .then();
    }
}
