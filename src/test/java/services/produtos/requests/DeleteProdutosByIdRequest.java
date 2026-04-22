package services.produtos.requests;

import config.RequestBase;
import utils.UtilsUsuario;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class DeleteProdutosByIdRequest extends RequestBase {

    private DeleteProdutosByIdRequest() {
    }

    @Step("DELETE /produtos/{idProduto}")
    public static ValidatableResponse executar(String idProduto) {
        String token = UtilsUsuario.getTokenAdmin();
        return executar(idProduto, token);
    }

    @Step("DELETE /produtos/{idProduto}")
    public static ValidatableResponse executar(String idProduto, String token) {
        return RestAssured.given()
                .spec(reqSpec)
                .pathParam("_id", idProduto)
                .header("Authorization", token)
                .delete("/produtos/{_id}")
                .then();
    }
}
