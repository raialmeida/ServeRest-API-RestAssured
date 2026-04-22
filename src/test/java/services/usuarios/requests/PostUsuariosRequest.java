package services.usuarios.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class PostUsuariosRequest extends RequestBase {

    private PostUsuariosRequest() {
    }

    @Step("POST /usuarios")
    public static ValidatableResponse executar(String payload) {
        return RestAssured.given()
                .spec(reqSpec)
                .body(payload)
                .post("/usuarios")
                .then();
    }
}
