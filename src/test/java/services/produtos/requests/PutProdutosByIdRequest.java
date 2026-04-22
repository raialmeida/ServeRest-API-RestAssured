package services.produtos.requests;

import config.RequestBase;
import utils.UtilsUsuario;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class PutProdutosByIdRequest extends RequestBase {

    private PutProdutosByIdRequest() {
    }

    @Step("PUT /produtos/{idProduto}")
    public static ValidatableResponse executar(String idProduto, String payload) {
        String token = UtilsUsuario.getTokenAdmin();
        return executar(idProduto, payload, token);
    }

    @Step("PUT /produtos/{idProduto}")
    public static ValidatableResponse executar(String idProduto, String payload, String token) {
        return RestAssured.given()
                .spec(reqSpec)
                .pathParam("_id", idProduto)
                .header("Authorization", token)
                .body(payload)
                .put("/produtos/{_id}")
                .then();
    }
}
