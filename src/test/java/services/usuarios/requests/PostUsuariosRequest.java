package services.usuarios.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class PostUsuariosRequest {

    private PostUsuariosRequest() {
    }

    @Step("POST /usuarios")
    public static ValidatableResponse enviar(String payload) {
        return RestAssured.given()
                .spec(RequestBase.spec())
                .body(payload)
                .post("/usuarios")
                .then();
    }
}
