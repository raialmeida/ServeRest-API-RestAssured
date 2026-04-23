package services.usuarios.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class GetUsuariosRequest {

    private GetUsuariosRequest() {
    }

    @Step("GET /usuarios")
    public static ValidatableResponse executar() {
        return RestAssured.given()
                .spec(RequestBase.spec())
                .get("/usuarios")
                .then();
    }
}
