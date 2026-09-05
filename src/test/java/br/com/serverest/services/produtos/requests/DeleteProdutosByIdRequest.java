package br.com.serverest.services.produtos.requests;

import static io.restassured.RestAssured.given;

import br.com.serverest.http.RequestSpec;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import br.com.serverest.utils.UtilsUsuario;

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
                .spec(RequestSpec.spec())
                .pathParam("_id", idProduto)
                .header("Authorization", token)
                .when()
                .delete("/produtos/{_id}")
                .then();
    }
}
