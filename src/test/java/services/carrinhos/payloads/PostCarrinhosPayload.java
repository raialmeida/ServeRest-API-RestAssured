package services.carrinhos.payloads;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;

public class PostCarrinhosPayload {

    private PostCarrinhosPayload() {
    }

    public static String payload(String idProduto) {
        return payload(idProduto, 1);
    }

    public static String payload(String idProduto, int quantidade) {
        JsonArrayBuilder produtos = Json.createArrayBuilder()
                .add(Json.createObjectBuilder()
                        .add("idProduto", idProduto)
                        .add("quantidade", quantidade));

        return Json.createObjectBuilder()
                .add("produtos", produtos)
                .build()
                .toString();
    }
}
