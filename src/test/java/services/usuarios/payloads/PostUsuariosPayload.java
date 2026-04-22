package services.usuarios.payloads;

import com.github.javafaker.Faker;
import jakarta.json.Json;

public class PostUsuariosPayload {

    private static Faker faker = new Faker();

    private PostUsuariosPayload() {
    }

    public static String payload() {
        String nome = faker.name().firstName() + " " + faker.name().lastName();
        String email = ("qa+" + System.nanoTime() + "@mailinator.com").toLowerCase();
        return payload(nome, email, "teste123", true);
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
