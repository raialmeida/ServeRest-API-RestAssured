package br.com.serverest.services.login.payloads;

import br.com.serverest.auth.AuthConfig;
import jakarta.json.Json;

public class PostLoginPayload {

    private PostLoginPayload() {
    }

    public static String payload() {
        return payload(AuthConfig.usuario(), AuthConfig.senha());
    }

    public static String payload(String email, String password) {
        return Json.createObjectBuilder()
                .add("email", email)
                .add("password", password)
                .build()
                .toString();
    }
}
