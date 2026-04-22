package services.carrinhos.requests;

import config.RequestBase;
import utils.UtilsUsuario;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class DeleteCarrinhosConcluirCompraRequest extends RequestBase {

    private DeleteCarrinhosConcluirCompraRequest() {
    }

    @Step("Criar carrinho e DELETE /carrinhos/concluir-compra")
    public static ValidatableResponse executarComCarrinho(String payloadCarrinho) {
        String token = UtilsUsuario.criarUsuarioEObterToken(false);
        PostCarrinhosRequest.executar(payloadCarrinho, token).assertThat().statusCode(201);
        return executar(token);
    }

    @Step("DELETE /carrinhos/concluir-compra")
    public static ValidatableResponse executar(String token) {
        return RestAssured.given()
                .spec(reqSpec)
                .header("Authorization", token)
                .delete("/carrinhos/concluir-compra")
                .then();
    }
}
