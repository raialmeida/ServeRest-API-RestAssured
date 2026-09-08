# ServeRest Testes Com RestAssured

Este projeto é um exemplo de como usar RestAssured com JUnit 6 em um projeto Maven para testar serviços REST.

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

Cada domínio da API fica organizado dentro de `services`, como `usuarios`, `produtos`, `carrinhos` e `login`. Os arquivos são separados por responsabilidade:

- `payloads`: responsáveis por montar os corpos das requisições.
- `requests`: responsáveis por encapsular as chamadas HTTP feitas com RestAssured.
- `tests`: responsáveis por executar os cenários de teste e realizar as asserções.

Os pacotes `auth`, `config`, `database` e `http` ficam em `src/main/java/br/com/serverest`. A configuração HTTP fica em `http/RequestSpec.java`. Os testes, requests, payloads, hooks e utilitários ficam em `src/test/java/br/com/serverest`.

## Estrutura

```text
src
├── main/java/br/com/serverest
│   ├── auth
│   │   ├── AuthConfig.java
│   │   └── PostAutenticacaoRequest.java
│   ├── config
│   │   ├── Environment.java
│   │   └── TestConfig.java
│   ├── database
│   │   └── DatabaseConfig.java
│   └── http
│       └── RequestSpec.java
└── test
    ├── java/br/com/serverest
    │   ├── hooks
    │   │   └── Hooks.java
    │   ├── services
    │   │   ├── login
    │   │   │   ├── payloads
    │   │   │   ├── requests
    │   │   │   └── tests
    │   │   ├── usuarios
    │   │   │   ├── payloads
    │   │   │   ├── requests
    │   │   │   └── tests
    │   │   ├── produtos
    │   │   │   ├── payloads
    │   │   │   ├── requests
    │   │   │   └── tests
    │   │   └── carrinhos
    │   │       ├── payloads
    │   │       ├── requests
    │   │       └── tests
    │   └── utils
    │       ├── UtilsProduto.java
    │       └── UtilsUsuario.java
    └── resources
        ├── config-dev.properties
        ├── config-hml.properties
        ├── config-staging.properties
        ├── allure.properties
        └── services
            ├── login/schema
            ├── usuarios/schema
            ├── produtos/schema
            └── carrinhos/schema
```

## Pré-requisitos

