package services.usuarios.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class GetUsuariosByIdRequest extends RequestBase {

    private GetUsuariosByIdRequest() {
    }

    @Step("GET /usuarios/{idUsuario}")
    public static ValidatableResponse executar(String idUsuario) {
        return RestAssured.given()
                .spec(reqSpec)
                .pathParam("_id", idUsuario)
                .get("/usuarios/{_id}")
                .then();
    }
}
