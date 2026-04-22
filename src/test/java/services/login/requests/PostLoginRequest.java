package services.login.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class PostLoginRequest extends RequestBase {

    private PostLoginRequest() {
    }

    @Step("POST /login")
    public static ValidatableResponse executar(String payload) {
        return RestAssured.given()
                .spec(reqSpec)
                .body(payload)
                .post("/login")
                .then();
    }
}
