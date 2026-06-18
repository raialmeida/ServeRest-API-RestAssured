package services.usuarios.requests;

import static io.restassured.RestAssured.given;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class GetUsuariosRequest {

    private GetUsuariosRequest() {
    }

    @Step("GET /usuarios")
    public static ValidatableResponse enviar() {
        return given()
                .spec(RequestBase.spec())
                .when()
                .get("/usuarios")
                .then();
    }
}
