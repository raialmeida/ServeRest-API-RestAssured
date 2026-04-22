package utils;

import java.io.File;

import io.restassured.module.jsv.JsonSchemaValidator;

public class SchemaValidator {
    public static JsonSchemaValidator matchesSchema(String schemaFilePath) {
        File schemaFile = new File("src/test/java/" + schemaFilePath);
        return JsonSchemaValidator.matchesJsonSchema(schemaFile);
    }
}
