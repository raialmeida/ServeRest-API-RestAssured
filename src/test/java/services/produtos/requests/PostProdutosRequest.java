package services.produtos.requests;

import static io.restassured.RestAssured.given;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import utils.UtilsUsuario;

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
                .spec(RequestBase.spec())
                .header("Authorization", token)
                .body(payload)
                .when()
                .post("/produtos")
                .then();
    }
}
