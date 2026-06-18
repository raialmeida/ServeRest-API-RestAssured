package services.produtos.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import utils.UtilsUsuario;

public class DeleteProdutosByIdRequest {

    private DeleteProdutosByIdRequest() {
    }

    @Step("DELETE /produtos/{idProduto}")
    public static ValidatableResponse enviar(String idProduto) {
        String token = UtilsUsuario.getTokenAdmin();
        return enviar(idProduto, token);
    }

    @Step("DELETE /produtos/{idProduto}")
    public static ValidatableResponse enviar(String idProduto, String token) {
        return RestAssured.given()
                .spec(RequestBase.spec())
                .pathParam("_id", idProduto)
                .header("Authorization", token)
                .delete("/produtos/{_id}")
                .then();
    }
}
