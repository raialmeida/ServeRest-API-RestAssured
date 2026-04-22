package utils;

import services.produtos.payloads.PostProdutosPayload;
import services.produtos.requests.PostProdutosRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class UtilsProduto {

    private UtilsProduto() {
    }

    @Step("Criando produto dinâmico")
    public static Response criarProduto() {
        String payload = PostProdutosPayload.payload();
        Response response = PostProdutosRequest.executar(payload)
                .extract()
                .response();

        if (response.statusCode() != 201) {
            throw new RuntimeException("Falha ao criar produto. Status: "
                    + response.statusCode() + " Body: " + response.asString());
        }

        return response;
    }

    @Step("Criando produto dinâmico com token explícito")
    public static Response criarProduto(String token) {
        String payload = PostProdutosPayload.payload();
        Response response = PostProdutosRequest.executar(payload, token)
                .extract()
                .response();

        if (response.statusCode() != 201) {
            throw new RuntimeException("Falha ao criar produto. Status: "
                    + response.statusCode() + " Body: " + response.asString());
        }

        return response;
    }
}
