package br.com.serverest.services.produtos.requests;

import static io.restassured.RestAssured.given;

import br.com.serverest.http.RequestSpec;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import br.com.serverest.utils.UtilsUsuario;

public class PostProdutosRequest {

    private PostProdutosRequest() {
    }

    @Step("POST /produtos")
    public static ValidatableResponse enviar(String payload) {
        String token = UtilsUsuario.getTokenAdmin();
        return enviar(payload, token);
    }

    @Step("POST /produtos")
    public static ValidatableResponse enviar(String payload, String token) {
        return given()
                .spec(RequestSpec.spec())
                .header("Authorization", token)
                .body(payload)
                .when()
                .post("/produtos")
                .then();
    }
}
