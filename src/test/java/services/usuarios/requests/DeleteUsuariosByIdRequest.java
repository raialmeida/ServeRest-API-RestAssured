package services.usuarios.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class DeleteUsuariosByIdRequest extends RequestBase {

    private DeleteUsuariosByIdRequest() {
    }

    @Step("DELETE /usuarios/{idUsuario}")
    public static ValidatableResponse executar(String idUsuario) {
        return RestAssured.given()
                .spec(reqSpec)
                .pathParam("_id", idUsuario)
                .delete("/usuarios/{_id}")
                .then();
    }
}
