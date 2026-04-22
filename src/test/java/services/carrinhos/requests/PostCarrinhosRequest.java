package services.carrinhos.requests;

import config.RequestBase;
import utils.UtilsUsuario;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class PostCarrinhosRequest extends RequestBase {

    private PostCarrinhosRequest() {
    }

    @Step("POST /carrinhos")
    public static ValidatableResponse executar(String payload) {
        String token = UtilsUsuario.criarUsuarioEObterToken(false);
        return executar(payload, token);
    }

    @Step("POST /carrinhos")
    public static ValidatableResponse executar(String payload, String token) {
        return RestAssured.given()
                .spec(reqSpec)
                .header("Authorization", token)
                .body(payload)
                .post("/carrinhos")
                .then();
    }
}
