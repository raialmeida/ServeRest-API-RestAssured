package br.com.serverest.services.carrinhos.requests;

import static io.restassured.RestAssured.given;

import br.com.serverest.http.RequestSpec;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import br.com.serverest.utils.UtilsUsuario;

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
                .spec(RequestSpec.spec())
                .header("Authorization", token)
                .body(payload)
                .when()
                .post("/carrinhos")
                .then();
    }
}
