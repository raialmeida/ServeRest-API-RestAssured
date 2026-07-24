package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.restassured.module.jsv.JsonSchemaValidator;

public class SchemaValidator {
    private static final Path SCHEMAS_ROOT = Path.of("src", "test", "java", "services");

    public static JsonSchemaValidator matchesSchema(String schemaFileName) {
        try (var paths = Files.walk(SCHEMAS_ROOT)) {
            Path schema = paths
                    .filter(path -> path.getFileName().toString().equals(schemaFileName))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Schema não encontrado: " + schemaFileName));

            return JsonSchemaValidator.matchesJsonSchema(schema.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao procurar o schema", e);
        }
    }
}