- [Java 18+](https://adoptium.net/temurin/releases/)
- [Maven 3.9+](https://maven.apache.org/download.cgi)
- [Docker](https://docs.docker.com/get-started/get-docker/) / [Docker Compose](https://docs.docker.com/compose/install/) (Opcional)

## Execução

### Compilar projeto

```bash
mvn compile
```

### Executando os testes

```bash
mvn test
```

### Ambiente staging

```bash
mvn test -Denv=staging
```

O ambiente `dev` é usado por padrão. As configurações ficam em
`src/test/resources/config-<ambiente>.properties`. Para HML, use:

```bash
mvn test -Denv=hml
```

Uma configuração pode ser sobrescrita por propriedade Java ou variável de
ambiente, sem alterar os arquivos versionados. A prioridade é: propriedade Java
(`-D`), variável de ambiente e, por último, arquivo `.properties`:

```bash
mvn test -DBASE_URI=http://localhost:3000
BASE_URI=http://localhost:3000 mvn test
```

O token é obtido pelo `AuthConfig`, que chama `PostAutenticacaoRequest` em
`POST /login`, usando a `BASE_URI` do ambiente selecionado.
O administrador criado pelos testes usa `AUTH_USUARIO` e `AUTH_SENHA` do
ambiente selecionado; esses valores também podem ser sobrescritos externamente.
`UtilsUsuario.getTokenAdmin()` retorna o campo `authorization` completo
(`Bearer ...`), usado no header `Authorization` das requisições protegidas.
Usuários dinâmicos são autenticados com suas próprias credenciais por
`AuthConfig.token(email, password)`. `RequestSpec.spec()` define um header
`Authorization` de exemplo (`Bearer your_token_here_default`); as requisições
protegidas sobrescrevem esse valor com o token correspondente ao usuário.

O acesso ao SQL Server é opcional. O exemplo de consulta e validação de persistência
em `PostProdutosTest` está comentado, portanto os testes atuais de API não exigem
uma base configurada. Para usar consultas ao banco, preencha as propriedades no
arquivo do ambiente escolhido ou forneça os valores externamente:

```properties
DB_URL=jdbc:sqlserver://servidor:1433;encrypt=true
DB_NAME=NomeDaBase
DB_USER=usuario_db
DB_PASSWORD=sua_senha
```

Os valores acima são exemplos e devem corresponder à instância utilizada.
`DB_NAME` é enviado separadamente nas propriedades da conexão; não é necessário
inserir `${DB_NAME}` na URL. Para escolher outra base na execução:

```bash
mvn test -Denv=hml -DDB_NAME=OutraBase
```

`DatabaseConfig` abre a conexão na primeira consulta ou alteração e a reutiliza
nas próximas chamadas. O `@BeforeAll` de `Hooks` inicializa a configuração dos
testes; o `@AfterAll` fecha a conexão ao terminar cada classe, somente se ela tiver
sido aberta. Cenários sem acesso ao banco não abrem conexão. O reuso utiliza uma
conexão compartilhada durante a execução sequencial, sem pool.

Para validar a persistência de uma requisição, a conexão deve apontar para a base
utilizada pela API testada. Uma base SQL Server independente serve para exercícios
de consulta, mas não recebe automaticamente os dados enviados à API pública.

### Para executar os testes de acordo com a tag no teste

```bash
mvn test -Dgroups=@smoke
```

O valor deve corresponder exatamente ao `@Tag` do teste. Atualmente, login usa
`@Tag("@smoke")` e um cenário de carrinhos usa `@Tag("smoke")`; para este último,
execute `mvn test -Dgroups=smoke`.

### Para executar os testes baseado nos arquivos de testes

```bash
mvn test "-Dtest=PostProdutosTest,PutProdutosByIdTest"
```

### Executando com Docker Compose

Para executar os testes sem instalar Java e Maven diretamente na máquina, é possível usar o Docker Compose.

Pré-requisito:

- Docker com suporte ao comando `docker compose`

Para construir a imagem e executar todos os testes:

```bash
docker compose build
docker compose run --rm test-api-serverest mvn test
```

Para executar os testes em um ambiente específico, informe o ambiente desejado:

```bash
docker compose run --rm test-api-serverest mvn test -Denv=staging
```

Para executar os testes por tag/grupo:

```bash
docker compose run --rm test-api-serverest mvn test -Dgroups=@smoke
```

Os resultados dos testes são gerados nos diretórios mapeados pelo `docker-compose.yml`:

- `target/surefire-reports`
- `allure-results`

### Allure Report

Para visualizar os resultados de uma execução local, gerados em `target/allure-results`:

```bash
mvn allure:serve
```

## Responsabilidades

### config

As configurações e o ciclo de vida dos testes ficam distribuídos nestes arquivos:

- `config/Environment.java`: seleciona o ambiente e lê propriedades Java, variáveis de ambiente e arquivos `.properties`.
- `config/TestConfig.java`: configura os filtros e logs do RestAssured e os metadados do Allure.
- `http/RequestSpec.java`: centraliza `baseURI`, `Content-Type` e os headers padrão.
- `auth/AuthConfig.java` e `auth/PostAutenticacaoRequest.java`: leem as credenciais e autenticam os usuários.
- `database/DatabaseConfig.java`: gerencia a conexão JDBC e executa consultas e alterações parametrizadas.
- `hooks/Hooks.java`, em `src/test/java/br/com/serverest`: inicializa a configuração, prepara o administrador quando necessário e fecha a conexão existente no `@AfterAll`.

`DatabaseConfig.queryConsultar(sql, parametros)` retorna `List<Map<String, Object>>`:
cada item representa uma linha, com nomes de colunas como chaves. Retorna todas as
linhas encontradas ou uma lista vazia. Os valores informados após o SQL preenchem
os `?` na mesma ordem. `executeUpdate(sql, parametros)` executa `INSERT`, `UPDATE`
ou `DELETE` e retorna a quantidade de linhas afetadas. As validações dos dados
ficam nos cenários de teste.

### services

Contém os serviços/domínios testados da API.

Cada serviço segue a mesma organização:

```text
src/test/java/br/com/serverest/services/<servico>
├── payloads
├── requests
└── tests

src/test/resources/services/<servico>
└── schema
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

Contém classes responsáveis por executar as chamadas HTTP. Os métodos de request
são estáticos, usam `@Step`, retornam `ValidatableResponse` e terminam com `.then()`.
Os cenários fazem as asserções a partir de `.assertThat()`.

Exemplo:

```java
PostProdutosRequest.enviar(payload);
```

A ideia é deixar os detalhes da requisição encapsulados, como endpoint, método HTTP, path params, headers e body.

### schema

Contém arquivos `.json` usados para validar o contrato das respostas da API, em
`src/test/resources/services/<servico>/schema`. O caminho passado à validação é
relativo ao classpath, sem o prefixo `src/test/resources`.

Exemplo:

```java
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

PostProdutosRequest.enviar(payload)
        .assertThat()
        .statusCode(201)
        .body(matchesJsonSchemaInClasspath("services/produtos/schema/PostProdutosSchema.json"));
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
PostProdutosRequest.enviar(payload)
        .assertThat()
        .statusCode(201)
        .body("message", equalTo("Cadastro realizado com sucesso"))
        .body("_id", notNullValue());
```

### utils

Contém classes auxiliares reutilizáveis.

- `UtilsProduto.java`: criação e manipulação auxiliar de produtos.
- `UtilsUsuario.java`: criação de usuários, geração de token e usuários admin.

## Fluxo de um teste

O fluxo padrão dos testes segue esta ordem:

```text
Test
└── chama Request
    ├── usa RequestSpec
    ├── envia Payload, quando necessário
    └── retorna ValidatableResponse para o teste
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

- O administrador usa `AUTH_USUARIO` e `AUTH_SENHA` do ambiente; os cenários de carrinho também criam usuários dinâmicos.
- O endpoint de carrinho precisa de um usuário autenticado e de pelo menos um produto existente.
- As propriedades dos ambientes ficam em `src/test/resources`.

## Cobertura do Swagger

Os cenários de exemplos implementados cobrem as seguintes operações da API ServeRest.

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
