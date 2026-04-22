package services.usuarios.payloads;

import com.github.javafaker.Faker;
import jakarta.json.Json;

public class PutUsuariosByIdPayload {

    private static Faker faker = new Faker();

    private PutUsuariosByIdPayload() {
    }

    public static String payload() {
        return payload(
                faker.name().fullName(),
                "qa.update+" + System.nanoTime() + "@mailinator.com",
                "teste123",
                false);
    }

    public static String payload(String nome, String email, String password, boolean administrador) {
        return Json.createObjectBuilder()
                .add("nome", nome)
                .add("email", email)
                .add("password", password)
                .add("administrador", Boolean.toString(administrador))
                .build()
                .toString();
    }
}
