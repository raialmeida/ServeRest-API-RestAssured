package services.produtos.payloads;

import com.github.javafaker.Faker;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;

public class PutProdutosByIdPayload {

    private static Faker fake = new Faker();

    private PutProdutosByIdPayload() {
    }

    public static String payload() {
        String nome = fake.commerce().productName();
        return payload(nome, 470, "Mouse", 381);
    }

    public static String payload(String nome, Integer preco, String descricao, Integer quantidade) {
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("nome", nome)
                .add("descricao", descricao == null ? "Produto gerado automaticamente" : descricao)
                .add("quantidade", quantidade == null ? 1 : quantidade);

        if (preco != null) {
            builder.add("preco", preco);
        }

        return builder.build().toString();
    }
}
