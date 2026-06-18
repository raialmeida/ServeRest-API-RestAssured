package services.carrinhos.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import utils.UtilsUsuario;

public class PostCarrinhosRequest {

    private PostCarrinhosRequest() {
    }

    @Step("POST /carrinhos")
    public static ValidatableResponse enviar(String payload) {
        String token = UtilsUsuario.criarUsuarioEObterToken(false);
        return enviar(payload, token);
    }

    @Step("POST /carrinhos")
    public static ValidatableResponse enviar(String payload, String token) {
        return RestAssured.given()
                .spec(RequestBase.spec())
                .header("Authorization", token)
                .body(payload)
                .post("/carrinhos")
                .then();
    }
}
