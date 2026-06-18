package services.produtos.requests;

import static io.restassured.RestAssured.given;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import utils.UtilsUsuario;

public class PutProdutosByIdRequest {

    private PutProdutosByIdRequest() {
    }

    @Step("PUT /produtos/{idProduto}")
    public static ValidatableResponse enviar(String idProduto, String payload) {
        String token = UtilsUsuario.getTokenAdmin();
        return enviar(idProduto, payload, token);
    }

    @Step("PUT /produtos/{idProduto}")
    public static ValidatableResponse enviar(String idProduto, String payload, String token) {
        return given()
                .spec(RequestBase.spec())
                .pathParam("_id", idProduto)
                .header("Authorization", token)
                .body(payload)
                .when()
                .put("/produtos/{_id}")
                .then();
    }
}
