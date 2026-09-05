package br.com.serverest.auth;

import static io.restassured.RestAssured.given;

import br.com.serverest.config.Environment;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import jakarta.json.Json;

public final class PostAutenticacaoRequest {

    private PostAutenticacaoRequest() {
    }

    @Step("Autenticar usuário pela API")
    public static ValidatableResponse enviar(String email, String password) {
        String payload = Json.createObjectBuilder()
                .add("email", email)
                .add("password", password)
                .build()
                .toString();

        return given()
                .baseUri(Environment.getEnv("BASE_URI"))
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(AuthConfig.endpoint())
                .then();
    }
}
