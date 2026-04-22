package services.produtos.requests;

import config.RequestBase;
import utils.UtilsUsuario;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class PostProdutosRequest extends RequestBase {

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
                .spec(reqSpec)
                .header("Authorization", token)
                .body(payload)
                .post("/produtos")
                .then();
    }
}
