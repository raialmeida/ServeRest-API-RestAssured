package services.carrinhos.requests;

import static io.restassured.RestAssured.given;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import utils.UtilsUsuario;

public class DeleteCarrinhosConcluirCompraRequest {

    private DeleteCarrinhosConcluirCompraRequest() {
    }

    @Step("Criar carrinho e DELETE /carrinhos/concluir-compra")
    public static ValidatableResponse enviarComCarrinho(String payloadCarrinho) {
        String token = UtilsUsuario.criarUsuarioEObterToken(false);
        PostCarrinhosRequest.enviar(payloadCarrinho, token).assertThat().statusCode(201);
        return enviar(token);
    }

    @Step("DELETE /carrinhos/concluir-compra")
    public static ValidatableResponse enviar(String token) {
        return given()
                .spec(RequestBase.spec())
                .header("Authorization", token)
                .when()
                .delete("/carrinhos/concluir-compra")
                .then();
    }
}
