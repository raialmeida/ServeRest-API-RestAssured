package services.carrinhos.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import utils.UtilsUsuario;

public class DeleteCarrinhosCancelarCompraRequest {

    private DeleteCarrinhosCancelarCompraRequest() {
    }

    @Step("Criar carrinho e DELETE /carrinhos/cancelar-compra")
    public static ValidatableResponse enviarComCarrinho(String payloadCarrinho) {
        String token = UtilsUsuario.criarUsuarioEObterToken(false);
        PostCarrinhosRequest.enviar(payloadCarrinho, token).assertThat().statusCode(201);
        return enviar(token);
    }

    @Step("DELETE /carrinhos/cancelar-compra")
    public static ValidatableResponse enviar(String token) {
        return RestAssured.given()
                .spec(RequestBase.spec())
                .header("Authorization", token)
                .delete("/carrinhos/cancelar-compra")
                .then();
    }
}
