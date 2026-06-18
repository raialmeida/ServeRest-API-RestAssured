package services.produtos.requests;

import static io.restassured.RestAssured.given;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class GetProdutosByIdRequest {

    private GetProdutosByIdRequest() {
    }

    @Step("GET /produtos/{idProduto}")
    public static ValidatableResponse enviar(String idProduto) {
        return given()
                .spec(RequestBase.spec())
                .pathParam("_id", idProduto)
                .when()
                .get("/produtos/{_id}")
                .then();
    }
}
