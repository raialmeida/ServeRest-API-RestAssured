package services.usuarios.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class PutUsuariosByIdRequest extends RequestBase {

    private PutUsuariosByIdRequest() {
    }

    @Step("PUT /usuarios/{idUsuario}")
    public static ValidatableResponse executar(String idUsuario, String payload) {
        return RestAssured.given()
                .spec(reqSpec)
                .pathParam("_id", idUsuario)
                .body(payload)
                .put("/usuarios/{_id}")
                .then();
    }
}
