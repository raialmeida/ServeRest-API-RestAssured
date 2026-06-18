# ServeRest Testes Com RestAssured
Este projeto é um exemplo de como usar RestAssured com JUnit5 em um projeto Maven para testar serviços REST.

Foi utilizado a plataforma de testes de exemplo: [ServeRest](https://serverest.dev/)

Para visualizar o report allure da última execução acesse: [Allure](https://raialmeida.github.io/ServeRest-API-RestAssured/)

## Serviços cobertos

- login
- usuarios
- produtos
- carrinhos


## Arquitetura do projeto

O projeto utiliza uma arquitetura baseada em serviços, inspirada no padrão **Service Object Pattern**, aplicado à automação de testes de API.

Esse padrão separa a lógica de chamada da API da lógica de validação dos testes. Com isso, os testes ficam mais legíveis, reutilizáveis e fáceis de manter.

Também é possível chamar essa estrutura de:

- Arquitetura de testes de API baseada em serviços
- Service Object Pattern para testes automatizados de API
- API Client Pattern com organização por domínio

Cada domínio da API fica organizado dentro de `services`, como `usuarios`, `produtos`, `carrinhos` e `login`. Dentro de cada serviço, os arquivos são separados por responsabilidade:

- `payloads`: responsáveis por montar os corpos das requisições.
- `requests`: responsáveis por encapsular as chamadas HTTP feitas com RestAssured.
- `schema`: responsáveis por armazenar os contratos JSON Schema das respostas.
- `tests`: responsáveis por executar os cenários de teste e realizar as asserções.

A configuração comum das requisições fica centralizada em `config`, enquanto classes auxiliares e geração de massa de teste ficam em `utils`.

## Estrutura

```text
src/test/java
├── config
│   ├── Hooks.java
│   ├── RequestBase.java
│   └── TestConfig.java
├── services
│   ├── login
│   │   ├── payloads
│   │   ├── requests
│   │   ├── schema
│   │   └── tests
│   ├── usuarios
│   │   ├── payloads
│   │   ├── requests
│   │   ├── schema
│   │   └── tests
│   ├── produtos
│   │   ├── payloads
│   │   ├── requests
│   │   ├── schema
│   │   └── tests
│   └── carrinhos
│       ├── payloads
│       ├── requests
│       ├── schema
│       └── tests
└── utils
    ├── Environment.java
    ├── SchemaValidator.java
    ├── UtilsProduto.java
    └── UtilsUsuario.java
```
## Pré-requisitos

- Java 18+
- Maven 3.9+
- Docker / Docker Compose (Opcional)

## Execução

### Compilar projeto

````
mvn compile
````

### Executando os testes
```bash
mvn test
```

### Ambiente staging
```bash
mvn test -P staging
```

### Para executar os testes de acordo com a tag no teste

```
mvn test -Dgroups=@smoke
```

### Para executar os testes baseado nos arquivos de testes

```
mvn test "-Dtest=PostProdutosTest,PutProdutosByIdTest"
```

### Executando com Docker Compose

Para executar os testes sem instalar Java e Maven diretamente na máquina, é possível usar o Docker Compose.

Pré-requisito:

- Docker com suporte ao comando `docker compose`

Para construir a imagem e executar todos os testes:

```bash
docker compose run --rm test-api-serverest mvn test
```

Para executar os testes em um ambiente específico, informe o profile Maven desejado:

```bash
docker compose run --rm test-api-serverest mvn test -P staging
```

Para executar os testes por tag/grupo:

```bash
docker compose run --rm test-api-serverest mvn test -Dgroups=smoke
```

Os resultados dos testes são gerados nos diretórios mapeados pelo `docker-compose.yml`:

- `target/surefire-reports`
- `allure-results`

### Allure Report
```bash
mvn allure:serve
```

## Responsabilidades

### config

Contém as configurações globais dos testes.

- `RequestBase.java`: centraliza a configuração base do RestAssured, como `baseURI` e `Content-Type`.
- `Hooks.java`: prepara a configuração base antes da execução dos testes.
- `TestConfig.java`: concentra configurações adicionais do projeto de testes.

### services

Contém os serviços/domínios testados da API.

Cada serviço segue a mesma organização:

```text
services/<NomeDoServico>
├── payloads
├── requests
├── schema
└── tests
```

Essa padronização facilita a manutenção e deixa claro onde cada responsabilidade deve ficar.

### payloads

Contém classes responsáveis por gerar os corpos das requisições.

Exemplo:

```java
String payload = PostProdutosPayload.payload();
```

Essas classes evitam que o JSON fique espalhado diretamente dentro dos testes.

### requests

Contém classes responsáveis por executar as chamadas HTTP.

Exemplo:

```java
PostProdutosRequest.executar(payload);
```

A ideia é deixar os detalhes da requisição encapsulados, como endpoint, método HTTP, path params, headers e body.

### schema

Contém arquivos `.json` usados para validar o contrato das respostas da API.

Exemplo:

```java
.body(SchemaValidator.matchesSchema("services/produtos/schema/PostProdutosSchema.json"));
```

Essa camada ajuda a garantir que a estrutura da resposta continua compatível com o esperado.

### tests

Contém os cenários de teste.

Os testes ficam focados em:

- Preparar massa quando necessário.
- Chamar o request correspondente.
- Validar status code.
- Validar campos da resposta.
- Validar contrato/schema.

Exemplo:

```java
PostProdutosRequest.executar(payload)
        .assertThat()
        .statusCode(201)
        .body("message", equalTo("Cadastro realizado com sucesso"))
        .body("_id", notNullValue());
```

### utils

Contém classes auxiliares reutilizáveis.

- `Environment.java`: leitura de variáveis/configurações de ambiente.
- `SchemaValidator.java`: helper para validação de JSON Schema.
- `UtilsProduto.java`: criação e manipulação auxiliar de produtos.
- `UtilsUsuario.java`: criação de usuários, geração de token e usuários admin.

## Fluxo de um teste

O fluxo padrão dos testes segue esta ordem:

```text
Test
└── chama Request
    ├── usa RequestBase
    ├── envia Payload, quando necessário
    └── recebe Response
        ├── valida status code
        ├── valida campos específicos
        └── valida JSON Schema
```

Exemplo prático:

```text
PostProdutosTest
├── usa PostProdutosPayload para montar o body
├── chama PostProdutosRequest para executar o POST
├── valida status code 201
├── valida mensagem de sucesso
├── valida retorno do _id
└── valida contrato com PostProdutosSchema.json
```

## Benefícios da estrutura

- Melhor organização por domínio da API.
- Reaproveitamento de requests e payloads.
- Menor duplicação de código nos testes.
- Facilidade para alterar endpoints sem impactar vários arquivos.
- Validação de contrato separada por endpoint.
- Testes mais limpos e focados no comportamento esperado.

## Observações

- O projeto usa usuário admin fixo para criar produtos e usuários dinâmicos para cenários de carrinho.
- O endpoint de carrinho precisa de um usuário autenticado e de pelo menos um produto existente.
- As propriedades dos ambientes ficam em `src/test/resources`.

## Cobertura do Swagger

Esta versão revisada foi organizada para deixar explícita a cobertura de pelo menos 1 teste para cada endpoint do Swagger do ServeRest.

Operações cobertas:
- POST /login
- GET /usuarios
- POST /usuarios
- GET /usuarios/{_id}
- PUT /usuarios/{_id}
- DELETE /usuarios/{_id}
- GET /produtos
- POST /produtos
- GET /produtos/{_id}
- PUT /produtos/{_id}
- DELETE /produtos/{_id}
- GET /carrinhos
- POST /carrinhos
- GET /carrinhos/{_id}
- DELETE /carrinhos/concluir-compra
- DELETE /carrinhos/cancelar-compra

