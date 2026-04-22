# ServeRest Testes Com RestAssured

Esta branch `gh-pages` é usada para publicar e gerenciar o relatório Allure gerado pela execução dos testes automatizados no GitHub Actions.

## Relatório Allure

O relatório publicado nesta branch pode ser acessado pelo GitHub Pages:

```text
https://raialmeida.github.io/ServeRest-API-RestAssured/
```

## Como funciona no GitHub Actions

O fluxo esperado é:

1. A branch principal do projeto executa o workflow de testes.
2. O GitHub Actions roda os testes automatizados com Docker Compose.
3. Os resultados do Allure são gerados durante a execução.
4. A action do Allure monta o histórico do relatório.
5. O conteúdo estático do relatório é publicado nesta branch `gh-pages`.
6. O GitHub Pages disponibiliza o relatório pela URL do projeto.

Para que isso funcione, o workflow deve publicar o relatório usando a branch `gh-pages`:

```yaml
publish_branch: gh-pages
publish_dir: allure-history
```

## Execução manual do relatório

Também é possível executar o workflow manualmente pelo GitHub:

1. Acesse a aba `Actions` do repositório.
2. Selecione o workflow de testes da API.
3. Clique em `Run workflow`.
4. Escolha a branch principal do projeto.
5. Aguarde a execução finalizar.

Após a execução, o relatório Allure será atualizado automaticamente na branch `gh-pages` e publicado no GitHub Pages.

## Observações

- Esta branch não deve ser usada para desenvolvimento dos testes.
- O código-fonte do projeto fica na branch principal.
- Esta branch deve conter somente os arquivos estáticos necessários para visualizar o relatório.
- Se o relatório não aparecer, verifique se o GitHub Pages está configurado para publicar a partir da branch `gh-pages`.
