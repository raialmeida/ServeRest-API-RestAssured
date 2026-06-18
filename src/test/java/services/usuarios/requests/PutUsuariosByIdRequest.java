package services.usuarios.requests;

import static io.restassured.RestAssured.given;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class PutUsuariosByIdRequest {

    private PutUsuariosByIdRequest() {
    }

    @Step("PUT /usuarios/{idUsuario}")
    public static ValidatableResponse enviar(String idUsuario, String payload) {
        return given()
                .spec(RequestBase.spec())
                .pathParam("_id", idUsuario)
                .body(payload)
                .when()
                .put("/usuarios/{_id}")
                .then();
    }
}
