package services.usuarios.requests;

import static io.restassured.RestAssured.given;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class PostUsuariosRequest {

    private PostUsuariosRequest() {
    }

    @Step("POST /usuarios")
    public static ValidatableResponse enviar(String payload) {
        return given()
                .spec(RequestBase.spec())
                .body(payload)
                .when()
                .post("/usuarios")
                .then();
    }
}
