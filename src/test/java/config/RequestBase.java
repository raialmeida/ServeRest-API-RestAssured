package config;

import utils.Environment;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestBase {
    public static RequestSpecification reqSpec;

    public static RequestSpecification baseRequest() {
        RestAssured.baseURI = Environment.getEnv("baseURI");
        reqSpec = new RequestSpecBuilder()
                .setBaseUri(RestAssured.baseURI)
                .setContentType(ContentType.JSON)
                .build();
        return reqSpec;
    }
}
