package services.carrinhos.requests;

import static io.restassured.RestAssured.given;

import config.RequestBase;
import io.qameta.allure.Step;
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
        return given()
                .spec(RequestBase.spec())
                .header("Authorization", token)
                .body(payload)
                .when()
                .post("/carrinhos")
                .then();
    }
}
