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

A configuração de SQL Server e PostgreSQL está no projeto apenas como exemplo
de acesso a banco de dados. A proposta dos testes ServeRest é validar a API;
essa configuração não é usada para comprovar a persistência das requisições.
As bases locais e os exemplos abaixo servem para exercitar conexões, consultas
e alterações de dados.

Há trechos demonstrativos para validação de banco comentados em `PostProdutosTest`. Enquanto forem
executados, esses trechos exigem os bancos locais configurados, mesmo sendo
exercícios independentes da validação da API.

Para experimentar a configuração, preencha as propriedades no arquivo do
ambiente escolhido ou forneça os valores externamente. Exemplo para SQL Server:

```properties
SQLSERVER_DB_URL=jdbc:sqlserver://servidor:1433;encrypt=true
SQLSERVER_DB_NAME=NomeDaBase
SQLSERVER_DB_USER=usuario_db
SQLSERVER_DB_PASSWORD=sua_senha
```

Para PostgreSQL, preencha suas próprias variáveis no mesmo arquivo de ambiente:

```properties
POSTGRES_DB_URL=jdbc:postgresql://servidor:5432/
POSTGRES_DB_NAME=NomeDaBase
POSTGRES_DB_USER=usuario_db
POSTGRES_DB_PASSWORD=sua_senha
```

Os valores acima são exemplos e devem corresponder à instância utilizada.
Cada método usa diretamente a configuração do seu banco. Por padrão, a base vem de
`POSTGRES_DB_NAME` ou `SQLSERVER_DB_NAME`. O nome definido na configuração ou passado
à sobrecarga com `nomeBase` prevalece sobre a base da URL, sem necessidade de placeholders.
Para alterar a variável da base PostgreSQL na execução:

```bash
mvn test -Denv=hml -DPOSTGRES_DB_NAME=OutraBase
```

O cenário escolhe o banco pelo método e passa a base como primeiro argumento:

```java
import br.com.serverest.config.Environment;

// Dentro do cenário:
var produtosPostgres = DatabaseConfig.postgresQuery(
        Environment.getEnv("POSTGRES_DB_NAME"),
        "SELECT id AS \"Id\", nome AS \"Nome\" FROM public.produtos WHERE id = ?",
        produtoId);

var produtosSqlServer = DatabaseConfig.sqlServerQuery(
        Environment.getEnv("SQLSERVER_DB_NAME"),
        "SELECT Id, Nome FROM dbo.Produtos WHERE Id = ?",
        produtoId);
```

O nome da base pode vir de qualquer variável do ambiente, permitindo escolher
outra base em um cenário específico. As sobrecargas sem o argumento `nomeBase`
usam `POSTGRES_DB_NAME` ou `SQLSERVER_DB_NAME` automaticamente.

Se a chamada não informar a base e o primeiro valor após o SQL for uma `String`,
agrupe os valores em `new Object[]{...}` para selecionar a sobrecarga correta.
Com a base explícita, ou com um `int` como primeiro valor após o SQL, os valores
podem ser passados diretamente. Sem parâmetros, basta chamar `postgresQuery(sql)`
ou `sqlServerQuery(sql)`. Esse cuidado com as sobrecargas Java não altera os tipos
das colunas do banco.

Para inserir, atualizar ou excluir dados, use o método de alteração do banco:

```java
int linhasPostgres = DatabaseConfig.postgresExecuteUpdate(
        "UPDATE public.produtos SET quantidade = ? WHERE id = ?",
        new Object[]{novaQuantidade, produtoId});

int linhasSqlServer = DatabaseConfig.sqlServerExecuteUpdate(
        Environment.getEnv("SQLSERVER_DB_NAME"),
        "UPDATE dbo.Produtos SET Quantidade = ? WHERE Id = ?",
        novaQuantidade, produtoId);
```

Cada chamada retorna um `int` com a quantidade de linhas afetadas. Para informar
outra base, use as sobrecargas `postgresExecuteUpdate(nomeBase, sql, parametros)` ou
`sqlServerExecuteUpdate(nomeBase, sql, parametros)`. O mesmo cuidado com o array
vale para alterações sem base explícita cujo primeiro valor após o SQL seja `String`.

`DatabaseConfig` mantém uma conexão por banco, aberta apenas quando utilizada.
Operações seguintes à mesma base reutilizam a conexão. Trocar a base de um banco
reabre somente sua conexão; a conexão do outro banco permanece aberta.
O `@BeforeAll` de `Hooks` inicializa a configuração dos testes; o `@AfterAll`
fecha as duas conexões ao terminar cada classe.
Cenários sem acesso ao banco não abrem conexão. O reuso é destinado à execução
sequencial, sem pool.

As bases locais de exemplo são independentes da API pública ServeRest e não
recebem os dados enviados nas requisições. Seus registros servem apenas para
demonstrar o funcionamento da configuração de banco.

As queries devem usar os schemas, tabelas e tipos da base escolhida. O exemplo
SQL Server em `PostProdutosTest` usa `dbo.Produtos`. Em PostgreSQL,
se a tabela estiver em `public.produtos`, uma consulta equivalente pode usar:

```java
var produtos = DatabaseConfig.postgresQuery(
        Environment.getEnv("POSTGRES_DB_NAME"),
        "SELECT id AS \"Id\", nome AS \"Nome\" FROM public.produtos WHERE id = ?",
        produtoId);
```

Os aliases entre aspas preservam as chaves `Id` e `Nome` no resultado. O retorno
continua sendo `List<Map<String, Object>>`, e as asserções ficam no teste.

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
- `database/DatabaseConfig.java`: exemplo de configuração JDBC para SQL Server e PostgreSQL, com consultas e alterações parametrizadas.
- `hooks/Hooks.java`, em `src/test/java/br/com/serverest`: inicializa a configuração, prepara o administrador quando necessário e fecha as conexões existentes no `@AfterAll`.

`postgresQuery(sql, parametros)` e `sqlServerQuery(sql, parametros)`
retornam `List<Map<String, Object>>`:
cada item representa uma linha, com nomes de colunas como chaves. Retorna todas as
linhas encontradas ou uma lista vazia. Os valores informados após o SQL preenchem
os `?` na mesma ordem.

`postgresExecuteUpdate(sql, parametros)` e
`sqlServerExecuteUpdate(sql, parametros)` executam `INSERT`, `UPDATE`
ou `DELETE` no banco correspondente e na base configurada no ambiente. Ambos retornam um `int`
com a quantidade de linhas afetadas e usam parâmetros na mesma ordem dos `?`.
As sobrecargas dos mesmos métodos recebem `nomeBase` antes do SQL para escolher
outra base. Sem base explícita, um `Object[]` evita a seleção indevida da sobrecarga
quando o primeiro valor após o SQL for `String`.
Esses métodos demonstram o acesso a banco e não fazem parte da proposta de
validação de persistência da API neste projeto.

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
