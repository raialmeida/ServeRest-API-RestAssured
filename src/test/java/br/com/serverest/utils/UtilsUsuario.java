package br.com.serverest.utils;

import br.com.serverest.auth.AuthConfig;
import br.com.serverest.services.usuarios.payloads.PostUsuariosPayload;
import br.com.serverest.services.usuarios.requests.PostUsuariosRequest;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class UtilsUsuario {

    private static final String ADMIN_NOME = "Admin ServeRest";
    private static final Faker faker = new Faker();

    private UtilsUsuario() {
    }

    @Step("Garantindo usuário administrador base")
    public static Response criarUsuarioAdmin() {
        String payload = PostUsuariosPayload.payload(
                ADMIN_NOME,
                AuthConfig.usuario(),
                AuthConfig.senha(),
                true);

        Response response = PostUsuariosRequest.enviar(payload).extract().response();

        if (response.statusCode() == 201 || response.statusCode() == 400) {
            return response;
        }

        throw new RuntimeException("Não foi possível garantir usuário admin. Status: " + response.statusCode()
                + " Body: " + response.asString());
    }

    @Step("Obtendo token do usuário administrador")
    public static String getTokenAdmin() {
        return AuthConfig.token();
    }

    @Step("Criando usuário dinâmico")
    public static Response criarUsuario(boolean administrador) {
        String nome = faker.name().firstName() + " " + faker.name().lastName();
        String email = ("qa+" + System.nanoTime() + "@mailinator.com").toLowerCase();
        String password = "teste123";
        return criarUsuario(nome, email, password, administrador);
    }

    @Step("Criando usuário com dados explícitos")
    public static Response criarUsuario(String nome, String email, String password, boolean administrador) {
        String payload = PostUsuariosPayload.payload(nome, email, password, administrador);
        return PostUsuariosRequest.enviar(payload).extract().response();
    }

    @Step("Criando usuário com dados explícitos e retornando token")
    public static String criarUsuarioEObterToken(String nome, String email, String password, boolean administrador) {
        Response response = criarUsuario(nome, email, password, administrador);
        if (response.statusCode() != 201 && response.statusCode() != 400) {
            throw new RuntimeException("Falha ao garantir usuário. Status: "
                    + response.statusCode() + " Body: " + response.asString());
        }

        return AuthConfig.token(email, password);
    }

    @Step("Criando usuário dinâmico e retornando token")
    public static String criarUsuarioEObterToken(boolean administrador) {
        String nome = faker.name().firstName() + " " + faker.name().lastName();
        String email = ("qa+" + System.nanoTime() + "@mailinator.com").toLowerCase();
        String password = "teste123";
        return criarUsuarioEObterToken(nome, email, password, administrador);
    }
}
