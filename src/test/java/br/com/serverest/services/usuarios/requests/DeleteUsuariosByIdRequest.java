package br.com.serverest.services.usuarios.requests;

import static io.restassured.RestAssured.given;

import br.com.serverest.http.RequestSpec;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class DeleteUsuariosByIdRequest {

    private DeleteUsuariosByIdRequest() {
    }

    @Step("DELETE /usuarios/{idUsuario}")
    public static ValidatableResponse enviar(String idUsuario) {
        return given()
                .spec(RequestSpec.spec())
                .pathParam("_id", idUsuario)
                .when()
                .delete("/usuarios/{_id}")
                .then();
    }
}
