package services.carrinhos.requests;

import config.RequestBase;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class GetCarrinhosRequest extends RequestBase {

    private GetCarrinhosRequest() {
    }

    @Step("GET /carrinhos")
    public static ValidatableResponse executar() {
        return RestAssured.given()
                .spec(reqSpec)
                .get("/carrinhos")
                .then();
    }
}
