package br.com.serverest.services.carrinhos.requests;

import static io.restassured.RestAssured.given;

import br.com.serverest.http.RequestSpec;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import br.com.serverest.utils.UtilsUsuario;

public class DeleteCarrinhosCancelarCompraRequest {

    private DeleteCarrinhosCancelarCompraRequest() {
    }

    @Step("DELETE /carrinhos/cancelar-compra")
    public static ValidatableResponse enviar(String payloadCarrinho) {
        String token = UtilsUsuario.criarUsuarioEObterToken(false);
        PostCarrinhosRequest.enviar(payloadCarrinho, token).assertThat().statusCode(201);
        return given()
                .spec(RequestSpec.spec())
                .header("Authorization", token)
                .when()
                .delete("/carrinhos/cancelar-compra")
                .then();
    }
}
