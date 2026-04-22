package utils;

import services.login.payloads.PostLoginPayload;
import services.login.requests.PostLoginRequest;
import services.usuarios.payloads.PostUsuariosPayload;
import services.usuarios.requests.PostUsuariosRequest;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class UtilsUsuario {

    private static final String ADMIN_NOME = "Admin ServeRest";
    private static final String ADMIN_EMAIL = "rateste@qa.com.br";
    private static final String ADMIN_PASSWORD = "teste";
    private static final Faker faker = new Faker();

    private UtilsUsuario() {
    }

    @Step("Garantindo usuário administrador base")
    public static Response criarUsuarioAdmin() {
        String payload = PostUsuariosPayload.payload(
                ADMIN_NOME,
                ADMIN_EMAIL,
                ADMIN_PASSWORD,
                true);

        Response response = PostUsuariosRequest.executar(payload).extract().response();

        if (response.statusCode() == 201 || response.statusCode() == 400) {
            return response;
        }

        throw new RuntimeException("Não foi possível garantir usuário admin. Status: " + response.statusCode()
                + " Body: " + response.asString());
    }

    @Step("Obtendo token do usuário administrador")
    public static String getTokenAdmin() {

        return PostLoginRequest.executar(PostLoginPayload.payload(ADMIN_EMAIL, ADMIN_PASSWORD))
                .extract()
                .path("authorization");
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
        return PostUsuariosRequest.executar(payload).extract().response();
    }

    @Step("Criando usuário com dados explícitos e retornando token")
    public static String criarUsuarioEObterToken(String nome, String email, String password, boolean administrador) {
        Response response = criarUsuario(nome, email, password, administrador);
        if (response.statusCode() != 201 && response.statusCode() != 400) {
            throw new RuntimeException("Falha ao garantir usuário. Status: "
                    + response.statusCode() + " Body: " + response.asString());
        }

        return PostLoginRequest.executar(PostLoginPayload.payload(email, password))
                .extract()
                .path("authorization");
    }

    @Step("Criando usuário dinâmico e retornando token")
    public static String criarUsuarioEObterToken(boolean administrador) {
        String nome = faker.name().firstName() + " " + faker.name().lastName();
        String email = ("qa+" + System.nanoTime() + "@mailinator.com").toLowerCase();
        String password = "teste123";
        return criarUsuarioEObterToken(nome, email, password, administrador);
    }
}
