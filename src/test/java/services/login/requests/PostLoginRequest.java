package services.login.requests;

import static io.restassured.RestAssured.given;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class PostLoginRequest {

    private PostLoginRequest() {
    }

    @Step("POST /login")
    public static ValidatableResponse enviar(String payload) {
        return given()
                .spec(RequestBase.spec())
                .body(payload)
                .when()
                .post("/login")
                .then();
    }
}
