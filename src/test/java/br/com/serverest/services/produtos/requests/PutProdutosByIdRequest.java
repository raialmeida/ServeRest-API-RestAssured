package br.com.serverest.services.produtos.requests;

import static io.restassured.RestAssured.given;

import br.com.serverest.http.RequestSpec;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import br.com.serverest.utils.UtilsUsuario;

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
                .spec(RequestSpec.spec())
                .pathParam("_id", idProduto)
                .header("Authorization", token)
                .body(payload)
                .when()
                .put("/produtos/{_id}")
                .then();
    }
}
