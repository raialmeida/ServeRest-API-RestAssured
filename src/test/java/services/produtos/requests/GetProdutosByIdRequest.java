package services.produtos.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class GetProdutosByIdRequest {

    private GetProdutosByIdRequest() {
    }

    @Step("GET /produtos/{idProduto}")
    public static ValidatableResponse executar(String idProduto) {
        return RestAssured.given()
                .spec(RequestBase.spec())
                .pathParam("_id", idProduto)
                .get("/produtos/{_id}")
                .then();
    }
}
