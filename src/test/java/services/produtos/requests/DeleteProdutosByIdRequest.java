package services.produtos.requests;

import static io.restassured.RestAssured.given;

import config.RequestBase;
import io.qameta.allure.Step;
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
        return given()
                .spec(RequestBase.spec())
                .pathParam("_id", idProduto)
                .header("Authorization", token)
                .when()
                .delete("/produtos/{_id}")
                .then();
    }
}
