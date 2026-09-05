package br.com.serverest.auth;

import br.com.serverest.config.Environment;
import io.restassured.response.Response;

public final class AuthConfig {

    private AuthConfig() {
    }

    public static String endpoint() {
        return Environment.getEnv("AUTH_ENDPOINT");
    }

    public static String usuario() {
        return Environment.getEnv("AUTH_USUARIO");
    }

    public static String senha() {
        return Environment.getEnv("AUTH_SENHA");
    }

    public static String token() {
        return token(usuario(), senha());
    }

    /** Obtém a autorização de uma identidade específica, sem cache global. */
    public static String token(String email, String password) {
        Response response = PostAutenticacaoRequest.enviar(email, password).extract().response();
        if (response.statusCode() != 200) {
            throw new IllegalStateException("Falha na autenticação: HTTP " + response.statusCode());
        }
        String token = response.jsonPath().getString("authorization");
        if (token == null || token.isBlank() || !token.startsWith("Bearer ") || token.length() <= 7) {
            throw new IllegalStateException("API de autenticação não retornou authorization válido.");
        }
        return token;
    }
}
