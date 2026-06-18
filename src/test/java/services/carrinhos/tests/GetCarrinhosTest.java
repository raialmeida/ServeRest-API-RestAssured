package services.carrinhos.tests;

import static org.hamcrest.Matchers.notNullValue;
import config.Hooks;
import services.carrinhos.requests.GetCarrinhosRequest;
import utils.SchemaValidator;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Carrinhos - GET /carrinhos")
@Feature("Carrinhos")
public class GetCarrinhosTest extends Hooks {

    @Test
    @DisplayName("Verificar listagem de carrinhos retornando lista não nula")
    public void validarGetCarrinhosComSucesso() {

        GetCarrinhosRequest.enviar()
                .assertThat()
                .statusCode(200)
                .body("carrinhos", notNullValue());
    }

    @Test
    @DisplayName("Validar contrato da resposta de listagem de carrinhos")
    public void validarSchemaGetCarrinhos() {

        GetCarrinhosRequest.enviar()
                .assertThat()
                .statusCode(200)
                .body(SchemaValidator.matchesSchema("services/carrinhos/schema/GetCarrinhosSchema.json"));
    }
}
