package services.usuarios.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class GetUsuariosByIdRequest {

    private GetUsuariosByIdRequest() {
    }

    @Step("GET /usuarios/{idUsuario}")
    public static ValidatableResponse enviar(String idUsuario) {
        return RestAssured.given()
                .spec(RequestBase.spec())
                .pathParam("_id", idUsuario)
                .get("/usuarios/{_id}")
                .then();
    }
}
