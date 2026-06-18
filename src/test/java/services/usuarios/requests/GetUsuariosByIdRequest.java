package services.usuarios.requests;

import static io.restassured.RestAssured.given;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class GetUsuariosByIdRequest {

    private GetUsuariosByIdRequest() {
    }

    @Step("GET /usuarios/{idUsuario}")
    public static ValidatableResponse enviar(String idUsuario) {
        return given()
                .spec(RequestBase.spec())
                .pathParam("_id", idUsuario)
                .when()
                .get("/usuarios/{_id}")
                .then();
    }
}
