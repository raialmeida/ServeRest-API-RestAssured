package services.carrinhos.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import utils.UtilsUsuario;

public class DeleteCarrinhosConcluirCompraRequest {

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
                .spec(RequestBase.spec())
                .header("Authorization", token)
                .delete("/carrinhos/concluir-compra")
                .then();
    }
}
