package services.produtos.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import utils.UtilsUsuario;

public class PostProdutosRequest {

    private PostProdutosRequest() {
    }

    @Step("POST /produtos")
    public static ValidatableResponse executar(String payload) {
        String token = UtilsUsuario.getTokenAdmin();
        return executar(payload, token);
    }

    @Step("POST /produtos")
    public static ValidatableResponse executar(String payload, String token) {
        return RestAssured.given()
                .spec(RequestBase.spec())
                .header("Authorization", token)
                .body(payload)
                .post("/produtos")
                .then();
    }
}
