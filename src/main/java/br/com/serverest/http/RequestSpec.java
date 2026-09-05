package br.com.serverest.http;

import br.com.serverest.config.Environment;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HeaderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpec {

    /**
     * Cria a especificação base utilizada nas requisições da API.
     * Define a URI base a partir da variável de ambiente {@code BASE_URI},
     * configura o conteúdo como JSON e adiciona um token de autorização padrão.
     * Os headers {@code Authorization} e {@code Content-Type} podem ser
     * sobrescritos pelas configurações de cada requisição.
     *
     * @return a especificação base configurada para as requisições
     */
    public static RequestSpecification spec() {
        return new RequestSpecBuilder()
                .setBaseUri(Environment.getEnv("BASE_URI"))
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer your_token_here_default")
                .setConfig(RestAssuredConfig.config().headerConfig(HeaderConfig.headerConfig().overwriteHeadersWithName(
                        "Authorization",
                        "Content-Type")))
                .build();
    }
}
