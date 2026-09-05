package br.com.serverest.services.usuarios.requests;

import static io.restassured.RestAssured.given;

import br.com.serverest.http.RequestSpec;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class GetUsuariosRequest {

    private GetUsuariosRequest() {
    }

    @Step("GET /usuarios")
    public static ValidatableResponse enviar() {
        return given()
                .spec(RequestSpec.spec())
                // se quiser remover o header Authorization ou outro header da spec requisição, utilize o filtro abaixo
                .filter((requestSpec, responseSpec, ctx) -> {
                    requestSpec.removeHeader("Authorization");
                    return ctx.next(requestSpec, responseSpec);
                })
                .when()
                .get("/usuarios")
                .then();
    }
}
