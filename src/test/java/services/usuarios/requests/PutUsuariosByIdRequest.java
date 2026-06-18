package services.usuarios.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class PutUsuariosByIdRequest {

    private PutUsuariosByIdRequest() {
    }

    @Step("PUT /usuarios/{idUsuario}")
    public static ValidatableResponse enviar(String idUsuario, String payload) {
        return RestAssured.given()
                .spec(RequestBase.spec())
                .pathParam("_id", idUsuario)
                .body(payload)
                .put("/usuarios/{_id}")
                .then();
    }
}
