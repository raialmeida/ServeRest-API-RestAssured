package services.carrinhos.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class GetCarrinhosRequest {

    private GetCarrinhosRequest() {
    }

    @Step("GET /carrinhos")
    public static ValidatableResponse enviar() {
        return RestAssured.given()
                .spec(RequestBase.spec())
                .get("/carrinhos")
                .then();
    }
}
