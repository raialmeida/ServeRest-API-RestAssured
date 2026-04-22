package services.login.payloads;

import jakarta.json.Json;

public class PostLoginPayload {

    private PostLoginPayload() {
    }

    public static String payload() {
        return payload("rateste@qa.com.br", "teste");
    }

    public static String payload(String email, String password) {
        return Json.createObjectBuilder()
                .add("email", email)
                .add("password", password)
                .build()
                .toString();
    }
}
