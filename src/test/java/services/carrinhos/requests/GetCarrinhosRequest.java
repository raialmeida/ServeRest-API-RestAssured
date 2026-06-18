package services.carrinhos.requests;

import static io.restassured.RestAssured.given;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class GetCarrinhosRequest {

    private GetCarrinhosRequest() {
    }

    @Step("GET /carrinhos")
    public static ValidatableResponse enviar() {
        return given()
                .spec(RequestBase.spec())
                .when()
                .get("/carrinhos")
                .then();
    }
}
