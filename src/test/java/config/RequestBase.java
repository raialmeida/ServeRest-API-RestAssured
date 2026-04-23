package config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.Environment;

public class RequestBase {

    public static RequestSpecification spec() {
        return new RequestSpecBuilder()
                .setBaseUri(Environment.getEnv("baseURI"))
                .setContentType(ContentType.JSON)
                .build();
    }
}
